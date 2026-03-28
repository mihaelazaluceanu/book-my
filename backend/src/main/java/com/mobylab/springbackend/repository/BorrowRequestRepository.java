package com.mobylab.springbackend.repository;

import com.mobylab.springbackend.entity.BookListing;
import com.mobylab.springbackend.entity.BorrowRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, UUID> {

    List<BorrowRequest> findByRequesterId(UUID requesterId);

    List<BorrowRequest> findByOwnerId(UUID ownerId);

    List<BorrowRequest> findAllByStatus(BorrowRequest.Status status);

    List<BorrowRequest> findByRequesterIdAndStatus(UUID requesterId, BorrowRequest.Status status);

    List<BorrowRequest> findByOwnerIdAndStatus(UUID ownerId, BorrowRequest.Status status);

    boolean existsByRequesterIdAndRequestedBookAndStatusIn(
            UUID requesterId, BookListing requestedBook, List<BorrowRequest.Status> statuses);
}
