package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Represents a window for adding a new booking to the flight booking system.
 * Provides input fields for customer ID and flight ID, and buttons to add or cancel the operation.
 * Handles user interactions and adds a booking to the system upon user confirmation.
 *
 * @author Aryan Malla
 */
public class AddBookingWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField customerId = new JTextField();
    private JTextField flightId = new JTextField();
    private JButton addBtn = new JButton("Add Booking");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs an AddBookingWindow object with a reference to the main window.
     *
     * @param mw the main window instance
     */
    public AddBookingWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initializes the GUI components of the window.
     * Sets up input fields for customer ID and flight ID, and buttons for add and cancel operations.
     */
    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Handle look and feel exception
        }

        setTitle("Add a New Booking");
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        setIconImage(icon.getImage());
        setSize(350, 220);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2));
        topPanel.add(new JLabel("Customer ID : "));
        topPanel.add(customerId);
        topPanel.add(new JLabel("Flight ID : "));
        topPanel.add(flightId);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        getContentPane().add(topPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(mw); // Position relative to main window
        setVisible(true);
    }

    /**
     * Handles action events from buttons in the window.
     * Performs add operation when the Add Booking button is clicked, or closes the window on Cancel button click.
     *
     * @param ae the action event triggered by user interaction
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            try {
                addBook();
            } catch (CustomerException | IOException e) {
                e.printStackTrace();
                // Handle exception (currently just prints stack trace)
            }
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false); // Close the window
        }
    }

    /**
     * Retrieves customer ID and flight ID input values, creates an AddBooking command,
     * executes it on the flight booking system, and closes this window.
     *
     * @throws CustomerException         if there is an issue with the customer data
     * @throws IOException               if an I/O error occurs
     */
    private void addBook() throws CustomerException, IOException {
        try {
            int customerID = Integer.parseInt(customerId.getText());
            int flightID = Integer.parseInt(flightId.getText());

            Command addBooking = new AddBooking(customerID, flightID);
            addBooking.execute(mw.getFlightBookingSystem());

            this.setVisible(false); // Close the window after adding
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
