package ru.pvn.libraryApp.dao;

import ru.pvn.libraryApp.models.Genre;

public interface GenreDao {
    Genre getById(long id);

    Genre getByName(String name);

    void create(Genre genre);
}
