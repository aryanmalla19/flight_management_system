package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Plane;

import java.time.LocalDate;

/**
 * Command to add a new flight to the flight booking system.
 * This class handles the creation and addition of a new flight.
 * 
 */
public class AddFlight implements Command {

    private final String flightNumber;
    private final String origin;
    private final String destination;
    private final double price;
    private final int planeId;
    private final LocalDate departureDate;

    /**
     * Constructor to create an AddFlight command with specified flight details.
     * 
     * @param flightNumber the flight number
     * @param origin the origin of the flight
     * @param destination the destination of the flight
     * @param price the price of the flight
     * @param planeId the ID of the plane
     * @param departureDate the departure date of the flight
     */
    public AddFlight(String flightNumber, String origin, String destination, double price, int planeId, LocalDate departureDate) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.planeId = planeId;
        this.departureDate = departureDate;
    }
    
    /**
     * Executes the AddFlight command, adding a flight to the flight booking system.
     * 
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if there is an issue with the flight booking system
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        int maxId = 0;
        if (flightBookingSystem.getFlights().size() > 0) {
            int lastIndex = flightBookingSystem.getFlights().size() - 1;
            maxId = flightBookingSystem.getFlights().get(lastIndex).getId();
        }
        System.out.println("The flight price is = " + price);
        Plane plane = flightBookingSystem.getPlaneByID(planeId);
        Flight flight = new Flight(++maxId, flightNumber, origin, destination, price, plane, departureDate);
        flightBookingSystem.addFlight(flight);
        System.out.println("Flight #" + flight.getId() + " added.");
    }
}
