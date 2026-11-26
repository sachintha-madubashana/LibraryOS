package io.github.sachintha_madubashana.libraryos.repository.mysql;

import io.github.sachintha_madubashana.libraryos.database.DatabaseConnection;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.model.SortingValue;
import io.github.sachintha_madubashana.libraryos.repository.MemberRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLMemberRepository implements MemberRepository {
    private final Connection connection;

    public MySQLMemberRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public Optional<Member> findById(String code) {
        String query = "SELECT `id`, `member_code`, `name`, `email`, `phone`, `address`,`joined_date` FROM `member` WHERE `member_code` = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getMembersFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find book.", e);
        }

        return Optional.empty();
    }

    @Override
    public boolean existsById(String code) {
        return findById(code).isPresent();
    }

    @Override
    public List<Member> find(List<String> terms, SortingValue sortingValue) {
        if (terms == null || sortingValue == null) return new ArrayList<>();

        StringBuilder query = new StringBuilder("SELECT `id`, `member_code`, `name`, `email`, `phone`, `address`,`joined_date` FROM `member` WHERE 1=1");
        List<String> params = new ArrayList<>();

        for (String term : terms) {
            if (term != null && !term.isBlank()) {
                query.append(" AND (LOWER(member_code) LIKE ?")
                        .append(" OR LOWER(name) LIKE ?")
                        .append(" OR LOWER(email) LIKE ?").append(")");
                String likeValue = "%" + term.toLowerCase() + "%";
                params.add(likeValue);
                params.add(likeValue);
                params.add(likeValue);
            }
        }

        switch (sortingValue) {
            case ID_DESCENDING -> query.append(" ORDER BY `member_code` DESC");
            case NAME_ASCENDING -> query.append(" ORDER BY `name` ASC");
            case NAME_DESCENDING -> query.append(" ORDER BY `name` DESC");
            default -> query.append(" ORDER BY `member_code` ASC");
        }

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                statement.setString(i + 1, params.get(i));
            }
            ResultSet resultSet = statement.executeQuery();
            List<Member> members = new ArrayList<>();
            while (resultSet.next()) {
                members.add(getMembersFromResultSet(resultSet));
            }
            return members;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find Member.", e);
        }
    }

    @Override
    public Optional<Member> findLast() {
        String query = "SELECT `id`, `member_code`, `name`, `email`, `phone`, `address`,`joined_date` FROM `member` ORDER BY `id` DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getMembersFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find last member.", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean save(Member member) {
        if (member == null || existsById(member.getCode())) {
            return false;
        }

        String query = "INSERT INTO `member` (`member_code`, `name`, `email`, `phone`, `address`, `joined_date`) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, member.getCode());
            statement.setString(2, member.getFullName());
            statement.setString(3, member.getEmail());
            statement.setString(4, member.getPhone());
            statement.setString(5, member.getAddress());
            statement.setDate(6, java.sql.Date.valueOf(member.getJoinedDate()));

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save member.", e);
        }

    }

    @Override
    public boolean update(Member member) {
        if (member == null || member.getId() == null) {
            return false;
        }

        String query = "UPDATE `member` SET `name` = ?, `email` = ?, `phone` = ?, `address` = ?, `joined_date` = ? WHERE `member_code` = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, member.getFullName());
            statement.setString(2, member.getEmail());
            statement.setString(3, member.getPhone());
            statement.setString(4, member.getAddress());
            statement.setDate(5, Date.valueOf(member.getJoinedDate()));
            statement.setString(6, member.getCode());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update member.", e);
        }
    }

    @Override
    public boolean deleteById(String code) {
        if (code == null) {
            return false;
        }

        String query = "DELETE FROM `member` WHERE `member_code` = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete member.", e);
        }
    }

    @Override
    public Long count() {
        String query = "SELECT COUNT(*) AS total FROM `member`";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to count members.", e);
        }
        return 0L;
    }

    private Member getMembersFromResultSet(ResultSet resultSet) {
        try {
            return new Member(
                    resultSet.getInt("id"),
                    resultSet.getString("member_code"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("phone"),
                    resultSet.getString("address"),
                    resultSet.getDate("joined_date").toLocalDate()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get member from result set.", e);
        }
    }
}
