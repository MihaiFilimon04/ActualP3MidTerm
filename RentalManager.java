interface RentalManager {
    void processRental(String customerId, String movieTitle) throws MovieNotAvailableException, CustomerNotFoundException;
    void processReturn(String customerId, String movieTitle) throws InvalidDataException;
    boolean checkAvailability(String movieTitle);
}