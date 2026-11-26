package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.model.SortingValue;
import io.github.sachintha_madubashana.libraryos.repository.MemberRepository;

import java.util.List;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Result<Member> getMemberById(String code) {
        if (code == null || code.isBlank()) {
            return Result.error(
                    "Invalid member code",
                    "Please provide a valid member code."
            );
        }

        return memberRepository.findById(code)
                .map(member -> Result.success(
                        "Member found",
                        "The requested member was found.",
                        member
                ))
                .orElseGet(() -> Result.error(
                        "Member not found",
                        "No member exists with code: " + code
                ));
    }

    public Result<List<Member>> getMembers(String query, SortingValue sortingValue){
        String trimmedQuery = query == null ? "" : query.trim();
        String[] terms = trimmedQuery.isEmpty() ? new String[0] : trimmedQuery.toLowerCase().split("[\\s-]+");
        List<Member> members = memberRepository.find(List.of(terms), sortingValue);

        if (members == null) {
            return Result.error(
                    "Unable to retrieve Members",
                    "The Member list could not be loaded."
            );
        }

        return Result.success(
                "Members retrieved successfully",
                "The Member list has been loaded.",
                members
        );
    }

    public Result<Member> register(Member member) {
        if (member == null) {
            return Result.error(
                    "Invalid member",
                    "Member information is required."
            );
        }

        if (memberRepository.existsById(member.getCode())) {
            return Result.error(
                    "Duplicate member code",
                    "A member with this code already exists."
            );
        }

        boolean isSaved = memberRepository.save(member);

        if (!isSaved) {
            return Result.error(
                    "Unable to add member",
                    "The member could not be saved."
            );
        }

        return Result.success(
                "Member added successfully",
                "The new member has been added to the library.",
                member
        );
    }

    public Result<Void> deleteMember(String id) {
        if (id == null) {
            return Result.error(
                    "Invalid Member",
                    "Member id is required."
            );
        }

        if (!memberRepository.existsById(id)) {
            return Result.error(
                    "Member not found",
                    "The Member with the provided ID does not exist."
            );
        }

        boolean isDeleted = memberRepository.deleteById(id);

        if (!isDeleted) {
            return Result.error(
                    "Unable to delete Member",
                    "The Member could not be deleted."
            );
        }

        return Result.success(
                "Member deleted successfully",
                "The Member has been deleted."
        );
    }

    public Result<String> generateMemberId() {
        Member lastMember = memberRepository.findLast();
        String newMemberCode = getNewMemberId(0);

        if (lastMember != null) {
            int lastCode = Integer.parseInt(lastMember.getCode().substring(2));
            newMemberCode = getNewMemberId(lastCode);
        }

        return Result.success(
                "Member Code generated successfully",
                "The member code has been generated.",
                newMemberCode
        );
    }

    private String getNewMemberId(int number) {
        return "mb" + String.format("%05d", (number + 1));
    }
}
