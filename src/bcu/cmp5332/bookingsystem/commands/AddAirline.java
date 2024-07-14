package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Airline;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Command to add a new airline to the flight booking system.
 * 
 */
public class AddAirline implements Command {

    private String name;
    private String email;
    private String password;

    /**
     * Constructor to create an AddAirline command with the specified details.
     * 
     * @param name the name of the airline
     * @param email the email of the airline
     * @param password the password for the airline
     */
    public AddAirline(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Executes the command to add a new airline to the flight booking system.
     * 
     * @param flightBookingSystem the flight booking system to which the airline will be added
     * @throws FlightBookingSystemException if there is an error in the flight booking system
     * @throws CustomerException if there is an error related to the customer
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem)
            throws FlightBookingSystemException, CustomerException {
        int maxId = 0;
        if (flightBookingSystem.getAirlines().size() > 0) {
            int lastIndex = flightBookingSystem.getAirlines().size() - 1;
            maxId = flightBookingSystem.getPlanes().get(lastIndex).getId();
        }

        Airline airline = new Airline(++maxId, name, email, password);
        flightBookingSystem.addAirline(airline);
        System.out.println("Airline #" + airline.getId() + " " + airline.getName() + " added.");
    }
}
