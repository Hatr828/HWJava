package Library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Newspaper {
    private String name;

    private LocalDate datePublished;

    private final List<String> headers = new ArrayList<>();

    public Newspaper(String name, LocalDate datePublished) {
        this.name = Objects.requireNonNull(name);
        this.datePublished = Objects.requireNonNull(datePublished);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public LocalDate getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(LocalDate datePublished) {
        this.datePublished = Objects.requireNonNull(datePublished);
    }

    @Override
    public String toString() {
        return "Newspaper{" +
                "name='" + name + '\'' +
                ", datePublished=" + datePublished +
                ", headers=" + headers +
                '}';
    }
}
