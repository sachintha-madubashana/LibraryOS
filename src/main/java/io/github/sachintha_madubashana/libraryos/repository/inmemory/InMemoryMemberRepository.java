package io.github.sachintha_madubashana.libraryos.repository.inmemory;

import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.model.SortingValue;
import io.github.sachintha_madubashana.libraryos.repository.MemberRepository;
import io.github.sachintha_madubashana.libraryos.service.SampleData;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class InMemoryMemberRepository implements MemberRepository {
    private final List<Member> members;

    public InMemoryMemberRepository() {
        members = new ArrayList<>(SampleData.getMembers());
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
    public List<Member> find(List<String> terms, SortingValue sortingValue) {
        Comparator<Member> comparator = switch (sortingValue) {
            case ID_DESCENDING -> Comparator.comparing(Member::getId).reversed();
            case NAME_ASCENDING -> Comparator.comparing(Member::getFullName);
            case NAME_DESCENDING -> Comparator.comparing(Member::getFullName).reversed();
            default -> Comparator.comparing(Member::getId);
        };

        return members.stream().filter(member -> terms.stream().allMatch(term -> {
            if(term == null || term.isBlank()) {
                return true;
            }
            return member.getCode().toLowerCase().contains(term) ||
                   member.getFullName().toLowerCase().contains(term) ||
                   member.getEmail().toLowerCase().contains(term);
        })).sorted(comparator).toList();
    }

    @Override
    public Long count() {
        return (long) members.size();
    }

    @Override
    public Optional<Member> findLast() {
        return members.isEmpty() ? Optional.empty() : Optional.of(members.getLast());
    }

    @Override
    public boolean save(Member member) {
        if (member == null || existsById(member.getCode())) {
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

        return members.removeIf(member -> member.getId().toString().equals(id));
    }
}
