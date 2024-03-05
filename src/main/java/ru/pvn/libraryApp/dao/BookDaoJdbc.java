package ru.pvn.libraryApp.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.pvn.libraryApp.models.Author;
import ru.pvn.libraryApp.models.Book;
import ru.pvn.libraryApp.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    private final String selectBooks = "SELECT bk.id book_id,\n" +
            "                       bk.name book_name, \n" +
            "                       bk.publish_year book_publish_year, \n" +
            "                       gnr.id genre_id, \n" +
            "                       gnr.name genre_name, \n" +
            "                       aut.id author_id, \n" +
            "                       aut.name author_name \n" +
            "        FROM BOOK bk\n" +
            "        JOIN AUTHOR aut ON aut.id = bk.author\n" +
            "        JOIN GENRE gnr ON gnr.id = bk.genre\n";

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Book getById(long id) {
        final Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return jdbc.query(selectBooks +
                        "WHERE bk.id = :id",
                Map.of("id", id),
                new BookExtractor());
    }

    @Override
    public void create(Book book) {
        jdbc.update("insert into BOOK (" +
                        " NAME,\n" +
                        " GENRE,\n" +
                        " AUTHOR,\n" +
                        " PUBLISH_YEAR\n" +
                        ")" +
                        "   values ( " +
                        " :name,\n" +
                        " :genre,\n" +
                        " :author,\n" +
                        " :publish_year\n" +
                        ")",
                Map.of("name", book.getName(),
                        "genre", book.getGenre().getId(),
                        "author", book.getAuthor().getId(),
                        "publish_year", book.getPublishYear()));
    }

    @Override
    public void update(Book book) {
            jdbc.update("update BOOK  SET" +
                            " NAME = :name,\n" +
                            " GENRE = :genre,\n" +
                            " AUTHOR = :author,\n" +
                            " PUBLISH_YEAR = :publish_year\n" +
                            "WHERE ID = :id",
                    Map.of("id", book.getId(),
                            "name", book.getName(),
                            "genre", book.getGenre().getId(),
                            "author", book.getAuthor().getId(),
                            "publish_year", book.getPublishYear()));
    }


    @Override
    public void deleteById(long id) {
        jdbc.update("DELETE FROM BOOK WHERE id = :id", Map.of("id", id));
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(selectBooks +
                " ORDER BY book_id", new BooksExtractor());
    }


    private class BookExtractor implements ResultSetExtractor<Book> {
        @Override
        public Book extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            List<Book> books = new BooksExtractor().extractData(resultSet);
            if (books.isEmpty()) return null;
            else return books.iterator().next();
        }
    }

    private class BooksExtractor implements ResultSetExtractor<List<Book>> {
        @Override
        public List<Book> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
            List<Book> books = new ArrayList<>();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setName(resultSet.getString("name"));
                book.setPublishYear(resultSet.getInt("publish_year"));

                Genre genre = new Genre();
                genre.setId(resultSet.getInt("genre_id"));
                genre.setName(resultSet.getString("genre_name"));
                book.setGenre(genre);

                Author author = new Author();
                author.setId(resultSet.getInt("author_id"));
                author.setName(resultSet.getString("author_name"));
                book.setAuthor(author);

                books.add(book);
            }

            return books;
        }
    }
}

