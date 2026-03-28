package com.mobylab.springbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "favorites",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"}))
public class Favorite {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public UUID getId() {
        return id;
    }
    public Favorite setId(UUID id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Favorite setUser(User user) {
        this.user = user;
        return this;
    }

    public Book getBook() {
        return book;
    }

    public Favorite setBook(Book book) {
        this.book = book;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Favorite setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
