package io.github.sachintha_madubashana.libraryos.database.entity;

import io.github.sachintha_madubashana.libraryos.model.BorrowStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "borrowing_status")
public class BorrowingStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "status", nullable = false, unique = true)
    private String status;

    public BorrowingStatusEntity() {
    }

    public BorrowingStatusEntity(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BorrowStatus toBorrowStatus() {
        return BorrowStatus.fromString(status);
    }

    public static BorrowingStatusEntity fromBorrowStatus(BorrowStatus borrowStatus) {
        BorrowingStatusEntity entity = new BorrowingStatusEntity();
        entity.setId(borrowStatus.getValue());
        entity.setStatus(borrowStatus.name());
        return entity;
    }
}
