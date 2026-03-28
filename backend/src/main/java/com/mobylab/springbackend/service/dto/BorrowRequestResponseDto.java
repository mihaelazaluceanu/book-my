package com.mobylab.springbackend.service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class BorrowRequestResponseDto {

    private UUID id;

    private UUID listingId;

    private String bookTitle;

    private String bookCoverUrl;

    private UUID requesterId;

    private String requesterName;

    private UUID ownerId;

    private String ownerName;

    private String message;

    private String status;

    private LocalDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public BorrowRequestResponseDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public UUID getListingId() {
        return listingId;
    }

    public BorrowRequestResponseDto setListingId(UUID listingId) {
        this.listingId = listingId;
        return this;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public BorrowRequestResponseDto setBookTitle(String t) {
        this.bookTitle = t;
        return this;
    }

    public String getBookCoverUrl() {
        return bookCoverUrl;
    }

    public BorrowRequestResponseDto setBookCoverUrl(String u) {
        this.bookCoverUrl = u;
        return this;
    }

    public UUID getRequesterId() {
        return requesterId;
    }

    public BorrowRequestResponseDto setRequesterId(UUID id) {
        this.requesterId = id;
        return this;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public BorrowRequestResponseDto setRequesterName(String n) {
        this.requesterName = n;
        return this;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public BorrowRequestResponseDto setOwnerId(UUID id) {
        this.ownerId = id;
        return this;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public BorrowRequestResponseDto setOwnerName(String n) {
        this.ownerName = n;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BorrowRequestResponseDto setMessage(String m) {
        this.message = m;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public BorrowRequestResponseDto setStatus(String s) {
        this.status = s;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BorrowRequestResponseDto setCreatedAt(LocalDateTime t) {
        this.createdAt = t;
        return this;
    }
}
