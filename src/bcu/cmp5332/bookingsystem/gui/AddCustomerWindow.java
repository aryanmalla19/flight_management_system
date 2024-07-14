package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Represents a window for adding a new customer to the flight booking system.
 * Provides input fields for name, age, email, and phone number, and buttons to add or cancel the operation.
 * Handles user interactions and adds a customer to the system upon user confirmation.
 *
 */
public class AddCustomerWindow extends JFrame implements ActionListener {

    private MainWindow mw;
    private JTextField nameText = new JTextField();
    private JTextField ageText = new JTextField();
    private JTextField emailText = new JTextField();
    private JTextField phoneText = new JTextField();
    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    /**
     * Constructs an AddCustomerWindow object with a reference to the main window.
     *
     * @param mw the main window instance
     */
    public AddCustomerWindow(MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    /**
     * Initializes the GUI components of the window.
     * Sets up input fields for customer's name, age, email, phone number,
     * and buttons for add and cancel operations.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Handle look and feel exception
        }

        setTitle("Add a New Customer");
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        setIconImage(icon.getImage());
        setSize(350, 220);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2));
        topPanel.add(new JLabel("Name : "));
        topPanel.add(nameText);
        topPanel.add(new JLabel("Age : "));
        topPanel.add(ageText);
        topPanel.add(new JLabel("Email : "));
        topPanel.add(emailText);
        topPanel.add(new JLabel("Phone : "));
        topPanel.add(phoneText);

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
     * Performs add operation when the Add button is clicked, or closes the window on Cancel button click.
     *
     * @param ae the action event triggered by user interaction
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            try {
                addCustomer();
            } catch (CustomerException | IOException e) {
                e.printStackTrace();
                // Handle exception (currently just prints stack trace)
            }
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false); // Close the window
        }
    }

    /**
     * Retrieves input values for customer's name, age, email, and phone number,
     * creates an AddCustomer command, executes it on the flight booking system,
     * updates the customer list in the main window, and closes this window.
     *
     * @throws CustomerException         if there is an issue with the customer data
     * @throws IOException               if an I/O error occurs
     */
    private void addCustomer() throws CustomerException, IOException {
        try {
            String name = nameText.getText();
            int age = Integer.parseInt(ageText.getText());
            String email = emailText.getText();
            String phone = phoneText.getText();

            Command addCustomer = new AddCustomer(name, age, phone, email);
            addCustomer.execute(mw.getFlightBookingSystem());

            mw.displayCustomers(); // Update customer list in main window

            this.setVisible(false); // Close the window after adding
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
