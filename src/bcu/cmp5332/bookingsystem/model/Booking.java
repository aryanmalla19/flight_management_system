package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * The Booking class represents a booking made by a customer for a flight.
 * It contains attributes such as id, customer, flight, bookingDate, and price.
 */
public class Booking {

    private int id;
    private Customer customer;
    private Flight flight;
    private LocalDate bookingDate;
    private double price;

    /**
     * Constructs a Booking object with the specified id, customer, flight,
     * booking date, and price.
     * 
     * @param id The unique identifier of the booking.
     * @param customer The customer who made the booking.
     * @param flight The flight booked by the customer.
     * @param bookingDate The date when the booking was made.
     * @param price The price of the booking.
     */
    public Booking(int id, Customer customer, Flight flight, LocalDate bookingDate, double price) {
        this.id = id;
        this.customer = customer;
        this.flight = flight;
        this.bookingDate = bookingDate;
        this.price = price;
    }

    /**
     * Sets a new price for the booking.
     * 
     * @param newPrice The new price to set for the booking.
     */
    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    /**
     * Calculates a dynamic price adjustment based on various factors such as
     * days until departure and remaining capacity of the flight.
     * 
     * @param flight The flight for which to calculate the dynamic price.
     * @return The dynamically adjusted price based on flight conditions.
     */
    public static double calculateDynamicPrice(Flight flight) {
        double basePrice = flight.getPrice();
        int daysUntilDeparture = (int) ChronoUnit.DAYS.between(LocalDate.now(), flight.getDepartureDate());

        // Adjust price based on days until departure
        if (daysUntilDeparture <= 2) {
            basePrice = basePrice * 1.7; // 70% increase for bookings made 2 or fewer days before departure
        } else if (daysUntilDeparture <= 7) {
            basePrice *= 1.4; // 40% increase for bookings made 3-7 days before departure
        } else if (daysUntilDeparture <= 30) {
            basePrice *= 1.1; // 10% increase for bookings made 8-30 days before departure
        }
        
        // You can add more sophisticated price adjustments based on remaining capacity here
        
        return basePrice;
    }

    /**
     * Retrieves the price of the booking.
     * 
     * @return The price of the booking.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Retrieves the customer who made the booking.
     * 
     * @return The customer who made the booking.
     */
    public Customer getCustomer() {
        return customer;
    }

    public void updateBooking(Flight flight) {
    	this.flight = flight;
    	this.price = Booking.calculateDynamicPrice(flight);
    	this.bookingDate = LocalDate.now();
    }
    
    /**
     * Sets the customer who made the booking.
     * 
     * @param customer The new customer to set for the booking.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Retrieves the flight that was booked.
     * 
     * @return The flight that was booked.
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Sets the flight that was booked.
     * 
     * @param flight The new flight to set for the booking.
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Retrieves the unique identifier of the booking.
     * 
     * @return The id of the booking.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the date when the booking was made.
     * 
     * @return The booking date.
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the date when the booking was made.
     * 
     * @param bookingDate The new booking date to set.
     */
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
}
