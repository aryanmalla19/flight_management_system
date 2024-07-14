package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Command to add a new customer to the flight booking system.
 * This class handles the creation and addition of a new customer.
 * 
 */
public class AddCustomer implements Command {

    private final String name;
    private final String phone;
    private final String email;
    private final int age;

    /**
     * Constructor to create an AddCustomer command with specified customer details.
     * 
     * @param name the name of the customer
     * @param age the age of the customer
     * @param phone the phone number of the customer
     * @param email the email address of the customer
     */
    public AddCustomer(String name, int age, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.age = age;
    }

    /**
     * Executes the AddCustomer command, adding a customer to the flight booking system.
     * 
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if there is an issue with the flight booking system
     * @throws CustomerException if there is an issue with the customer
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException, CustomerException {
        int maxId = 0;
        if (flightBookingSystem.getCustomers().size() > 0) {
            int lastIndex = flightBookingSystem.getCustomers().size() - 1;
            maxId = flightBookingSystem.getCustomers().get(lastIndex).getId();
        }
        
        Customer customer = new Customer(++maxId, name, age, phone, email);
        flightBookingSystem.addCustomer(customer);
        System.out.println("Customer #" + customer.getId() + " " + customer.getName() + " added.");
    }
}
