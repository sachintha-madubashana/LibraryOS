package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.service.SampleData;
import javafx.collections.FXCollections;

import java.util.List;
import java.util.Optional;

public class InMemoryMemberRepository implements MemberRepository {
    private final List<Member> members;

    public InMemoryMemberRepository() {
        members = FXCollections.observableArrayList(SampleData.getMembers());
    }

    @Override
    public Optional<Member> findById(String id) {
        if (id == null || id.isBlank()) {
            return Optional.empty();
        }

        return members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Member> findAll() {
        return List.copyOf(members);
    }

    @Override
    public boolean save(Member member) {
        if (member == null || existsById(member.getId())) {
            return false;
        }

        members.add(member);
        return true;
    }

    @Override
    public boolean update(Member member) {
        if (member == null || member.getId() == null) {
            return false;
        }

        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getId().equals(member.getId())) {
                members.set(i, member);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteById(String id) {
        if (id == null) {
            return false;
        }

        return members.removeIf(member -> member.getId().equals(id));
    }
}
