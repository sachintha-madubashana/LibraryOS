package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findById(String id);
    boolean existsById(String id);
    List<Member> findAll();
    boolean save(Member member);
    boolean update(Member member);
    boolean deleteById(String id);
}
