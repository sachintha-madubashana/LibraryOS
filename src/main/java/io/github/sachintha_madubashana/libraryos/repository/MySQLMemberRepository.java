package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.database.DatabaseConnection;
import io.github.sachintha_madubashana.libraryos.model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLMemberRepository implements MemberRepository{
    private final Connection connection;

    public MySQLMemberRepository(){
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public Optional<Member> findById(String code) {
        String query = "SELECT `member_code`, `name`, `email`, `phone`, `address`,`joined_date` FROM `member` WHERE `member_code` = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, code);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Member member = new Member(
                            resultSet.getString("member_code"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getString("address"),
                            resultSet.getDate("joined_date").toLocalDate()
                    );

                    return Optional.of(member);
                }
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
    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        String query = "SELECT `member_code` , `name`, `email`, `phone`, `address`,`joined_date` FROM `member`";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Member member = new Member(
                            resultSet.getString("member_code"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getString("address"),
                            resultSet.getDate("joined_date").toLocalDate()
                    );

                    members.add(member);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find book.", e);
        }

        return members;
    }

    @Override
    public boolean save(Member member) {
        if(member == null || existsById(member.getId())) {
            return false;
        }

        String query = "INSERT INTO `member` (`member_code`, `name`, `email`, `phone`, `address`, `joined_date`) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, member.getId());
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
        if(member == null || member.getId() == null) {
            return false;
        }

        String query = "UPDATE `member` SET `name` = ?, `email` = ?, `phone` = ?, `address` = ?, `joined_date` = ? WHERE `member_code` = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, member.getFullName());
            statement.setString(2, member.getEmail());
            statement.setString(3, member.getPhone());
            statement.setString(4, member.getAddress());
            statement.setDate(5, Date.valueOf(member.getJoinedDate()));
            statement.setString(6, member.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update member.", e);
        }
    }

    @Override
    public boolean deleteById(String code) {
        if(code == null) {
            return false;
        }

        String query = "DELETE FROM `member` WHERE `member_code` = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, code);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete member.", e);
        }
    }
}
