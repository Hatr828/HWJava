package Library;

import java.util.Vector;

public class Almanac {
    private String title;

    private String anotation;

    private String author;

    private Vector<Book> books;

    public Almanac(String title, String anotation, String author, Vector<Book> books) {
        this.title = title;
        this.anotation = anotation;
        this.author = author;
        this.books = books != null ? books : new Vector<>();
    }

    public Almanac() {
        this("", "", "", new Vector<>());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnotation() {
        return anotation;
    }

    public void setAnotation(String anotation) {
        this.anotation = anotation;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Vector<Book> getBooks() {
        return books;
    }

    public void setBooks(Vector<Book> books) {
        this.books = books;
    }

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
