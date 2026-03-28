package com.mobylab.springbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "books")
public class Book {
    public enum Genre { ADVENTURE, ACTION, FANTASY, FICTION, HISTORY, HORROR, MYSTERY, ROMANCE, SCIENCE, SF, THRILLER, OTHER }

    @Id
    @GeneratedValue()
    private UUID id;

    private String title;

    private String author;

    private Genre genre;

    private String coverURL;

    private String description;

    public UUID getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Genre getGenre() {
        return this.genre;
    }

    public Book setGenre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public String getCoverURL() {
        return this.coverURL;
    }

    public Book setCoverURL(String coverURL) {
        this.coverURL = coverURL;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Book setDescription(String description) {
        this.description = description;
        return this;
    }
}
