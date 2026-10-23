package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.common.result.Result;
import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.repository.MemberRepository;

import java.util.List;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Result<List<Member>> getMembers(){
        List<Member> members = memberRepository.findAll();

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

        if (memberRepository.existsById(member.getId())) {
            return Result.error(
                    "Duplicate member ID",
                    "A member with this ID already exists."
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
        List<Member> members = memberRepository.findAll();
        String newMemberId = getNewMemberId(0);

        if (!members.isEmpty()) {
            Member lastMember = members.get(members.size() - 1);
            int memberCount = Integer.parseInt(lastMember.getId().substring(2));
            newMemberId = getNewMemberId(memberCount);
        }

        return Result.success(
                "Member id generated successfully",
                "The member id has been generated.",
                newMemberId
        );
    }

    private String getNewMemberId(int number) {
        return "MB" + String.format("%05d", (number + 1));
    }
}
