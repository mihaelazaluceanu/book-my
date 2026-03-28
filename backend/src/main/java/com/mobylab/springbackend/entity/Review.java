package com.mobylab.springbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reviews", uniqueConstraints = @UniqueConstraint(columnNames = {"borrow_request_id", "reviewer_id"}))
public class Review {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_request_id", nullable = false)
    private BorrowRequest borrowRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_user_id", nullable = false)
    private User reviewedUser;

    @Min(1) @Max(5)
    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public UUID getId() {
        return id;
    }

    public Review setId(UUID id) {
        this.id = id;
        return this;
    }

    public BorrowRequest getBorrowRequest() {
        return borrowRequest;
    }

    public Review setBorrowRequest(BorrowRequest borrowRequest) {
        this.borrowRequest = borrowRequest;
        return this;
    }

    public User getReviewer() {
        return reviewer;
    }

    public Review setReviewer(User reviewer) {
        this.reviewer = reviewer;
        return this;
    }

    public User getReviewedUser() {
        return reviewedUser;
    }

    public Review setReviewedUser(User reviewedUser) {
        this.reviewedUser = reviewedUser;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public Review setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Review setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Review setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
