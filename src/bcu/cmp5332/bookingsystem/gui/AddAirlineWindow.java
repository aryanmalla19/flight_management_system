package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddAirline;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Represents a window for adding a new airline to the flight booking system.
 * Provides input fields for airline details and buttons to add or cancel the operation.
 * Handles user interactions and updates the main window after adding a new airline.
 *
 */
public class AddAirlineWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField nameText = new JTextField();
    private JTextField emailText = new JTextField();
    private JTextField passwordText = new JTextField();
    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs an AddAirlineWindow object with a reference to the main window.
     *
     * @param mw the main window instance
     */
    public AddAirlineWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initializes the GUI components of the window.
     * Sets up input fields for airline details and buttons for add and cancel operations.
     */
    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Handle look and feel exception
        }
        setTitle("Add a New Airline");
        setSize(350, 220);

        // Top panel for input fields
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 2));
        topPanel.add(new JLabel("Name : "));
        topPanel.add(nameText);
        topPanel.add(new JLabel("Email : "));
        topPanel.add(emailText);
        topPanel.add(new JLabel("Password : "));
        topPanel.add(passwordText);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        // Add action listeners to buttons
        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        // Add panels to the frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(mw); // Position relative to main window
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
                addAirline();
            } catch (CustomerException | IOException | FlightBookingSystemException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false); // Close the window
        }
    }

    /**
     * Retrieves input values for airline details, creates an AddAirline command,
     * executes it on the flight booking system, updates the main window, and closes this window.
     *
     * @throws CustomerException         if there is an issue with the customer data
     * @throws IOException               if an I/O error occurs
     * @throws FlightBookingSystemException if there is an issue with the flight booking system
     */
    private void addAirline() throws CustomerException, IOException, FlightBookingSystemException {
        // Retrieve input values
        String name = nameText.getText();
        String email = emailText.getText();
        String password = passwordText.getText();

        // Create AddAirline command and execute it
        Command addAirlineCommand = new AddAirline(name, email, password);
        addAirlineCommand.execute(mw.getFlightBookingSystem());

        // Update airline list in main window
        mw.displayAirlines();

        // Close the window after adding
        this.setVisible(false);
    }
}
