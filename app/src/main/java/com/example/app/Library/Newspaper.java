package com.example.app.Library;

import lombok.Getter;
import lombok.Setter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ToString
public class Newspaper {
    @Getter @Setter @NonNull
    private String name;

    @Getter @Setter @NonNull
    private LocalDate datePublished;

    private final List<String> headers = new ArrayList<>();

    public Newspaper(String name, LocalDate datePublished) {
        this.name = name;
        this.datePublished = datePublished;
    }

    public Newspaper(String name, LocalDate datePublished, List<String> headers) {
        this(name, datePublished);
        if (headers != null) this.headers.addAll(headers);
    }

    public void addHeader(String header) {
        if (header != null) this.headers.add(header);
    }

    public List<String> getHeaders() {
        return Collections.unmodifiableList(headers); 
    }

    public void showInfo() {
        System.out.println("Newspaper Name: " + name);
        System.out.println("Date Published: " + datePublished);
        System.out.println("Headers:");
        for (String h : headers) System.out.println("- " + h);
    }
}
