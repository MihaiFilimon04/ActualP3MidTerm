import java.io.Serializable;

public class Movie implements Serializable {
    private String title;
    private double price;
    private boolean isAvailable;
    private String genre;

    public Movie(String title, String genre, double price) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.isAvailable = true;
    }

    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
    public String getGenre() { return genre; }
    
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return String.format("Movie: %s, Genre: %s, Price: $%.2f, Available: %s",
                title, genre, price, isAvailable);
    }
}