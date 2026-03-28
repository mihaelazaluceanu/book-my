package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.BookListing;
import com.mobylab.springbackend.entity.BorrowRequest;
import com.mobylab.springbackend.entity.Notification;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.repository.BookListingRepository;
import com.mobylab.springbackend.repository.BorrowRequestRepository;
import com.mobylab.springbackend.repository.FavoriteRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.BorrowRequestRequestDto;
import com.mobylab.springbackend.service.dto.BorrowRequestResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BorrowRequestService {

    private final BorrowRequestRepository borrowRequestRepository;
    private final BookListingRepository bookListingRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
//     private final FavoriteRepository favoriteRepository;

    public BorrowRequestService(BorrowRequestRepository borrowRequestRepository,
                                BookListingRepository bookListingRepository,
                                UserRepository userRepository,
                                NotificationService notificationService,
                                FavoriteRepository favoriteRepository) {
        this.borrowRequestRepository = borrowRequestRepository;
        this.bookListingRepository = bookListingRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        // this.favoriteRepository = favoriteRepository;
    }

    public List<BorrowRequestResponseDto> getOutgoing(UUID requesterId) {
        return borrowRequestRepository.findByRequesterId(requesterId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<BorrowRequestResponseDto> getIncoming(UUID ownerId) {
        return borrowRequestRepository.findByOwnerId(ownerId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public BorrowRequestResponseDto create(UUID requesterId,
                                           BorrowRequestRequestDto dto) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        BookListing listing = bookListingRepository.findById(dto.getListingId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Listing not found"));

        // can't borrow your own book
        if (listing.getUser().getId().equals(requesterId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "You cannot borrow your own book");
        }

        // listing must be available
        if (listing.getStatus() != BookListing.Status.AVAILABLE) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "This listing is not available");
        }

        // can't send duplicate active request
        boolean alreadyRequested = borrowRequestRepository
                .existsByRequesterIdAndRequestedBookAndStatusIn(
                        requesterId, listing,
                        List.of(BorrowRequest.Status.PENDING,
                                BorrowRequest.Status.ACCEPTED));
        if (alreadyRequested) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "You already have an active request for this listing");
        }

        BorrowRequest request = new BorrowRequest()
                .setRequester(requester)
                .setOwner(listing.getUser())
                .setRequestBook(listing)
                .setStatus(BorrowRequest.Status.PENDING);

        BorrowRequest saved = borrowRequestRepository.save(request);

        // notify the owner
        notificationService.send(
                listing.getUser(),
                saved,
                Notification.Type.REQUEST_RECEIVED,
                requester.getUsername() + " wants to borrow your copy of "
                        + listing.getBook().getTitle());

        return toDto(saved);
    }

    public BorrowRequestResponseDto updateStatus(UUID requestId,
                                                 BorrowRequest.Status newStatus,
                                                 UUID actingUserId) {
        BorrowRequest request = borrowRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Borrow request not found"));

        validateTransition(request, newStatus, actingUserId);
        applyStatusSideEffects(request, newStatus);

        request.setStatus(newStatus);
        return toDto(borrowRequestRepository.save(request));
    }

    // checks who is allowed to make which transition
    private void validateTransition(BorrowRequest request,
                                    BorrowRequest.Status newStatus,
                                    UUID actingUserId) {
        UUID ownerId = request.getOwner().getId();
        UUID requesterId = request.getRequester().getId();

        switch (newStatus) {
            case ACCEPTED, DECLINED -> {
                if (!ownerId.equals(actingUserId))
                    throw new ResponseStatusException(
                            HttpStatus.FORBIDDEN, "Only the owner can accept or decline");
                if (request.getStatus() != BorrowRequest.Status.PENDING)
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, "Request is no longer pending");
            }
            case AT_LIBRARY -> {
                if (!ownerId.equals(actingUserId))
                    throw new ResponseStatusException(
                            HttpStatus.FORBIDDEN, "Only the owner can mark book as dropped off");
                if (request.getStatus() != BorrowRequest.Status.ACCEPTED)
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, "Request must be accepted first");
            }
            case PICKED_UP, RETURNED -> {
                // librarian action — in a real app check for ADMIN role here
                if (request.getStatus() == BorrowRequest.Status.RETURNED)
                    throw new ResponseStatusException(
                            HttpStatus.CONFLICT, "This request is already completed");
            }
            case CANCELLED -> {
                if (!ownerId.equals(actingUserId) && !requesterId.equals(actingUserId))
                    throw new ResponseStatusException(
                            HttpStatus.FORBIDDEN, "Not your request");
            }
        }
    }

    // fires notifications and flips listing status when needed
    private void applyStatusSideEffects(BorrowRequest request,
                                        BorrowRequest.Status newStatus) {
        BookListing listing = request.getRequestedBook();
        User requester = request.getRequester();
        User owner = request.getOwner();
        String bookTitle = listing.getBook().getTitle();

        switch (newStatus) {
            case ACCEPTED -> {
                listing.setStatus(BookListing.Status.LENT);
                bookListingRepository.save(listing);
                notificationService.send(requester, request,
                        Notification.Type.REQUEST_ACCEPTED,
                        "Your request for " + bookTitle + " was accepted. " +
                                "The owner will drop it off at the library.");
            }

            case DECLINED -> notificationService.send(requester, request,
                    Notification.Type.REQUEST_DECLINED,
                    "Your request for " + bookTitle + " was declined.");

            case AT_LIBRARY -> notificationService.send(requester, request, Notification.Type.BOOK_AT_LIBRARY,
                    bookTitle + " is now at the library. You can come pick it up.");

            case PICKED_UP -> notificationService.send(owner, request,
                    Notification.Type.BOOK_PICKED_UP,
                    requester.getUsername() + " picked up " + bookTitle + " from the library.");

            case RETURNED -> {
                listing.setStatus(BookListing.Status.AVAILABLE);
                bookListingRepository.save(listing);
                notificationService.send(owner, request, Notification.Type.BOOK_RETURNED,
                        bookTitle + " has been returned and is available again.");
                    // notify users who favorited this book
                    notificationService.notifyFavoriteWatchers(listing);
            }

            case CANCELLED -> {
                if (listing.getStatus() == BookListing.Status.LENT) {
                    listing.setStatus(BookListing.Status.AVAILABLE);
                    bookListingRepository.save(listing);
                }
            }
        }
        }

    private BorrowRequestResponseDto toDto(BorrowRequest r) {
        return new BorrowRequestResponseDto()
                .setId(r.getId())
                .setListingId(r.getRequestedBook().getId())
                .setBookTitle(r.getRequestedBook().getBook().getTitle())
                .setBookCoverUrl(r.getRequestedBook().getBook().getCoverURL())
                .setRequesterId(r.getRequester().getId())
                .setRequesterName(r.getRequester().getUsername())
                .setOwnerId(r.getOwner().getId())
                .setOwnerName(r.getOwner().getUsername())
                .setStatus(r.getStatus().name())
                .setCreatedAt(r.getCreatedAt());
    }
}
