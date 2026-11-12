package io.github.sachintha_madubashana.libraryos.model;

import java.io.Serializable;

public class Book implements Serializable {

    private Integer id;
    private String code;
    private String title;
    private String author;
    private Integer publishedYear;
    private Category category;
    private int totalCopies;
    private int availableCopies;

    public Book() {
    }

    public Book(Integer id, String code, String title, String author, Integer publishedYear, Category category, int totalCopies, int availableCopies) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public Book(String code, String title, String author, Integer publishedYear, Category category, int totalCopies, int availableCopies) {
        this.code = code;
        this.title = title;
        this.author = author;
        this.publishedYear = publishedYear;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Integer getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(Integer publishedYear) {
        this.publishedYear = publishedYear;
    }
}