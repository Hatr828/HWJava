package com.example.app;

public class Book {

    private String title;
    private String authorFullName;
    private int year;
    private String publisher;
    private String genre;
    private int pages;

    public Book(String title, String authorFullName, int year, String publisher, String genre, int pages) {
        this.title = title;
        this.authorFullName = authorFullName;
        this.year = year;
        this.publisher = publisher;
        this.genre = genre;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String v) {
        title = v;
    }

    public String getAuthorFullName() {
        return authorFullName;
    }

    public void setAuthorFullName(String v) {
        authorFullName = v;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int v) {
        year = v;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String v) {
        publisher = v;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String v) {
        genre = v;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int v) {
        pages = v;
    }
}
