package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Represents a window for booking a flight in the flight booking system.
 * Provides input fields for customer details and displays flight information.
 * Handles user interactions and adds a booking to the system upon user confirmation.

 */
public class BookingWindow extends JFrame implements ActionListener {

    private Flight flight;
    private JTextField nameText = new JTextField();
    private JTextField ageText = new JTextField();
    private JTextField phoneText = new JTextField();
    private JTextField emailText = new JTextField();
    private FlightBookingSystem fbs;
    private JButton bookBtn = new JButton("Book");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs a BookingWindow object for the specified flight.
     * 
     * @param flight the flight to be booked
     * @throws FlightBookingSystemException if an error occurs in the flight booking system
     * @throws IOException if an I/O error occurs
     * @throws CustomerException if a customer-related error occurs
     */
    public BookingWindow(Flight flight) throws FlightBookingSystemException, IOException, CustomerException {
        this.flight = flight;
        this.fbs = FlightBookingSystemData.load();
        initialize();
    }

    /**
     * Initializes the GUI components of the window.
     * Sets up input fields for customer details, displays flight information, and sets up buttons.
     */
    private void initialize() {
        setTitle("Book Flight");
        setSize(400, 300);
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        setIconImage(icon.getImage());

        // Flight details panel
        JPanel flightDetailsPanel = new JPanel();
        flightDetailsPanel.setLayout(new GridLayout(5, 2));
        flightDetailsPanel.setBorder(BorderFactory.createTitledBorder("Flight Details"));
        flightDetailsPanel.add(new JLabel("Flight No:"));
        flightDetailsPanel.add(new JLabel(flight.getFlightNumber()));
        flightDetailsPanel.add(new JLabel("Airline:"));
        flightDetailsPanel.add(new JLabel(flight.getPlane().getAirline().getName()));
        flightDetailsPanel.add(new JLabel("Plane Model:"));
        flightDetailsPanel.add(new JLabel(flight.getPlane().getModel()));
        flightDetailsPanel.add(new JLabel("Origin:"));
        flightDetailsPanel.add(new JLabel(flight.getOrigin()));
        flightDetailsPanel.add(new JLabel("Destination:"));
        flightDetailsPanel.add(new JLabel(flight.getDestination()));

        // Customer details panel
        JPanel customerDetailsPanel = new JPanel();
        customerDetailsPanel.setLayout(new GridLayout(4, 2));
        customerDetailsPanel.setBorder(BorderFactory.createTitledBorder("Customer Details"));
        customerDetailsPanel.add(new JLabel("Name: "));
        customerDetailsPanel.add(nameText);
        customerDetailsPanel.add(new JLabel("Age: "));
        customerDetailsPanel.add(ageText);
        customerDetailsPanel.add(new JLabel("Phone: "));
        customerDetailsPanel.add(phoneText);
        customerDetailsPanel.add(new JLabel("Email: "));
        customerDetailsPanel.add(emailText);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(bookBtn);
        buttonsPanel.add(cancelBtn);

        // Add action listeners to buttons
        bookBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        // Layout setup
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(flightDetailsPanel, BorderLayout.NORTH);
        getContentPane().add(customerDetailsPanel, BorderLayout.CENTER);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null); // Center the window

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close window without exiting application

        setVisible(true);
    }

    /**
     * Handles action events from buttons in the window.
     * Performs booking operation when the Book button is clicked, or closes the window on Cancel button click.
     *
     * @param ae the action event triggered by user interaction
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bookBtn) {
            try {
                bookFlight();
            } catch (CustomerException | FlightBookingSystemException | IOException e) {
                JOptionPane.showMessageDialog(this, "Booking failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == cancelBtn) {
            this.dispose(); // Close the window
        }
    }

    /**
     * Retrieves customer details input values, checks if the customer already exists,
     * creates commands to add the customer (if new) and booking, executes them on the flight booking system,
     * and stores the updated data.
     *
     * @throws CustomerException if a customer-related error occurs
     * @throws FlightBookingSystemException if an error occurs in the flight booking system
     * @throws IOException if an I/O error occurs
     */
    private void bookFlight() throws CustomerException, FlightBookingSystemException, IOException {
        String name = nameText.getText();
        String ageStr = ageText.getText();
        String phone = phoneText.getText();
        String email = emailText.getText();

        if (name.isEmpty() || ageStr.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            throw new CustomerException("All fields must be filled.");
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            throw new CustomerException("Age must be a valid number.");
        }

        // Check if the customer already exists based on phone number
        Customer customer = fbs.getCustomerByPhone(phone);
        if (customer == null) {
            // Customer does not exist, add a new customer
            Command addCustomerCmd = new AddCustomer(name, age, phone, email);
            addCustomerCmd.execute(fbs);
            customer = fbs.getCustomerByPhone(phone);
        }

        // Add booking for the flight
        Command addBookingCmd = new AddBooking(customer.getId(), flight.getId());
        addBookingCmd.execute(fbs);
        flight.addPassenger(customer);

        // Store updated data
        FlightBookingSystemData.store(fbs);

        JOptionPane.showMessageDialog(this, "Booking successful for " + name + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }
}
