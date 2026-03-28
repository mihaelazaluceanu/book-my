package com.mobylab.springbackend.service.dto;

import com.mobylab.springbackend.entity.Book;

import java.util.UUID;

public class BookListingResponseDto {

    private UUID id;

    private UUID bookId;

    private String bookTitle;

    private String bookAuthor;

    private Book.Genre bookGenre;

    private String bookCoverUrl;

    private UUID ownerId;

    private String ownerName;

    private String ownerCity;

    private String status;

    public UUID getId() {
        return id;
    }

    public BookListingResponseDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getBookId() {
        return bookId;
    }

    public BookListingResponseDto setBookId(UUID bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public BookListingResponseDto setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public BookListingResponseDto setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
        return this;
    }

    public Book.Genre getBookGenre() {
        return bookGenre;
    }

    public BookListingResponseDto setBookGenre(Book.Genre bookGenre) {
        this.bookGenre = bookGenre;
        return this;
    }

    public String getBookCoverUrl() {
        return bookCoverUrl;
    }

    public BookListingResponseDto setBookCoverUrl(String bookCoverUrl) {
        this.bookCoverUrl = bookCoverUrl;
        return this;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public BookListingResponseDto setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public BookListingResponseDto setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public String getOwnerCity() {
        return ownerCity;
    }

    public BookListingResponseDto setOwnerCity(String ownerCity) {
        this.ownerCity = ownerCity;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public BookListingResponseDto setStatus(String status) {
        this.status = status;
        return this;
    }
}
