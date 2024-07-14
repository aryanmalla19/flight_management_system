package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The Help command displays help messages based on the user's role.
 */
public class Help implements Command {

    private String role;

    /**
     * Constructs a Help command object with the specified role.
     * 
     * @param role the role of the user (admin, airline, customer)
     */
    public Help(String role) {
        this.role = role;
    }

    /**
     * Executes the Help command, printing the help message based on the specified role.
     * 
     * @param flightBookingSystem the flight booking system (not used in this implementation)
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) {
        System.out.println(Command.getMessage(role));
    }
}
