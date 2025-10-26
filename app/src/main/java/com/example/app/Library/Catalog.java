package com.example.app.Library;

import java.util.Collections;
import java.util.Vector;

public class Catalog {

    private Vector<Almanac> almanacs;

    private Vector<Book> books;

    private Vector<Newspaper> newspapers;

    public Catalog() {
        this.almanacs = new Vector<>();
        this.books = new Vector<>();
        this.newspapers = new Vector<>();
    }

    public void TestInitialization() {
        Almanac[] almanacsArray = new Almanac[10];
        for (int i = 0; i < almanacsArray.length; i++) {
            almanacsArray[i] = new Almanac("Almanac " + (i + 1), "Annotation " + (i + 1), "Author " + (i + 1), new Vector<>());
        }

        Collections.addAll(this.almanacs, almanacsArray);

        Book[] booksArray = new Book[10];
        for (int i = 0; i < booksArray.length; i++) {
            booksArray[i] = new Book("Book " + (i + 1), "Author " + (i + 1), "Publisher " + (i + 1), 2000 + i, "ISBN" + (i + 1));
        }

        Collections.addAll(this.books, booksArray);

        Newspaper[] newspapersArray = new Newspaper[10];
        for (int i = 0; i < newspapersArray.length; i++) {
            newspapersArray[i] = new Newspaper("Newspaper " + (i + 1), java.time.LocalDate.now());
        }

        Collections.addAll(this.newspapers, newspapersArray);
    }

    public void showInfo() {
        System.out.println("Catalog Information:");
        System.out.println("Number of Almanacs: " + almanacs.size());
        System.out.println("Number of Books: " + books.size());
        System.out.println("Number of Newspapers: " + newspapers.size());

        System.out.println("\nAlmanacs:");
        for (Almanac almanac : almanacs) {
            almanac.showInfo();
            System.out.println();
        }

        System.out.println("Books:");
        for (Book book : books) {
            book.showInfo();
            System.out.println();
        }

        System.out.println("Newspapers:");
        for (Newspaper newspaper : newspapers) {
            newspaper.showInfo();
            System.out.println();
        }
    }

    public void addAlmanac(Almanac almanac) {
        this.almanacs.add(almanac);
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void addNewspaper(Newspaper newspaper) {
        this.newspapers.add(newspaper);
    }

    public void add(Object item) {
        if (item instanceof Almanac) {
            addAlmanac((Almanac) item);
        } else if (item instanceof Book) {
            addBook((Book) item);
        } else if (item instanceof Newspaper) {
            addNewspaper((Newspaper) item);
        } else {
            throw new IllegalArgumentException("Unsupported item type");
        }
    }

    public Object delete(String title) {
        for (Almanac almanac : almanacs) {
            if (almanac.getTitle().equals(title)) {
                almanacs.remove(almanac);
                return almanac;
            }
        }

        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                books.remove(book);
                return book;
            }
        }

        for (Newspaper newspaper : newspapers) {
            if (newspaper.getName().equals(title)) {
                newspapers.remove(newspaper);
                return newspaper;
            }
        }
        return null;
    }

    public Object findByTitle(String title) {
        for (Almanac almanac : almanacs) {
            if (almanac.getTitle().equals(title)) {
                return almanac;
            }
        }

        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }

        for (Newspaper newspaper : newspapers) {
            if (newspaper.getName().equals(title)) {
                return newspaper;
            }
        }
        return null;
    }

    public Object findByAuthor(String author) {
        for (Almanac almanac : almanacs) {
            if (almanac.getAuthor().equals(author)) {
                return almanac;
            }
        }

        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                return book;
            }
        }
        return null;

    }

    public Object[] findByAnnotation(String annotation) {
        Vector<Object> results = new Vector<>();

        for (Almanac almanac : almanacs) {
            if (almanac.getAnotation().contains(annotation)) {
                results.add(almanac);
            }
        }

        for (Book book : books) {
            if (book.getAnnotation().contains(annotation)) {
                results.add(book);
            }
        }

        return results.toArray();
    }

    public Newspaper[] findByHeader(String header) {
        Vector<Newspaper> results = new Vector<>();

        for (Newspaper newspaper : newspapers) {
            if (newspaper.getHeaders().contains(header)) {
                results.add(newspaper);
            }
        }

        return results.toArray(new Newspaper[0]);
    }

    public Book[] getByLessThanPages(int pages) {
        Vector<Book> results = new Vector<>();

        for (Book book : books) {
            if (book.getPages() < pages) {
                results.add(book);
            }
        }

        return results.toArray(new Book[0]);
    }
}