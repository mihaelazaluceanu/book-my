package com.mobylab.springbackend.repository;

import com.mobylab.springbackend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> getBooksByAuthor(String author);

    List<Book> findBooksByTitle(String title);

    List<Book> getBooksByGenre(Book.Genre genre);

    Optional<Book> findBookByTitleAndAuthor(String title, String author);
}
