package io.github.sachintha_madubashana.libraryos.service;

import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.repository.InMemoryMemberRepository;
import javafx.collections.ObservableList;

public class MemberService {
    private final InMemoryMemberRepository memberRepository;

    public MemberService(InMemoryMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ObservableList<Member> getMembers(){
        return memberRepository.getMembers();
    }

    public void removeMember(Member member){
        memberRepository.removeMember(member);
    }
}
