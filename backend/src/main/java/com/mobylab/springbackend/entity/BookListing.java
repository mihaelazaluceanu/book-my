package com.mobylab.springbackend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "book_listings")
public class BookListing {

    public enum Status { AVAILABLE, LENT, UNAVAILABLE }

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.AVAILABLE;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public UUID getId() {
        return id;
    }

    public BookListing setId(UUID id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public BookListing setUser(User user) {
        this.user = user;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public BookListing setBook(Book book) {
        this.book = book;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public BookListing setStatus(Status status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public BookListing setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
