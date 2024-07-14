package bcu.cmp5332.bookingsystem.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Airline;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The LandingGUI class manages the initial splash screen, role selection, and login functionality
 * for the Flight Management System GUI.
 */
public class LandingGUI {
    private FlightBookingSystem fbs;
    private JProgressBar progressBar;
    private JFrame splashFrame;
    private JFrame loginFrame = new JFrame("Airlines Login");
    
    /**
     * Constructs a LandingGUI object with the given FlightBookingSystem instance.
     * Initializes and displays the splash screen.
     * 
     * @param flightBookingSystem The FlightBookingSystem instance to use.
     */
    public LandingGUI(FlightBookingSystem flightBookingSystem) {
        this.fbs = flightBookingSystem;
        showSplashScreen();
    }
    
    /**
     * Displays the splash screen with a progress bar indicating loading progress.
     */
    private void showSplashScreen() {
        splashFrame = new JFrame("Flight Management System");
        splashFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        splashFrame.setSize(650, 550);

        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        splashFrame.setIconImage(icon.getImage());

        splashFrame.setLayout(new BorderLayout());
        splashFrame.getContentPane().setBackground(Color.decode("#F0F3BD"));

        JPanel first = new JPanel();
        first.setBackground(Color.decode("#F0F3BD"));
        first.setLayout(new BorderLayout());
        JLabel logoLabel = new JLabel(icon, JLabel.CENTER);
        JLabel loadingLabel = new JLabel("Welcome to Flight Management System!", JLabel.CENTER);
        loadingLabel.setFont(new Font("Monospaced", Font.PLAIN, 26));

        first.add(logoLabel, BorderLayout.CENTER);
        first.add(loadingLabel, BorderLayout.SOUTH);

        progressBar = new JProgressBar();
        progressBar.setFont(new Font("Monospaced", Font.BOLD, 24));
        progressBar.setForeground(Color.green);
        progressBar.setBackground(Color.black);
        progressBar.setStringPainted(true);

        JPanel second = new JPanel();
        second.setBackground(Color.decode("#F0F3BD"));
        second.setLayout(new BorderLayout());
        second.add(progressBar, BorderLayout.CENTER);

        splashFrame.add(first, BorderLayout.CENTER);
        splashFrame.add(second, BorderLayout.SOUTH);
        splashFrame.setLocationRelativeTo(null);
        splashFrame.setVisible(true);

        fillProgressBar();
    }

    /**
     * Fills the progress bar over time and switches to the role selection screen upon completion.
     */
    private void fillProgressBar() {
        Timer timer = new Timer(10, new ActionListener() {
            private int counter = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                counter += 1;
                progressBar.setValue(counter);

                if (counter >= 100) {
                    ((Timer) e.getSource()).stop();
                    splashFrame.dispose();
                    showRoleSelectionScreen();
                }
            }
        });
        timer.start();
    }

    /**
     * Displays the role selection screen where users can choose their role.
     */
    private void showRoleSelectionScreen() {
        JFrame frame = new JFrame("Flight Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        frame.setIconImage(icon.getImage());

        JPanel first = new JPanel();
        first.setLayout(new BoxLayout(first, BoxLayout.Y_AXIS));
        first.setBackground(Color.decode("#F0F3BD"));
        JLabel titleLabel = new JLabel("Flight Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel label = new JLabel("Please select your role!", JLabel.CENTER);
        label.setFont(new Font("Monospaced", Font.BOLD, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        first.add(titleLabel);
        first.add(Box.createRigidArea(new Dimension(0, 10)));
        first.add(label);
        first.add(Box.createRigidArea(new Dimension(0, 10)));
        frame.add(first, BorderLayout.NORTH);

        JPanel second = new JPanel();
        second.setBackground(Color.decode("#F0F3BD"));
        second.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));

        JButton adminButton = createStyledButton("Admin");
        JButton airlineButton = createStyledButton("Airline");
        JButton customerButton = createStyledButton("Customer");

        second.add(adminButton);
        second.add(airlineButton);
        second.add(customerButton);

        frame.add(second, BorderLayout.CENTER);

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	frame.setVisible(false);
            	splashFrame.setVisible(false);
                frame.setVisible(false);
                new AdminLoginWindow(fbs);
            }
        });

        airlineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	frame.setVisible(false);
            	splashFrame.setVisible(false);
                handleRoleSelection("airline");
            }
        });

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	frame.setVisible(false);
            	splashFrame.setVisible(false);
            	new CustomerWindow(fbs);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Creates a styled JButton with custom appearance and hover effects.
     * 
     * @param text The text to display on the button.
     * @return The styled JButton.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Monospaced", Font.BOLD, 16));
        button.setBackground(new Color(0x4CAF50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        button.setPreferredSize(new Dimension(150, 50));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0x45A049));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0x4CAF50));
            }
        });

        return button;
    }
    
    /**
     * Handles the login process for the selected role (e.g., airline).
     * Displays a login frame where credentials are entered.
     * 
     * @param role The role for which the login is handled.
     */
    private void handleRoleSelection(String role) {
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        loginFrame.setIconImage(icon.getImage());
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setSize(400, 250);
        loginFrame.setLayout(new BorderLayout());

        JPanel first = new JPanel();
        first.setLayout(new BorderLayout());
        JLabel label = new JLabel("Please login for " + role + "!", JLabel.CENTER);
        label.setFont(new Font("Monospaced", Font.BOLD, 19));
        first.setBackground(Color.decode("#F0F3BD"));
        first.add(label, BorderLayout.CENTER);

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.decode("#F0F3BD"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Make the text field expand horizontally
        loginPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0; // Reset weightx for the label
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Make the text field expand horizontally
        loginPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Monospaced", Font.BOLD, 16));
        loginButton.setBackground(new Color(0x4CAF50));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setOpaque(true);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Make the button span two columns
        gbc.weightx = 0.0; // Reset weightx for the button
        gbc.fill = GridBagConstraints.NONE; // Do not expand button
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        loginPanel.add(loginButton, gbc);

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0x45A049));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(0x4CAF50));
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    if (verifyAirline(username, password)) {
                        loginFrame.setVisible(false);
                        loginFrame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(loginFrame, "Invalid credentials. Please try again.");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(loginFrame, "Error: " + ex.getMessage());
                }
            }
        });

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(Color.decode("#F0F3BD"));
        wrapperPanel.add(loginPanel, BorderLayout.CENTER);
        wrapperPanel.add(Box.createHorizontalStrut(20), BorderLayout.WEST); // Margin left
        wrapperPanel.add(Box.createHorizontalStrut(20), BorderLayout.EAST); // Margin right

        loginFrame.add(first, BorderLayout.NORTH);
        loginFrame.add(wrapperPanel, BorderLayout.CENTER);

        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    /**
     * Verifies the entered credentials against the list of airlines.
     * 
     * @param email The entered email (username).
     * @param password The entered password.
     * @return True if the credentials are valid, false otherwise.
     * @throws IOException If an error occurs during credential verification.
     */
    private boolean verifyAirline(String email, String password) throws IOException {
        List<Airline> airlines = fbs.getAirlines();
        for (Airline airline : airlines) {
            if (airline.getEmail().equals(email) && airline.getPassword().equals(password)) {
                loginFrame.setVisible(false);
                new AirlineWindow(fbs, airline.getId());
                return true;
            }
        }
        return false;
    }

    
}
