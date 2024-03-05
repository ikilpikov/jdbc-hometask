DROP TABLE IF EXISTS genre;
CREATE TABLE IF NOT EXISTS genre
(
    id   BIGSERIAL auto_increment  NOT NULL PRIMARY KEY,
    name VARCHAR(60)
);

DROP TABLE IF EXISTS author;
CREATE TABLE IF NOT EXISTS author
(
    id   BIGSERIAL auto_increment NOT NULL PRIMARY KEY,
    name VARCHAR(60)
);

DROP TABLE IF EXISTS book;
CREATE TABLE IF NOT EXISTS book
(
    id           BIGSERIAL NOT NULL PRIMARY KEY,
    name         VARCHAR(60),
    genre        INT,
    author       INT,
    publish_year INT,
    FOREIGN KEY (author) REFERENCES author (id),
    FOREIGN KEY (genre) REFERENCES genre (id)
);
