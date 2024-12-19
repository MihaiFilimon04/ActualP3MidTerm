import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Customer implements Serializable {
    private String id;
    private String name;
    private List<Rental> rentalHistory;

    public Customer(String id, String name) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
        this.id = id;
        this.name = name;
        this.rentalHistory = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Rental> getRentalHistory() { return rentalHistory; }

    public void addRental(Rental rental) {
        rentalHistory.add(rental);
    }
}