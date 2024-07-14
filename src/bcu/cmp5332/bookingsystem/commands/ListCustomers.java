package bcu.cmp5332.bookingsystem.commands;

import java.util.List;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Command to list all customers in the flight booking system.
 * This class implements the {@link Command} interface.
 * 
 * <p>This command retrieves and prints a list of all customers who are not removed from the system.
 * It also prints the total number of active customers.</p>
 * 
 * @see Command
 * @see Customer
 * @see FlightBookingSystem
 * @see CustomerException
 * @see bcu.cmp5332.bookingsystem.main.FlightBookingSystemException
 * 
 * @version 1.0
 */
public class ListCustomers implements Command {

    /**
     * Executes the ListCustomers command, which retrieves and prints the details of all active customers.
     * 
     * @param flightBookingSystem the flight booking system containing the list of customers.
     * @throws CustomerException if an error occurs while retrieving the customers.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws CustomerException {
        List<Customer> customers = flightBookingSystem.getCustomers();
        int counter = 0;
        for (Customer customer : customers) {
            if (!customer.isRemoved()) {
                counter++;
                System.out.println(customer.getDetailsShort());
            }
        }
        System.out.println(counter + " customer(s)");
    }
}
