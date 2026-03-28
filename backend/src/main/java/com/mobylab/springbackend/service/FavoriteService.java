package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.Book;
import com.mobylab.springbackend.entity.Favorite;
import com.mobylab.springbackend.entity.User;
import com.mobylab.springbackend.repository.BookRepository;
import com.mobylab.springbackend.repository.FavoriteRepository;
import com.mobylab.springbackend.repository.UserRepository;
import com.mobylab.springbackend.service.dto.FavoriteRequestDto;
import com.mobylab.springbackend.service.dto.FavoriteResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public FavoriteService(FavoriteRepository favoriteRepository,
                           BookRepository bookRepository,
                           UserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public List<FavoriteResponseDto> getByUser(UUID userId) {
        return favoriteRepository.findByUserId(userId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public FavoriteResponseDto add(UUID userId, FavoriteRequestDto dto) {
        if (favoriteRepository.existsByUserIdAndBookId(userId, dto.getBookId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Book is already in your favorites");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Book not found"));

        Favorite favorite = new Favorite()
                .setUser(user)
                .setBook(book);

        return toDto(favoriteRepository.save(favorite));
    }

    public void remove(UUID userId, UUID bookId) {
        if (!favoriteRepository.existsByUserIdAndBookId(userId, bookId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Favorite not found");
        }
        favoriteRepository.deleteByUserIdAndBookId(userId, bookId);
    }

    private FavoriteResponseDto toDto(Favorite f) {
        return new FavoriteResponseDto()
                .setId(f.getId())
                .setBookId(f.getBook().getId())
                .setBookTitle(f.getBook().getTitle())
                .setBookAuthor(f.getBook().getAuthor())
                .setBookCoverUrl(f.getBook().getCoverURL());
    }
}
