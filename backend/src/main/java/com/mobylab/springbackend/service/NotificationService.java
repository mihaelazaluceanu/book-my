package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.BookListing;
import com.mobylab.springbackend.entity.BorrowRequest;
import com.mobylab.springbackend.entity.Notification;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.repository.FavoriteRepository;
import com.mobylab.springbackend.repository.NotificationRepository;
import com.mobylab.springbackend.service.dto.NotificationDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final FavoriteRepository favoriteRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(NotificationRepository notificationRepository,
                               FavoriteRepository favoriteRepository,
                               SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.favoriteRepository = favoriteRepository;
        this.messagingTemplate = messagingTemplate;
    }

    // called internally by BorrowRequestService
    public void send(User recipient, BorrowRequest borrowRequest,
                     Notification.Type type, String message) {
        Notification notification = new Notification()
                .setUser(recipient)
                .setBorrowRequest(borrowRequest)
                .setType(type)
                .setMessage(message)
                .setIsRead(false);

        Notification saved = notificationRepository.save(notification);

        // push over WebSocket to the recipient in real time
        messagingTemplate.convertAndSendToUser(
                recipient.getId().toString(),
                "/queue/notifications",
                toDto(saved));
    }

    // called when a book is returned — notify everyone who favorited it
    public void notifyFavoriteWatchers(BookListing listing) {
        favoriteRepository.findByBookId(listing.getBook().getId())
                .forEach(favorite -> send(
                        favorite.getUser(),
                        null,
                        Notification.Type.FAVORITE_AVAILABLE,
                        "\"" + listing.getBook().getTitle()
                                + "\" is now available to borrow!"));
    }

    public List<NotificationDto> getForUser(UUID userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public long countUnread(UUID userId) {
        return notificationRepository.countByUserIdAndIsRead(userId, false);
    }

    public void markAllRead(UUID userId) {
        List<Notification> unread = notificationRepository
                .findByUserIdAndIsRead(userId, false);
        unread.forEach(n -> n.setIsRead(true));
        notificationRepository.saveAll(unread);
    }

    public void markRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Notification not found"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    private NotificationDto toDto(Notification n) {
        return new NotificationDto()
                .setId(n.getId())
                .setType(n.getType().name())
                .setMessage(n.getMessage())
                .setIsRead(n.getIsRead())
                .setBorrowRequestId(
                        n.getBorrowRequest() != null
                                ? n.getBorrowRequest().getId()
                                : null)
                .setCreatedAt(n.getCreatedAt());
    }
}
