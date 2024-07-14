package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * The BookingDataManager class manages the loading and storing of booking data for the Flight Booking System.
 * It implements the DataManager interface to provide functionality for handling booking data operations.
 * 
 * @version 1.0
 * 
 */
public class BookingDataManager implements DataManager {
    
    /**
     * The resource path where booking data is stored or loaded from.
     */
    public final String RESOURCE = "./resources/data/bookings.txt";

    /**
     * Loads booking data from a file into the Flight Booking System.
     * 
     * @param fbs the Flight Booking System instance into which data will be loaded.
     * @throws IOException if an I/O error occurs during data loading.
     * @throws FlightBookingSystemException if a general error occurs in the flight booking system.
     * @throws CustomerException if an error occurs related to customer operations.
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException, CustomerException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                	int id = Integer.parseInt(properties[0]);
                    int customer_id = Integer.parseInt(properties[1]);
                    Customer customer = fbs.getCustomerByID(customer_id);
                    int flight_id = Integer.parseInt(properties[2]);
                    Flight flight = fbs.getFlightByID(flight_id);
                    LocalDate date = LocalDate.parse(properties[3]);
                    Double price = Double.parseDouble(properties[4]);
                    Booking booking = new Booking(id, customer, flight, date, price);
                    
                    // Add booking to customer and flight
                    customer.addBooking(booking);
                    flight.addPassenger(customer);
                    
                    // Add booking to flight booking system
                    fbs.addBooking(booking);
                } catch (NumberFormatException ex) {
                    throw new CustomerException("Unable to parse booking id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }

    /**
     * Stores booking data from the Flight Booking System to a file.
     * 
     * @param fbs the Flight Booking System instance from which data will be stored.
     * @throws IOException if an I/O error occurs during data storage.
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Booking booking : fbs.getBookings()) {
                out.print(booking.getId() + SEPARATOR);
                out.print(booking.getCustomer().getId() + SEPARATOR);
                out.print(booking.getFlight().getId() + SEPARATOR);
                out.print(booking.getBookingDate() + SEPARATOR);
                out.print(booking.getPrice() + SEPARATOR);
                out.println();
            }
        }
    }
}
