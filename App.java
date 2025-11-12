package main;

import Library.*;

public class App {

    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        catalog.TestInitialization();
        catalog.showInfo();

        System.out.println("Books with less than 1000 pages:");
        for (Book book : catalog.getByLessThanPages(1000)) {
            book.showInfo();
        }
    }

}
