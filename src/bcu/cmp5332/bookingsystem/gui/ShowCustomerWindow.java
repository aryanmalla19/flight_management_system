package bcu.cmp5332.bookingsystem.gui;

import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;

/**
 * Represents a window for displaying detailed information about a customer.
 * Displays customer ID, name, email, phone number, and all bookings associated with the customer.
 * 
 * @version 1.0
 */
public class ShowCustomerWindow extends JFrame {

    private JLabel customerID;
    private JLabel customerName;
    private JLabel customerEmail;
    private JLabel customerPhone;
    private JLabel customerBooking;

    /**
     * Constructs a ShowCustomerWindow object to display information about the given customer.
     * 
     * @param customer the customer whose details are to be displayed
     */
    public ShowCustomerWindow(Customer customer) {
        super("Customer Details");
        setSize(650, 600);
        setLocation(100, 50);
        setVisible(true);
        initialize(customer);
    }

    /**
     * Initializes the components and layout of the window.
     * 
     * @param customer the customer whose details are to be displayed
     */
    private void initialize(Customer customer) {
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(6, 1));

        customerID = new JLabel("Customer ID: " + customer.getId());
        customerName = new JLabel("Customer Name: " + customer.getName());
        customerEmail = new JLabel("Customer Email: " + customer.getEmail());
        customerPhone = new JLabel("Customer Phone: " + customer.getPhone());

        StringBuilder result = new StringBuilder("<html>Customer Bookings: <br>");
        int counter = 0;
        for (Booking booking : customer.getBookings()) {
            counter++;
            result.append("<br>* Booking date: ").append(booking.getBookingDate())
                  .append(" for Flight #").append(booking.getFlight().getId())
                  .append(" - ").append(booking.getFlight().getFlightNumber())
                  .append(" - ").append(booking.getFlight().getOrigin())
                  .append(" to ").append(booking.getFlight().getDestination())
                  .append(" on ").append(booking.getFlight().getDepartureDate())
                  .append(". Price: Rs.").append(booking.getPrice());
        }
        if (counter == 0) {
            result.append("No booking made yet.");
        }
        customerBooking = new JLabel(result.toString());

        contentPane.add(customerID);
        contentPane.add(customerName);
        contentPane.add(customerEmail);
        contentPane.add(customerPhone);
        contentPane.add(customerBooking);
    }
}
