package com.mobylab.springbackend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "borrow_request")
public class BorrowRequest {

    public enum Status { PENDING, ACCEPTED, COMPLETED, DECLINED, CANCELLED, RETURNED, AT_LIBRARY, PICKED_UP }

    @Id
    @GeneratedValue()
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "req_book_id", nullable = false)
    private BookListing requestedBook;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.PENDING;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public UUID getId() {
        return id;
    }

    public BorrowRequest setId(UUID id) {
        this.id = id;
        return this;
    }

    public User getRequester() {
        return this.requester;
    }

    public BorrowRequest setRequester(User requester) {
        this.requester = requester;
        return this;
    }

    public User getOwner() {
        return this.owner;
    }

    public BorrowRequest setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public BookListing getRequestedBook() {
        return this.requestedBook;
    }

    public BorrowRequest setRequestBook(BookListing book) {
        this.requestedBook = book;
        return this;
    }

    public Status getStatus() {
        return this.status;
    }

    public BorrowRequest setStatus(Status status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BorrowRequest setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
