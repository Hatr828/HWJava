package Library;

public class Book {
    private String title;

    private String author;

    private String genre;

    private int pages;

    private String annotation;

    public Book() {
    }

    public Book(String title, String author, String genre, int pages, String annotation) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pages = pages;
        this.annotation = annotation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", pages=" + pages +
                ", annotation='" + annotation + '\'' +
                '}';
    }

    public void showInfo() {
        System.out.println("Book Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Genre: " + genre);
        System.out.println("Pages: " + pages);
        System.out.println("Annotation: " + annotation);
    }
}
