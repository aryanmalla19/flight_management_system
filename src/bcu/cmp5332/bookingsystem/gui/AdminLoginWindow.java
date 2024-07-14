package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents the admin login window for the flight booking system.
 * Provides username and password fields for admin authentication,
 * and buttons for login and cancel operations.
 * Upon successful login, opens the main window of the application.
 * Handles user interactions and validates admin credentials.
 *
 * @author Aryan Malla
 */
public class AdminLoginWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private JLabel adminLabel;
    private FlightBookingSystem fbs;

    /**
     * Constructs an AdminLoginWindow with a reference to the FlightBookingSystem instance.
     *
     * @param flightBookingSystem the flight booking system instance
     */
    public AdminLoginWindow(FlightBookingSystem flightBookingSystem) {
        this.fbs = flightBookingSystem;
        setTitle("Admin Login");
        setSize(600, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        setIconImage(icon.getImage());

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Admin Label
        adminLabel = new JLabel("Admin Panel", SwingConstants.CENTER);
        adminLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        add(adminLabel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(15);
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(15);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.setBackground(Color.decode("#468189"));
        loginButton.setToolTipText("Click to login");
        buttonPanel.add(loginButton);

        // Cancel Button
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setFocusPainted(false);
        cancelButton.setToolTipText("Click to clear the fields");
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCancel();
            }
        });

        setVisible(true);
    }

    /**
     * Handles the login button click event.
     * Validates the admin username and password.
     * On successful login, hides the login window and opens the main window of the application.
     * Displays an error message for invalid credentials.
     */
    private void handleLogin() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        // For demonstration, we assume admin/admin as correct credentials
        if ("admin".equals(username) && "admin".equals(new String(password))) {
            this.setVisible(false);
            new MainWindow(fbs);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles the cancel button click event.
     * Clears the username and password fields.
     */
    private void handleCancel() {
        // Clear the fields
        usernameField.setText("");
        passwordField.setText("");
    }
}
