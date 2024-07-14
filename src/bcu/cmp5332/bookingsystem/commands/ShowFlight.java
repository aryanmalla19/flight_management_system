package bcu.cmp5332.bookingsystem.commands;

import java.time.LocalDate;
import java.util.List;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Command to show detailed information about a specific flight.
 * This class implements the {@link Command} interface.
 * 
 * <p>This command retrieves and displays the detailed information of a flight
 * identified by its unique ID.</p>
 * 
 * @see Command
 * @see FlightBookingSystem
 * @see FlightBookingSystemException
 * 
 * @version 1.0
 * 
 */
public class ShowFlight implements Command {
    private final int id;

    /**
     * Constructs a ShowFlight command with the specified flight ID.
     * 
     * @param id the unique ID of the flight to be shown.
     */
    public ShowFlight(int id) {
        this.id = id;
    }

    /**
     * Executes the ShowFlight command, which retrieves and displays the detailed information
     * of the specified flight.
     * 
     * @param flightBookingSystem the flight booking system containing the flight information.
     * @throws FlightBookingSystemException if an error occurs while retrieving the flight information.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Flight> flights = flightBookingSystem.getFlights();
        Flight flight = flights.get(this.id);
        if (flight.isRemoved()) {
            System.out.println("The flight with ID " + this.id + " has been removed.");
        } else if (flight.getDepartureDate().isBefore(LocalDate.now())) {
            System.out.println("The flight with ID " + this.id + " has already departed.");
        } else {
            System.out.println(flight.getDetailsLong());
        }
    }
}
