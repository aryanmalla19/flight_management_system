package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Airline;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Plane;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The CustomerWindow class provides a graphical user interface for customers to interact
 * with flights, planes, airlines, and their bookings.
 * <p>
 * It allows customers to view flights, book flights, view planes, view airlines,
 * and manage their bookings.
 *
 * @author Aryan Malla
 */
public class CustomerWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu profileMenu;
    private JMenu flightsMenu;
    private JMenu planeMenu;
    private JMenu airlineMenu;

    private JMenuItem profileExit;
    private JMenuItem flightsView;
    private JMenuItem planeView;
    private JMenuItem airlineView;

    private JTextField airlineSearchField = new JTextField(15);
    private JTextField originSearchField = new JTextField(15);
    private JTextField destinationSearchField = new JTextField(15);
    private JTextField departureDateSearchField = new JTextField(10);
    private JButton searchButton = new JButton("Search");

    private FlightBookingSystem fbs;
    private JLabel welcomeLabel;
    private ListSelectionModel flightModel;
    private ListSelectionModel airlineModel;

    /**
     * Constructs a CustomerWindow object with the provided FlightBookingSystem.
     *
     * @param fbs The FlightBookingSystem instance.
     */
    public CustomerWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        initialize();
    }

    /**
     * Returns the FlightBookingSystem associated with this window.
     *
     * @return The FlightBookingSystem instance.
     */
    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    /**
     * Initializes the graphical components of the CustomerWindow.
     */
    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        setIconImage(icon.getImage());
        setTitle("Flight Booking Management System - Customer Window");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        profileMenu = new JMenu("Profile");
        profileExit = new JMenuItem("Exit");
        profileMenu.add(profileExit);
        menuBar.add(profileMenu);
        profileExit.addActionListener(this);

        flightsMenu = new JMenu("Flights");
        flightsView = new JMenuItem("View");
        flightsMenu.add(flightsView);
        menuBar.add(flightsMenu);
        flightsView.addActionListener(this);

        planeMenu = new JMenu("Plane");
        planeView = new JMenuItem("View");
        planeMenu.add(planeView);
        menuBar.add(planeMenu);
        planeView.addActionListener(this);

        airlineMenu = new JMenu("Airline");
        airlineView = new JMenuItem("View");
        airlineMenu.add(airlineView);
        menuBar.add(airlineMenu);
        airlineView.addActionListener(this);

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        welcomeLabel = new JLabel("Welcome to Flight Management System!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        pane.add(welcomeLabel, BorderLayout.NORTH);

        setVisible(true);
    }

    /**
     * Handles action events for menu items.
     *
     * @param ae The ActionEvent object representing the user's action.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            FlightBookingSystemData.store(fbs);
            fbs = FlightBookingSystemData.load();
        } catch (IOException | FlightBookingSystemException | CustomerException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (ae.getSource() == profileExit) {
            System.exit(0);
        } else if (ae.getSource() == flightsView) {
            displayFlights();
        } else if (ae.getSource() == planeView) {
            displayPlanes();
        } else if (ae.getSource() == airlineView) {
            displayAirlines();
        }
    }

    /**
     * Displays a table of bookings for the current customer.
     */
    public void displayBookings() {
        List<Booking> bookingsList = fbs.getBookings();
        String[] columns = new String[]{"Booking ID", "Customer Name", "Flight No", "Origin", "Destination", "Departure Date", "Booking Date"};

        List<Booking> activeBookings = new ArrayList<>();
        for (Booking booking : bookingsList) {
            if (booking.getBookingDate().isAfter(LocalDate.now())) {
                activeBookings.add(booking);
            }
        }

        Object[][] data = new Object[activeBookings.size()][7];
        for (int i = 0; i < activeBookings.size(); i++) {
            Booking booking = activeBookings.get(i);
            data[i][0] = booking.getId();
            data[i][1] = booking.getCustomer().getName();
            data[i][2] = booking.getFlight().getFlightNumber();
            data[i][3] = booking.getFlight().getOrigin();
            data[i][4] = booking.getFlight().getDestination();
            data[i][5] = booking.getFlight().getDepartureDate();
            data[i][6] = booking.getBookingDate();
        }

        JTable table = new JTable(data, columns);

        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

    /**
     * Displays a table of planes in the FlightBookingSystem.
     */
    public void displayPlanes() {
        List<Plane> planeList = fbs.getPlanes();
        String[] columns = {"Plane ID", "Plane Name", "Capacity", "Airline"};

        Object[][] data = new Object[planeList.size()][4];
        for (int i = 0; i < planeList.size(); i++) {
            Plane plane = planeList.get(i);
            data[i][0] = plane.getId();
            data[i][1] = plane.getModel();
            data[i][2] = plane.getCapacity();
            data[i][3] = plane.getAirline().getName();
        }

        JTable table = new JTable(data, columns);

        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        this.revalidate();
    }

    /**
     * Displays a table of airlines in the FlightBookingSystem.
     */
    public void displayAirlines() {
        List<Airline> airlineList = fbs.getAirlines();
        String[] columns = {"Airline ID", "Airline Name", "Email"};

        Object[][] data = new Object[airlineList.size()][3];
        for (int i = 0; i < airlineList.size(); i++) {
            Airline airline = airlineList.get(i);
            data[i][0] = airline.getId();
            data[i][1] = airline.getName();
            data[i][2] = airline.getEmail();
        }

        JTable table = new JTable(data, columns);
        airlineModel = table.getSelectionModel();
        airlineModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !airlineModel.isSelectionEmpty()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int selectedData = (int) table.getValueAt(selectedRow, 0);
                        // Implement airline selection functionality
                    }
                }
            }
        });

        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        this.revalidate();
    }

    /**
     * Displays a table of flights in the FlightBookingSystem based on search criteria.
     */
    public void displayFlights() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(3, 4, 10, 10));
        searchPanel.add(new JLabel("Airline:"));
        searchPanel.add(airlineSearchField);
        searchPanel.add(new JLabel("Origin:"));
        searchPanel.add(originSearchField);
        searchPanel.add(new JLabel("Destination:"));
        searchPanel.add(destinationSearchField);
        searchPanel.add(new JLabel("Departure Date (YYYY-MM-DD):"));
        searchPanel.add(departureDateSearchField);
        searchPanel.add(new JLabel());
        searchPanel.add(searchButton);
        searchPanel.add(new JLabel());
        searchPanel.add(new JLabel("Click row to book flight", SwingConstants.CENTER));

        String[] columns = {"Flight ID", "Flight No", "Origin", "Destination", "Departure Date", "Price", "Capacity", "Plane", "Airline"};

        List<Flight> flightsList = fbs.getFlights();
        List<Flight> activeFlights = filterFlights(flightsList);

        Object[][] data = new Object[activeFlights.size()][9];
        for (int i = 0; i < activeFlights.size(); i++) {
            Flight flight = activeFlights.get(i);
            data[i][0] = flight.getId();
            data[i][1] = flight.getFlightNumber();
            data[i][2] = flight.getOrigin();
            data[i][3] = flight.getDestination();
            data[i][4] = flight.getDepartureDate();
            data[i][5] = Booking.calculateDynamicPrice(flight);
            data[i][6] = flight.getRemainingCapacity() + "/" + flight.getCapacity();
            data[i][7] = flight.getPlane().getModel();
            data[i][8] = flight.getPlane().getAirline().getName();
        }

        JTable table = new JTable(data, columns);
        flightModel = table.getSelectionModel();
        flightModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !flightModel.isSelectionEmpty()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int selectedData = (int) table.getValueAt(selectedRow, 0);
                        try {
                            Flight flight = fbs.getFlightByID(selectedData);
                            new BookingWindow(flight);
                        } catch (FlightBookingSystemException | IOException | CustomerException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayFlights();
            }
        });

        this.getContentPane().removeAll();
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(searchPanel, BorderLayout.NORTH);
        this.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        this.revalidate();
    }

    /**
     * Filters flights based on search criteria provided in the CustomerWindow.
     *
     * @param flightsList The list of flights to filter.
     * @return List of flights that match the search criteria.
     */
    private List<Flight> filterFlights(List<Flight> flightsList) {
        List<Flight> activeFlights = new ArrayList<>();
        String airline = airlineSearchField.getText().trim();
        String origin = originSearchField.getText().trim();
        String destination = destinationSearchField.getText().trim();
        String departureDateStr = departureDateSearchField.getText().trim();
        LocalDate departureDate = null;

        if (!departureDateStr.isEmpty()) {
            try {
                departureDate = LocalDate.parse(departureDateStr);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        for (Flight flight : flightsList) {
            if (!flight.isRemoved() && flight.getDepartureDate().isAfter(LocalDate.now())) {
                boolean matches = true;
                if (!airline.isEmpty() && !flight.getPlane().getAirline().getName().equalsIgnoreCase(airline)) {
                    matches = false;
                }
                if (!origin.isEmpty() && !flight.getOrigin().equalsIgnoreCase(origin)) {
                    matches = false;
                }
                if (!destination.isEmpty() && !flight.getDestination().equalsIgnoreCase(destination)) {
                    matches = false;
                }
                if (departureDate != null && !flight.getDepartureDate().isEqual(departureDate)) {
                    matches = false;
                }
                if (matches) {
                    activeFlights.add(flight);
                }
            }
        }
        return activeFlights;
    }

    /**
     * Displays a success message dialog.
     */
    public void success() {
        JOptionPane.showMessageDialog(this, "Success", "Success", JOptionPane.OK_OPTION);
    }
}
