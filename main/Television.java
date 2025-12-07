public class Television {
    private final String model;
    private final int year;
    private final double price;
    private final double diagonal;
    private final String manufacturer;
    private final int rating;

    public Television(String model, int year, double price, double diagonal, String manufacturer, int rating) {
        this.model = model;
        this.year = year;
        this.price = price;
        this.diagonal = diagonal;
        this.manufacturer = manufacturer;
        this.rating = rating;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public double getDiagonal() {
        return diagonal;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %.1f\" %s, $%.2f, rating %d/10",
                manufacturer, model, diagonal, year, price, rating);
    }
}
