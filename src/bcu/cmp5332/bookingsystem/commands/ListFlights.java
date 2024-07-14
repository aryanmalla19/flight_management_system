package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.time.LocalDate;
import java.util.List;

/**
 * Command to list all upcoming flights in the flight booking system.
 * This class implements the {@link Command} interface.
 * 
 * <p>This command retrieves and prints a list of all flights that are not removed 
 * and have a departure date after the current date. It also prints the total number 
 * of these flights.</p>
 * 
 * @see Command
 * @see Flight
 * @see FlightBookingSystem
 * @see FlightBookingSystemException
 * 
 * @version 1.0
 * 
 * @author Aryan Malla
 */
public class ListFlights implements Command {

    /**
     * Executes the ListFlights command, which retrieves and prints the details of all upcoming flights.
     * 
     * @param flightBookingSystem the flight booking system containing the list of flights.
     * @throws FlightBookingSystemException if an error occurs while retrieving the flights.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Flight> flights = flightBookingSystem.getFlights();
        int counter = 0;
        for (Flight flight : flights) {
            if (!flight.isRemoved() && flight.getDepartureDate().isAfter(LocalDate.now())) {
                counter++;
                System.out.println(flight.getDetailsShort());
            }
        }
        System.out.println(counter + " flight(s)");
    }
}
