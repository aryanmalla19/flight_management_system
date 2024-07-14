package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Command to edit an existing booking by updating the flight associated with it.
 */
public class EditBooking implements Command {

    private int bookingId;
    private int flightId;

    /**
     * Constructs an EditBooking command with the specified booking ID and flight ID.
     *
     * @param bookingId the ID of the booking to be edited
     * @param flightId  the ID of the new flight to associate with the booking
     */
    public EditBooking(int bookingId, int flightId) {
        this.bookingId = bookingId;
        this.flightId = flightId;
    }

    /**
     * Executes the command to edit the booking.
     * 
     * Retrieves the booking and flight by their IDs, updates the booking with the new flight,
     * and prints a success message to the console.
     *
     * @param flightBookingSystem the flight booking system instance
     * @throws FlightBookingSystemException if the booking or flight cannot be found
     * @throws CustomerException if there is an issue with the customer data
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException, CustomerException {
        Booking booking = flightBookingSystem.getBookingByID(bookingId);
        Flight flight = flightBookingSystem.getFlightByID(flightId);
        booking.updateBooking(flight);
        System.out.println("Booking successfully updated #" + booking.getId() + 
                           " Flight No#" + flight.getId() + 
                           " New Flight: " + booking.getFlight().getOrigin() + 
                           " to " + booking.getFlight().getDestination());
    }
}
