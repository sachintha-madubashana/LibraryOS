package io.github.sachintha_madubashana.libraryos.repository;

import io.github.sachintha_madubashana.libraryos.model.Member;
import io.github.sachintha_madubashana.libraryos.service.SampleData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InMemoryMemberRepository {
    private final ObservableList<Member> members;

    public InMemoryMemberRepository() {
        members = FXCollections.observableArrayList(SampleData.createMembers());
    }

    public ObservableList<Member> getMembers(){
        return members;
    }

    public void addMember(Member member){
        members.add(member);
    }

    public void removeMember(Member member){
        members.remove(member);
    }

}
