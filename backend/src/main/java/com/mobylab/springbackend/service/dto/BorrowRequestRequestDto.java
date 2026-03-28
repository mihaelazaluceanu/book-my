package com.mobylab.springbackend.service.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class BorrowRequestRequestDto {

    @NotNull(message = "Listing ID is required")
    private UUID listingId;

    private String message;

    public UUID getListingId() {
        return listingId;
    }

    public String getMessage() {
        return message;
    }

    public BorrowRequestRequestDto setListingId(UUID listingId) {
        this.listingId = listingId;
        return this;
    }

    public BorrowRequestRequestDto setMessage(String message) {
        this.message = message;
        return this;
    }
}
