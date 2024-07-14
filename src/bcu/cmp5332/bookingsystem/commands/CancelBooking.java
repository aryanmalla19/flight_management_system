package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Command to cancel an existing booking in the flight booking system.
 * This class handles the cancellation of a booking and removes the customer from the flight.
 * 
 */
public class CancelBooking implements Command {

    private final int customer_id;
    private final int flight_id;

    /**
     * Constructor to create a CancelBooking command with specified customer and flight IDs.
     * 
     * @param customer_id the ID of the customer
     * @param flight_id the ID of the flight
     */
    public CancelBooking(int customer_id, int flight_id) {
        this.customer_id = customer_id;
        this.flight_id = flight_id;
    }

    /**
     * Executes the CancelBooking command, cancelling a booking in the flight booking system.
     * 
     * @param flightBookingSystem the flight booking system
     * @throws CustomerException if there is an issue with the customer
     * @throws FlightBookingSystemException if there is an issue with the flight booking system
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws CustomerException, FlightBookingSystemException {
        Customer customer = flightBookingSystem.getCustomerByID(customer_id);
        Flight flight = flightBookingSystem.getFlightByID(flight_id);
        
        if (customer.isRemoved() || flight.isRemoved()) {
            System.out.println("Can't cancel a flight because the account is already removed.");
            return;
        }

        for (Booking booking : flightBookingSystem.getBookings()) {
            if (booking.getCustomer().getId() == customer_id && booking.getFlight().getId() == flight_id) {
                double cancellationFee = Booking.calculateDynamicPrice(flight) - flight.getPrice();
                flightBookingSystem.cancelBooking(booking);
                customer.cancelBooking(booking);
                flight.removePassenger(customer);
                System.out.println("Successfully canceled booking #" + booking.getId() + " for Customer #" + customer_id +
                        " on Flight #" + flight_id + " Cancellation Fee: $" + cancellationFee);
                return;
            }
        }

        System.out.println("No booking found for Customer #" + customer_id + " on Flight #" + flight_id);
    }
}
