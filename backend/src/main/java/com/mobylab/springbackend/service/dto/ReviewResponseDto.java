package com.mobylab.springbackend.service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReviewResponseDto {

    private UUID id;
    private String reviewerName;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public ReviewResponseDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public ReviewResponseDto setReviewerName(String n) {
        this.reviewerName = n;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public ReviewResponseDto setRating(Integer r) {
        this.rating = r;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public ReviewResponseDto setComment(String c) {
        this.comment = c;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public ReviewResponseDto setCreatedAt(LocalDateTime t) {
        this.createdAt = t;
        return this;
    }
}
