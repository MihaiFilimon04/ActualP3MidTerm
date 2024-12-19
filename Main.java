public class Main {
    public static void main(String[] args) {
        try {
            RentalSystem system = new RentalSystem();

            system.addMovie(new Movie("The Matrix", "Sci-Fi", 4.99));
            system.addMovie(new Movie("Inception", "Action", 5.99));
            system.addMovie(new Movie("Aftersun", "Drama", 6.99));

            //system.addMovie(new Movie("", "", 4.99));

            //add customers when presenting
            Customer customer1 = new Customer("C001", "John Doe");
            Customer customer2 = new Customer("C002", "Mihai");
            Customer customer3 = new Customer("C003", "Jane Doe");
            Customer customer4 = new Customer("C004", "dandan");
            Customer customer5 = new Customer("C005", "Razvan");
            //don't forget to register customers
            system.registerCustomer(customer1);
            system.registerCustomer(customer2);
            system.registerCustomer(customer3);
            system.registerCustomer(customer4);
            system.registerCustomer(customer5);
            for (String arg : args) { // processing rentals
                switch (arg) {
                    case "C001":
                        system.processRental("C001", "The Matrix");
                        break;
                    case "C002":
                        system.processRental("C002", "Aftersun");
                        break;
                    case "C003":
                        system.processRental("C003", "Inception");
                        break;
                    case "C004":
                        system.processRental("C004", "Shrek 2");
                    case "C005":
                        system.processRental("C005", "Inception");
                    default:
                        System.out.println("Invalid customer ID: " + arg); /// data validation
                        break;
                }
            }
            System.out.println("Rental processed successfully");

            system.saveData("rental_system.dat");
            system.exportReport("rental_report.txt");

            //basically everytime the code compiles the customers return the movie, actually they return once the
            //process return compiles
            //no clue how to actually give the customer the choice to return the movie
           /// system.processReturn("C002", "Aftersun");
           /// system.processReturn("C001", "The Matrix");
           system.processReturn("C005", "Inception");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
