package com.mobylab.springbackend.service.dto;

import java.util.UUID;

public class FavoriteResponseDto {

    private UUID id;

    private UUID bookId;

    private String bookTitle;

    private String bookAuthor;

    private String bookCoverUrl;

    public UUID getId() {
        return id;
    }

    public FavoriteResponseDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getBookId() {
        return bookId;
    }

    public FavoriteResponseDto setBookId(UUID bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public FavoriteResponseDto setBookTitle(String t) {
        this.bookTitle = t;
        return this;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public FavoriteResponseDto setBookAuthor(String a) {
        this.bookAuthor = a;
        return this;
    }

    public String getBookCoverUrl() {
        return bookCoverUrl;
    }

    public FavoriteResponseDto setBookCoverUrl(String u) {
        this.bookCoverUrl = u;
        return this;
    }
}
