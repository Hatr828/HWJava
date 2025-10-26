package com.example.app.Library;

import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@ToString
@AllArgsConstructor
@Builder
public class Book {
    private String title;

    private String author;

    private String genre;

    private int pages;

    private String annotation;

    public void showInfo() {
        System.out.println("Book Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Genre: " + genre);
        System.out.println("Pages: " + pages);
        System.out.println("Annotation: " + annotation);
    }
}
