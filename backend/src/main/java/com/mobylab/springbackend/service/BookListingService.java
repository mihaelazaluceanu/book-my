package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.Book;
import com.mobylab.springbackend.entity.BookListing;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.repository.BookListingRepository;
import com.mobylab.springbackend.repository.BookRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.BookListingRequestDto;
import com.mobylab.springbackend.service.dto.BookListingResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookListingService {

    private final BookListingRepository bookListingRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookListingService(BookListingRepository bookListingRepository,
                              BookRepository bookRepository,
                              UserRepository userRepository) {
        this.bookListingRepository = bookListingRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<BookListingResponseDto> getAll() {
        return bookListingRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<BookListingResponseDto> getAvailable() {
        return bookListingRepository.findByStatus(BookListing.Status.AVAILABLE)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<BookListingResponseDto> getByUser(UUID userId) {
        return bookListingRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public BookListingResponseDto create(UUID userId, BookListingRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Book not found"));

        BookListing listing = new BookListing()
                .setUser(user)
                .setBook(book)
                .setStatus(BookListing.Status.AVAILABLE);

        return toDto(bookListingRepository.save(listing));
    }

    public BookListingResponseDto updateStatus(UUID listingId, BookListing.Status newStatus, UUID requestingUserId) {
        BookListing listing = bookListingRepository.findById(listingId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Listing not found"));

        // only the owner can change their listing status
        if (!listing.getUser().getId().equals(requestingUserId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You do not own this listing");
        }

        listing.setStatus(newStatus);
        return toDto(bookListingRepository.save(listing));
    }

    public void delete(UUID listingId, UUID requestingUserId) {
        BookListing listing = bookListingRepository.findById(listingId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Listing not found"));

        if (!listing.getUser().getId().equals(requestingUserId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You do not own this listing");
        }

        bookListingRepository.delete(listing);
    }

    private BookListingResponseDto toDto(BookListing l) {
        return new BookListingResponseDto()
                .setId(l.getId())
                .setBookId(l.getBook().getId())
                .setBookTitle(l.getBook().getTitle())
                .setBookAuthor(l.getBook().getAuthor())
                .setBookGenre(l.getBook().getGenre())
                .setBookCoverUrl(l.getBook().getCoverURL())
                .setOwnerId(l.getUser().getId())
                .setOwnerName(l.getUser().getUsername())
                .setOwnerCity(l.getUser().getCity())
                .setStatus(l.getStatus().name());
    }
}
