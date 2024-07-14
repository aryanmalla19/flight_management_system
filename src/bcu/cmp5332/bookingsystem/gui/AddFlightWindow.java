package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents a window for adding a new flight to the flight booking system.
 * Provides input fields for flight number, origin, destination, departure date, price, and capacity,
 * and buttons to add or cancel the operation.
 * Handles user interactions and adds a flight to the system upon user confirmation.
 */
public class AddFlightWindow extends JFrame implements ActionListener {

    private MainWindow mw;      // Reference to the main window
    private AirlineWindow aw;   // Reference to the airline window
    private JTextField flightNoText = new JTextField();
    private JTextField originText = new JTextField();
    private JTextField destinationText = new JTextField();
    private JTextField depDateText = new JTextField();
    private JTextField flightPrice = new JTextField();
    private JTextField flightCapacity = new JTextField();
    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs an AddFlightWindow object with a reference to the main window.
     *
     * @param mw the main window instance
     */
    public AddFlightWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Constructs an AddFlightWindow object with a reference to the airline window.
     *
     * @param aw the airline window instance
     */
    public AddFlightWindow(AirlineWindow aw) {
        this.aw = aw;
        initialize();
    }

    /**
     * Initializes the GUI components of the frame.
     * Sets up input fields for flight details and buttons for add and cancel operations.
     */
    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle exception properly in a real application
        }

        setTitle("Add a New Flight");
        setSize(350, 250);
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        setIconImage(icon.getImage());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(6, 2));
        topPanel.add(new JLabel("Flight No : "));
        topPanel.add(flightNoText);
        topPanel.add(new JLabel("Origin : "));
        topPanel.add(originText);
        topPanel.add(new JLabel("Destination : "));
        topPanel.add(destinationText);
        topPanel.add(new JLabel("Departure Date (YYYY-MM-DD) : "));
        topPanel.add(depDateText);
        topPanel.add(new JLabel("Price : "));
        topPanel.add(flightPrice);
        topPanel.add(new JLabel("Capacity : "));
        topPanel.add(flightCapacity);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        // Center the window on main window or airline window based on availability
        setLocationRelativeTo(mw != null ? mw : aw);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close window without exiting application

        setVisible(true);
    }

    /**
     * Handles action events from buttons in the window.
     * Performs add operation when the Add button is clicked, or closes the window on Cancel button click.
     *
     * @param ae the action event triggered by user interaction
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            try {
                addFlight();
            } catch (FlightBookingSystemException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (CustomerException e) {
                e.printStackTrace();
                // Handle exception (currently just prints stack trace)
            }
        } else if (ae.getSource() == cancelBtn) {
            this.dispose(); // Close the window
        }
    }

    /**
     * Retrieves input values for flight details, validates them, creates an AddFlight command,
     * executes it on the flight booking system (either through main window or airline window),
     * shows a success message, and closes this window upon successful addition.
     *
     * @throws FlightBookingSystemException if there is an issue with the flight data
     * @throws CustomerException            if there is an issue with the customer data (not currently thrown, for potential future use)
     */
    private void addFlight() throws FlightBookingSystemException, CustomerException {
        String flightNumber = flightNoText.getText();
        String origin = originText.getText();
        String destination = destinationText.getText();
        LocalDate departureDate;
        double price;
        int capacity;

        try {
            departureDate = LocalDate.parse(depDateText.getText());
        } catch (DateTimeParseException dtpe) {
            throw new FlightBookingSystemException("Invalid date format. Please use YYYY-MM-DD.");
        }

        try {
            price = Double.parseDouble(flightPrice.getText());
        } catch (NumberFormatException nfe) {
            throw new FlightBookingSystemException("Invalid price format. Please enter a valid number.");
        }

        try {
            capacity = Integer.parseInt(flightCapacity.getText());
        } catch (NumberFormatException nfe) {
            throw new FlightBookingSystemException("Invalid capacity format. Please enter a valid number.");
        }

        Command addFlightCmd = new AddFlight(flightNumber, origin, destination, price, capacity, departureDate);

        if (mw != null) {
            addFlightCmd.execute(mw.getFlightBookingSystem());
            mw.displayFlights();
        } else if (aw != null) {
            addFlightCmd.execute(aw.getFlightBookingSystem());
            aw.displayFlights();
        }

        JOptionPane.showMessageDialog(this, "Flight added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Close the window after successful addition
        this.dispose();
    }

}
