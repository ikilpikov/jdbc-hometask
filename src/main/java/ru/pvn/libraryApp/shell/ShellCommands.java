package ru.pvn.libraryApp.shell;

import org.h2.tools.Console;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.pvn.libraryApp.dao.AuthorDaoJdbc;
import ru.pvn.libraryApp.dao.BookDaoJdbc;
import ru.pvn.libraryApp.dao.GenreDaoJdbc;
import ru.pvn.libraryApp.models.Author;
import ru.pvn.libraryApp.models.Book;
import ru.pvn.libraryApp.models.Genre;

import java.sql.SQLException;
import java.text.ParseException;

@ShellComponent
public class ShellCommands {

    private final AuthorDaoJdbc jdbcAuthor;
    private final GenreDaoJdbc jdbcGenre;
    private final BookDaoJdbc jdbcBook;

    public ShellCommands(AuthorDaoJdbc jdbcAuthor, GenreDaoJdbc jdbcGenreDao, BookDaoJdbc jdbcBookDao) {
        this.jdbcAuthor = jdbcAuthor;
        this.jdbcGenre = jdbcGenreDao;
        this.jdbcBook = jdbcBookDao;
    }

    @ShellMethod(value = "run H2 console", key = {"console"})
    public String runConsoleH2() throws SQLException {
        Console.main();
        return "Консоль H2 запущена";
    }

    @ShellMethod(value = "add Author to DB", key = {"add-author"})
    public String addAuthor(@ShellOption String name) throws SQLException, ParseException {
        Author author = null;
        author = new Author(name);
        jdbcAuthor.create(author);
        return "Автор добавлен";
    }

    @ShellMethod(value = "get author from DB", key = {"get-author"})
    public String getAuthor(@ShellOption long id) throws SQLException {
        Author author = jdbcAuthor.getById(id);
        return author.toString();
    }


    @ShellMethod(value = "add Genre to DB", key = {"add-genre"})
    public String addGenre(@ShellOption String name) throws SQLException {
        Genre genre = new Genre(name);
        jdbcGenre.create(genre);
        return "Жанр добавлен";
    }

    @ShellMethod(value = "get genre from DB", key = {"get-genre"})
    public String getGenre(@ShellOption long id) throws SQLException {
        Genre genre = jdbcGenre.getById(id);
        return genre.toString();
    }

    @ShellMethod(value = "get book from DB", key = {"get-book"})
    public String getBook(@ShellOption long id) throws SQLException {
        Book book = jdbcBook.getById(id);
        return book.toString();
    }

    @ShellMethod(value = "delete book from DB", key = {"delete-book"})
    public String deleteBook(@ShellOption long id) throws SQLException {
        jdbcBook.deleteById(id);
        return "Книга #" + id + " удалена";
    }

    @ShellMethod(value = "update book in DB", key = {"update-book"})
    public String updateBookByName(@ShellOption String id,
                                 @ShellOption String name,
                                 @ShellOption String genre,
                                 @ShellOption String author,
                                 @ShellOption int publishYear) throws SQLException {

        Book book = new Book();
        book.setId(Long.parseLong(id));
        book.setName(name);
        book.setGenre(jdbcGenre.getByName(genre));
        book.setAuthor(jdbcAuthor.getByName(author));
        book.setPublishYear(publishYear);

        jdbcBook.update(book);
        return "Книга c" + book + " обновлена!";
    }

    @ShellMethod(value = "add book to DB", key = {"add-book"})
    public String addBook(@ShellOption String name,
                          @ShellOption String genre,
                          @ShellOption String author,
                          @ShellOption int publishYear) throws SQLException {
        Book book = new Book();
        book.setName(name);
        book.setGenre(jdbcGenre.getByName(genre));
        book.setAuthor(jdbcAuthor.getByName(author));
        book.setPublishYear(publishYear);

        jdbcBook.create(book);
        return "Книга " + book.toString() + "добавлена!";
    }

}