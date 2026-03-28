package com.mobylab.springbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification {

    public enum Type {
        REQUEST_RECEIVED,
        REQUEST_ACCEPTED,
        REQUEST_DECLINED,
        BOOK_AT_LIBRARY,
        BOOK_PICKED_UP,
        BOOK_RETURNED,
        FAVORITE_AVAILABLE
    }

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_request_id")
    private BorrowRequest borrowRequest;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Type type;

    @Column(nullable = false)
    private String message;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters
    public UUID getId() {
        return id;
    }
    public Notification setId(UUID id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Notification setUser(User user) {
        this.user = user;
        return this;
    }

    public BorrowRequest getBorrowRequest() {
        return borrowRequest;
    }

    public Notification setBorrowRequest(BorrowRequest borrowRequest) {
        this.borrowRequest = borrowRequest;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Notification setType(Type type) {
        this.type = type;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Notification setMessage(String message) {
        this.message = message;
        return this;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public Notification setIsRead(Boolean isRead) {
        this.isRead = isRead;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Notification setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
