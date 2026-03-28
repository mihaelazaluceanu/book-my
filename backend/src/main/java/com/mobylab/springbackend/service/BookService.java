package com.mobylab.springbackend.service;

import com.mobylab.springbackend.entity.Book;
import com.mobylab.springbackend.repository.BookRepository;
import com.mobylab.springbackend.service.dto.BookDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepsoitory) {
        this.bookRepository = bookRepsoitory;
    }

    public List<BookDto> getBooksByAuthor(String author) {
        return bookRepository.getBooksByAuthor(author)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> findBooksByTitle(String title) {
        return bookRepository.findBooksByTitle(title)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> getBooksByGenre(Book.Genre genre) {
        return bookRepository.getBooksByGenre(genre)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Book addBook(BookDto bookDto) {
        Book book = new Book()
                .setAuthor(bookDto.getAuthor())
                .setTitle(bookDto.getTitle())
                .setCoverURL(bookDto.getCoverURL())
                .setGenre(bookDto.getGenre())
                .setDescription(bookDto.getDescription());

        return bookRepository.save(book);
    }

    private BookDto toDto(Book book) {
        return new BookDto()
                .setAuthor(book.getAuthor())
                .setTitle(book.getTitle())
                .setCoverURL(book.getCoverURL())
                .setGenre(book.getGenre())
                .setDescription((book.getDescription()));
    }
}
