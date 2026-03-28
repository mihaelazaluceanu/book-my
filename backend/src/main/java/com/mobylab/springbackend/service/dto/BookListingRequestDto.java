package com.mobylab.springbackend.service.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class BookListingRequestDto {

    @NotNull(message = "Book ID is required")
    private UUID bookId;

    public UUID getBookId() {
        return bookId;
    }

    public BookListingRequestDto setBookId(UUID bookId) {
        this.bookId = bookId;
        return this;
    }
}
