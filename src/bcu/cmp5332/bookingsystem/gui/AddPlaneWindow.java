package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddPlane;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a window for adding a new plane to the flight booking system.
 * Provides input fields for plane name, capacity, and optionally airline ID,
 * and buttons to add or cancel the operation.
 * Handles user interactions and adds a plane to the system upon user confirmation.
 *
 * @author Aryan Malla
 */
public class AddPlaneWindow extends JFrame implements ActionListener {

    private MainWindow mw;      // Reference to the main window
    private AirlineWindow aw;   // Reference to the airline window
    private JTextField nameText = new JTextField();
    private JTextField capacityText = new JTextField();
    private JTextField airlineText = new JTextField();
    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");
    private int id;             // Airline ID

    /**
     * Constructs an AddPlaneWindow object with a reference to the main window.
     *
     * @param mw the main window instance
     */
    public AddPlaneWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Constructs an AddPlaneWindow object with a reference to the airline window and airline ID.
     * Initializes the window and hides the airline ID field since it's not needed for the airline window.
     *
     * @param aw the airline window instance
     * @param id the airline ID
     */
    public AddPlaneWindow(AirlineWindow aw, int id) {
        this.aw = aw;
        this.id = id;
        initialize();
        airlineText.setVisible(false); // Hide the Airline ID text field for AirlineWindow
    }

    /**
     * Initializes the GUI components of the frame.
     * Sets up input fields for plane details and buttons for add and cancel operations.
     */
    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace(); // Handle exception properly in a real application
        }

        setTitle("Add a New Plane");
        setSize(350, 220);
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        setIconImage(icon.getImage());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2));
        topPanel.add(new JLabel("Name : "));
        topPanel.add(nameText);
        topPanel.add(new JLabel("Capacity : "));
        topPanel.add(capacityText);

        if (mw != null) {
            topPanel.add(new JLabel("Airline ID : "));
            topPanel.add(airlineText);
        }

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
                addPlane();
            } catch (FlightBookingSystemException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (CustomerException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == cancelBtn) {
            this.dispose(); // Close the window
        }
    }

    /**
     * Retrieves input values for plane details, validates them, creates an AddPlane command,
     * executes it on the flight booking system (either through main window or airline window),
     * shows a success message, and closes this window upon successful addition.
     *
     * @throws FlightBookingSystemException if there is an issue with the plane data
     * @throws CustomerException            if there is an issue with the customer data (not currently thrown, for potential future use)
     */
    private void addPlane() throws FlightBookingSystemException, CustomerException {
        String name = nameText.getText();
        int capacity;
        int airlineId = 0; // Initialize to 0 by default

        if (mw != null) {
            try {
                airlineId = Integer.parseInt(airlineText.getText());
            } catch (NumberFormatException nfe) {
                throw new FlightBookingSystemException("Invalid airline ID format. Please enter a valid number.");
            }
        }

        try {
            capacity = Integer.parseInt(capacityText.getText());
        } catch (NumberFormatException nfe) {
            throw new FlightBookingSystemException("Invalid capacity format. Please enter a valid number.");
        }

        Command addPlaneCmd;

        if (mw != null) {
            addPlaneCmd = new AddPlane(name, capacity, airlineId);
            addPlaneCmd.execute(mw.getFlightBookingSystem());
            mw.displayPlane();
        } else if (aw != null) {
            addPlaneCmd = new AddPlane(name, capacity, this.id);
            addPlaneCmd.execute(aw.getFlightBookingSystem());
            aw.displayPlane();
        }

        JOptionPane.showMessageDialog(this, "Plane added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);

        // Close the window after successful addition
        this.dispose();
    }

}

