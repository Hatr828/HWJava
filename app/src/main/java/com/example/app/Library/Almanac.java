package com.example.app.Library;

import java.util.Vector;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Almanac {
    private String title;

    private String anotation;

    private String author;
    
    private Vector<Book> books;

    public void showInfo() {
        System.out.println("Almanac Title: " + title);
        System.out.println("Anotation: " + anotation);
        System.out.println("Author: " + author);
        System.out.println("Books in Almanac:");
        for (Book book : books) {
            book.showInfo();
            System.out.println();
        }
    }
}
