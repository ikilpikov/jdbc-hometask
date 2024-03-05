package ru.pvn.libraryApp.models;


import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Author {

    private long id;

    private String name;

    public Author(String name) {
        this.name = name;
    }

}
