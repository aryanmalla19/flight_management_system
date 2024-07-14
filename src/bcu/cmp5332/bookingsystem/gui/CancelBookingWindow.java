package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * The CancelBookingWindow class provides a graphical user interface for canceling a booking.
 * Users can enter customer ID and flight ID to cancel a booking.
 * 
 */
public class CancelBookingWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField customerId = new JTextField();
    private JTextField flightId = new JTextField();
    private JButton cancelBtn = new JButton("Exit");
    private JButton cancelBookingBtn = new JButton("Cancel Booking");

    /**
     * Constructs a CancelBookingWindow object associated with the main window.
     *
     * @param mw The MainWindow object.
     */
    public CancelBookingWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initializes the graphical components of the CancelBookingWindow.
     */
    private void initialize() {
        setTitle("Cancel Booking");
        setSize(350, 220);
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        setIconImage(icon.getImage());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2));
        topPanel.add(new JLabel("Customer ID : "));
        topPanel.add(customerId);
        topPanel.add(new JLabel("Flight ID : "));
        topPanel.add(flightId);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(cancelBookingBtn);
        bottomPanel.add(cancelBtn);

        cancelBookingBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        getContentPane().add(topPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);
        setVisible(true);
    }

    /**
     * Handles button click events for canceling booking and exiting the window.
     *
     * @param ae The ActionEvent object representing the user's action.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == cancelBookingBtn) {
            try {
                cancelBooking();
            } catch (CustomerException | IOException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    /**
     * Cancels a booking using the entered customer ID and flight ID.
     *
     * @throws CustomerException If there is an issue with customer data.
     * @throws IOException       If there is an issue with input or output operations.
     */
    private void cancelBooking() throws CustomerException, IOException {
        try {
            int customerID = Integer.parseInt(customerId.getText());
            int flightID = Integer.parseInt(flightId.getText());

            Command cancelBooking = new CancelBooking(customerID, flightID);
            cancelBooking.execute(mw.getFlightBookingSystem());

            this.setVisible(false);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
