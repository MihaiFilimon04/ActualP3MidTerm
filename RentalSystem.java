import java.io.*;
import java.util.*;

public class RentalSystem implements FileHandler, RentalManager, Serializable {
    private static final long serialVersionUID = 1L; //id for serialization for stability, might be useful when developing
    private List<Movie> movieInventory;
    private Set<Customer> customers;
    private Map<String, Rental> activeRentals;

    public RentalSystem() {
        movieInventory = new ArrayList<>();
        customers = new HashSet<>();
        activeRentals = new HashMap<>();
    }

    public void addMovie(Movie movie) throws InvalidDataException {
        if (movie == null) {
            throw new InvalidDataException("Movie cannot be null"); //tested
        }
        movieInventory.add(movie);
    }

    public void registerCustomer(Customer customer) throws InvalidDataException {
        if (customer == null) {
            throw new InvalidDataException("Customer cannot be null"); //also tested
        }
        customers.add(customer);
    }

    @Override
    public void processRental(String customerId, String movieTitle)
            throws MovieNotAvailableException, CustomerNotFoundException {
        Customer customer = findCustomer(customerId);
        Movie movie = findAvailableMovie(movieTitle);

        if (movie == null || !movie.isAvailable()) {
            throw new MovieNotAvailableException("Movie is not available: " + movieTitle);
        }

        movie.setAvailable(false);//should have set to true when the movie is returned
        Rental rental = new Rental(movie, customer);
        customer.addRental(rental);
        activeRentals.put(customerId + ":" + movieTitle, rental);//
    }

    @Override
    public void processReturn(String customerId, String movieTitle) throws InvalidDataException {
        String rentalKey = customerId + ":" + movieTitle; //used when searching in active rentals
        Rental rental = activeRentals.get(rentalKey);

        if (rental == null) {
            throw new InvalidDataException("No active rental found"); //if the customers did not rent the specific movie show message
        }

        rental.setReturnDate(new Date());//if rental exists process the return -> validating data

        activeRentals.remove(rentalKey);

        double fee = rental.calculateFees();
        System.out.println("Return processed. Fee: $" + fee);
        rental.getMovie().setAvailable(true); //no clue why it does not work
    }

    @Override
    public boolean checkAvailability(String movieTitle) {
        Movie movie = findMovie(movieTitle);
        return movie != null && movie.isAvailable();
    }


    @Override
    public void saveData(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this); //must serialize the entire rentalsystem object so movies, customers and rentals are saved
                                    // at once
        }
    }

    @Override
    public void loadData(String filename) throws IOException, ClassNotFoundException { ///2 io exceptions, cne for object deserilzation
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            RentalSystem loaded = (RentalSystem) ois.readObject();
            this.movieInventory = loaded.movieInventory;
            this.customers = loaded.customers;
            this.activeRentals = loaded.activeRentals;
        }
    }

    @Override
    public void exportReport(String filename) throws IOException { //updates the rental report
        try {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                writer.println("=== Movie Rental System Report ===");
                writer.println("\nMovie Inventory:");
                for (Movie movie : movieInventory) {
                    writer.println(movie);
                }
                writer.println("\nActive Rentals:");
                for (Rental rental : activeRentals.values()) {
                    writer.println(String.format("Customer: %s, Movie: %s, Due: %s",
                            rental.getCustomer().getName(),
                            rental.getMovie().getTitle(),
                            rental.getDueDate()));
                }
            }
        } catch (FileNotFoundException e) { ///3rd exception
            System.err.println("Error: The file could not be created or opened. Please check the file path.");
            e.printStackTrace();
            throw e;
        }
    }


    private Customer findCustomer(String customerId) throws CustomerNotFoundException {
        return customers.stream()
                .filter(c -> c.getId().equals(customerId))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));
    }

    private Movie findMovie(String title) {
        return movieInventory.stream()
                .filter(m -> m.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    private Movie findAvailableMovie(String title) {
        return movieInventory.stream()
                .filter(m -> m.getTitle().equals(title) && m.isAvailable())
                .findFirst()
                .orElse(null);
    }
}