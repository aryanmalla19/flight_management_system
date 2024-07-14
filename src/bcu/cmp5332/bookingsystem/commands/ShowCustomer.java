package bcu.cmp5332.bookingsystem.commands;

import java.util.List;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Command to show detailed information about a specific customer.
 * This class implements the {@link Command} interface.
 * 
 * <p>This command retrieves and displays the detailed information of a customer
 * identified by their unique ID.</p>
 * 
 * @see Command
 * @see FlightBookingSystem
 * @see CustomerException
 * 
 * @version 1.0
 * 
 */
public class ShowCustomer implements Command {
    private final int id;

    /**
     * Constructs a ShowCustomer command with the specified customer ID.
     * 
     * @param id the unique ID of the customer to be shown.
     */
    public ShowCustomer(int id) {
        this.id = id;
    }

    /**
     * Executes the ShowCustomer command, which retrieves and displays the detailed information
     * of the specified customer.
     * 
     * @param flightBookingSystem the flight booking system containing the customer information.
     * @throws CustomerException if an error occurs while retrieving the customer information.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws CustomerException {
        try {            
            List<Customer> customers = flightBookingSystem.getCustomers();
            Customer customer = customers.get(this.id);
            if (!customer.isRemoved()) {
                System.out.println(customer.getDetailsLong());
            }
        } catch (Exception ex) {
            System.out.println("Invalid id of customer");
        }
    }
}
