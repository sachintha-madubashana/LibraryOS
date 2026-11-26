package io.github.sachintha_madubashana.libraryos.repository.inmemory;

import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.model.SortingValue;
import io.github.sachintha_madubashana.libraryos.repository.MemberRepository;
import io.github.sachintha_madubashana.libraryos.service.SampleData;
import javafx.collections.FXCollections;

import java.util.Comparator;
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
    public List<Member> find(List<String> terms, SortingValue sortingValue) {
        List<Member> filteredMembers;

        if(terms != null && !terms.isEmpty()) {
            filteredMembers = members.stream().filter(member -> {
                boolean[] conditions = new boolean[terms.size()];
                for (String term : terms) {
                    if (member.getCode().toLowerCase().contains(term) ||
                            member.getFullName().toLowerCase().contains(term) ||
                            member.getEmail().toLowerCase().contains(term)) {
                        conditions[terms.indexOf(term)] = true;
                    }
                }

                boolean isMatch = true;
                for (boolean condition : conditions) {
                    isMatch = isMatch && condition;
                }

                return isMatch;
            }).toList();
        } else {
            filteredMembers = members;
        }

        return switch (sortingValue) {
            case ID_ASCENDING -> filteredMembers.stream().sorted(Comparator.comparing(Member::getId)).toList();
            case ID_DESCENDING -> filteredMembers.stream().sorted((o1, o2) -> o2.getId().compareTo(o1.getId())).toList();
            case NAME_ASCENDING -> filteredMembers.stream().sorted((o1, o2) -> o1.getFullName().compareTo(o2.getFullName())).toList();
            case NAME_DESCENDING -> filteredMembers.stream().sorted((o1, o2) -> o2.getFullName().compareTo(o1.getFullName())).toList();
        };
    }

    @Override
    public Long count() {
        return (long) members.size();
    }

    @Override
    public Member findLast() {
        return members.isEmpty() ? null : members.getLast();
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
