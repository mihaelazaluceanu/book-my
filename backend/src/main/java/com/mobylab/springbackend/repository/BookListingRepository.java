package com.mobylab.springbackend.repository;

import com.mobylab.springbackend.entity.BookListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookListingRepository extends JpaRepository<BookListing, UUID> {

    List<BookListing> findByStatus(BookListing.Status status);

    List<BookListing> findByUserId(UUID userId);

    List<BookListing> findByBookId(UUID bookId);

    List<BookListing> findByUserCity(String city);

}
