package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.commands.EditBooking;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Represents a window for updating an existing booking in the flight booking system.
 * Provides input fields for booking ID and flight ID, and buttons to update or cancel the operation.
 * Handles user interactions and updates a booking in the system upon user confirmation.
 * 
 * @see EditBooking
 * @see MainWindow
 * @see FlightBookingSystemException
 * @see CustomerException
 * 
 * @version 1.0
 * 
 */
public class UpdateBookingWindow extends JFrame implements ActionListener {

    private FlightBookingSystem mw;
    private JTextField bookingId = new JTextField();
    private JTextField flightId = new JTextField();
    private JButton updateBtn = new JButton("Update Booking");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs an UpdateBookingWindow object with a reference to the main window.
     * 
     * @param fbs the main window instance
     */
    public UpdateBookingWindow(FlightBookingSystem fbs) {
        this.mw = fbs;
        initialize();
    }

    /**
     * Initializes the GUI components of the window.
     * Sets up input fields for booking ID and flight ID, and buttons for update and cancel operations.
     */
    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setTitle("Update a Booking");
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        setIconImage(icon.getImage());
        setSize(350, 220);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2));
        topPanel.add(new JLabel("Booking ID: "));
        topPanel.add(bookingId);
        topPanel.add(new JLabel("Flight ID: "));
        topPanel.add(flightId);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(updateBtn);
        bottomPanel.add(cancelBtn);

        updateBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        getContentPane().add(topPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Handles action events from buttons in the window.
     * Performs update operation when the Update Booking button is clicked, or closes the window on Cancel button click.
     * 
     * @param ae the action event triggered by user interaction
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == updateBtn) {
            try {
                updateBook();
            } catch (CustomerException | IOException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    /**
     * Retrieves booking ID and flight ID input values, creates an EditBooking command,
     * executes it on the flight booking system, and closes this window.
     * 
     * @throws CustomerException if there is an issue with the customer data
     * @throws IOException if an I/O error occurs
     */
    private void updateBook() throws CustomerException, IOException {
        try {
            int bookingID = Integer.parseInt(bookingId.getText());
            int flightID = Integer.parseInt(flightId.getText());

            Command updateBooking = new EditBooking(bookingID, flightID);
            updateBooking.execute(FlightBookingSystemData.load());

            this.setVisible(false);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for booking ID and flight ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
