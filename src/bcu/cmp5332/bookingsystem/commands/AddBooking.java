package bcu.cmp5332.bookingsystem.commands;

import java.time.LocalDate;

import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Command to add a booking in the flight booking system.
 * This class handles the addition of a booking by a customer for a specific flight.
 * 
 */
public class AddBooking implements Command {

    private int customer_id;
    private int flight_id;

    /**
     * Constructor to create an AddBooking command with specified customer and flight IDs.
     * 
     * @param customer_id the ID of the customer
     * @param flight_id the ID of the flight
     */
    public AddBooking(int customer_id, int flight_id) {
        this.customer_id = customer_id;
        this.flight_id = flight_id;
    }

    /**
     * Executes the AddBooking command, adding a booking to the flight booking system if possible.
     * 
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if there is an issue with the flight booking system
     * @throws CustomerException if there is an issue with the customer
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException, CustomerException {
        Customer customer = flightBookingSystem.getCustomerByID(customer_id);
        Flight flight = flightBookingSystem.getFlightByID(flight_id);

        if (customer.isRemoved() || flight.isRemoved()) {
            System.out.println("Can't book a flight because the account is already removed.");
            return;
        }

        for (Booking booking : flightBookingSystem.getBookings()) {
            if (booking.getCustomer() == customer && booking.getFlight() == flight) {
                System.out.println("This booking already exists");
                return;
            }
        }

        int maxId = 0;
        if (flightBookingSystem.getBookings().size() > 0) {
            int lastIndex = flightBookingSystem.getBookings().size() - 1;
            maxId = flightBookingSystem.getBookings().get(lastIndex).getId();
        }

        double adjustedPrice = Booking.calculateDynamicPrice(flight);
        if (LocalDate.now().isBefore(flight.getDepartureDate()) || LocalDate.now().isEqual(flight.getDepartureDate())) {
            if (flight.getRemainingCapacity() > 0) {
                Booking booking = new Booking(++maxId, customer, flight, LocalDate.now(), adjustedPrice);
                flightBookingSystem.addBooking(booking);
                System.out.println("Booking success #" + customer.getId() + " - " + customer.getName() + " Flight No#" + flight.getId());
            } else {
                System.out.println("This flight is already full and cannot be booked.");
            }
        } else {
            System.out.println("Couldn't book a flight that has already expired!");
        }
    }
}
