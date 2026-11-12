package io.github.sachintha_madubashana.libraryos.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Member implements Serializable {

    private Integer id;
    private String code;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private LocalDate joinedDate;

    public Member() {
    }

    public Member(Integer id,String code, String fullName, String email, String phone, String address, LocalDate joinedDate) {
        this.id = id;
        this.code = code;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.joinedDate = joinedDate;
    }

    public Member(String code, String fullName, String email, String phone, String address, LocalDate joinedDate) {
        this.code = code;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.joinedDate = joinedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    @Override
    public String toString() {
        return code.toUpperCase() + " - " + fullName;
    }
}
