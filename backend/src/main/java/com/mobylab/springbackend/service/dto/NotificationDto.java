package com.mobylab.springbackend.service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationDto {

    private UUID id;
    private String type;
    private String message;
    private Boolean isRead;
    private UUID borrowRequestId;
    private LocalDateTime createdAt;

    public UUID getId() {
        return id;
    }

    public NotificationDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public NotificationDto setType(String type) {
        this.type = type;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public NotificationDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public NotificationDto setIsRead(Boolean isRead) {
        this.isRead = isRead;
        return this;
    }

    public UUID getBorrowRequestId() {
        return borrowRequestId;
    }

    public NotificationDto setBorrowRequestId(UUID id) {
        this.borrowRequestId = id;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public NotificationDto setCreatedAt(LocalDateTime t) {
        this.createdAt = t;
        return this;
    }
}
