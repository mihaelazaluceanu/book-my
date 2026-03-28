package com.mobylab.springbackend.service.dto;

import com.mobylab.springbackend.entity.Book;

public class BookDto {

    private String title;

    private String author;

    private Book.Genre genre;

    private String coverURL;

    private String description;

    public String getTitle() {
        return title;
    }

    public BookDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Book.Genre getGenre() {
        return this.genre;
    }

    public BookDto setGenre(Book.Genre genre) {
        this.genre = genre;
        return this;
    }

    public String getCoverURL() {
        return this.coverURL;
    }

    public BookDto setCoverURL(String coverURL) {
        this.coverURL = coverURL;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public BookDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
