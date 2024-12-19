import java.io.Serializable;
import java.util.Date;

public class Rental implements Serializable {
    private Movie movie;
    private Customer customer;
    private Date rentalDate;
    private Date dueDate;
    private Date returnDate;
    private static final int RENTAL_PERIOD_DAYS = 7; /// late fee functionality, play with the numbers

    public Rental(Movie movie, Customer customer) {
        this.movie = movie;
        this.customer = customer;
        this.rentalDate = new Date();
        this.dueDate = new Date(rentalDate.getTime() + (RENTAL_PERIOD_DAYS * 24 * 60 * 60 * 1000)); /// conversion to miliseconds
    }

    public boolean isOverdue() {
        if (returnDate == null) {
            return new Date().after(dueDate);
        }
        return returnDate.after(dueDate);
    }

    public double calculateFees() {
        double baseFee = movie.getPrice();
        if (isOverdue()) {
            long overdueDays = (returnDate.getTime() - dueDate.getTime()) / (24 * 60 * 60 * 1000);
            return baseFee + (overdueDays * 2.0); //$2 per day late fee, come on man you had a week
        }
        return baseFee;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Movie getMovie() { return movie; }
    public Customer getCustomer() { return customer; }
    public Date getRentalDate() { return rentalDate; }
    public Date getDueDate() { return dueDate; }
}
