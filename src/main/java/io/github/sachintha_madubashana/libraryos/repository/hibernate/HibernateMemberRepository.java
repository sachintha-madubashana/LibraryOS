package io.github.sachintha_madubashana.libraryos.repository.hibernate;

import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

public class HibernateMemberRepository implements MemberRepository {
    @Override
    public Optional<Member> findById(String id) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public List<Member> findAll() {
        return List.of();
    }

    @Override
    public boolean save(Member member) {
        return false;
    }

    @Override
    public boolean update(Member member) {
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }
}
