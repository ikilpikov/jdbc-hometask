package ru.pvn.libraryApp.models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {

    private long id;

    private String name;

    private Genre genre;

    private Author author;

    private int publishYear;

    public Book(String name, Genre genre, Author author, int publishYear) {
        this.name = name;
        this.genre = genre;
        this.author = author;
        this.publishYear = publishYear;
    }
}
