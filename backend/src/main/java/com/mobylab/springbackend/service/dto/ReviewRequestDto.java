package com.mobylab.springbackend.service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ReviewRequestDto {

    @NotNull
    private UUID borrowRequestId;

    @NotNull
    private UUID reviewedUserId;

    @NotNull @Min(1) @Max(5)
    private Integer rating;

    private String comment;

    public UUID getBorrowRequestId() {
        return borrowRequestId;
    }

    public ReviewRequestDto setBorrowRequestId(UUID id) {
        this.borrowRequestId = id;
        return this;
    }

    public UUID getReviewedUserId() {
        return reviewedUserId;
    }

    public ReviewRequestDto setReviewedUserId(UUID id) {
        this.reviewedUserId = id;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public ReviewRequestDto setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public ReviewRequestDto setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
