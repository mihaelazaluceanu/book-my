package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.BorrowRequest;
import com.mobylab.springbackend.entity.Review;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.repository.BorrowRequestRepository;
import com.mobylab.springbackend.repository.ReviewRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.ReviewRequestDto;
import com.mobylab.springbackend.service.dto.ReviewResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BorrowRequestRepository borrowRequestRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         BorrowRequestRepository borrowRequestRepository,
                         UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.borrowRequestRepository = borrowRequestRepository;
        this.userRepository = userRepository;
    }

    public List<ReviewResponseDto> getForUser(UUID userId) {
        return reviewRepository.findByReviewedUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ReviewResponseDto create(UUID reviewerId, ReviewRequestDto dto) {
        BorrowRequest borrowRequest = borrowRequestRepository
                .findById(dto.getBorrowRequestId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Borrow request not found"));

        // can only review after the book is returned
        if (borrowRequest.getStatus() != BorrowRequest.Status.RETURNED) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You can only leave a review after the book has been returned");
        }

        // reviewer must be one of the two parties
        UUID ownerId = borrowRequest.getOwner().getId();
        UUID requesterId = borrowRequest.getRequester().getId();
        if (!reviewerId.equals(ownerId) && !reviewerId.equals(requesterId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You were not part of this exchange");
        }

        // one review per person per borrow
        if (reviewRepository.existsByBorrowRequestIdAndReviewerId(
                dto.getBorrowRequestId(), reviewerId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "You have already reviewed this exchange");
        }

        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        User reviewedUser = userRepository.findById(dto.getReviewedUserId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Reviewed user not found"));

        Review review = new Review()
                .setBorrowRequest(borrowRequest)
                .setReviewer(reviewer)
                .setReviewedUser(reviewedUser)
                .setRating(dto.getRating())
                .setComment(dto.getComment());

        return toDto(reviewRepository.save(review));
    }

    private ReviewResponseDto toDto(Review r) {
        return new ReviewResponseDto()
                .setId(r.getId())
                .setReviewerName(r.getReviewer().getUsername())
                .setRating(r.getRating())
                .setComment(r.getComment())
                .setCreatedAt(r.getCreatedAt());
    }
}
