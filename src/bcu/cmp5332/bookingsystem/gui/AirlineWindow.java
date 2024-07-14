package bcu.cmp5332.bookingsystem.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Plane;

/**
 * Represents the main window for the airline management interface.
 * Provides menus for managing flights and planes associated with the airline.
 * Handles user interactions such as viewing flights, adding flights, deleting flights,
 * viewing planes, and adding planes.
 * Implements ActionListener to handle menu actions.
 * Manages display of flights and planes associated with the logged-in airline.
 * Implements basic functionality to load and store data from the flight booking system.
 * Handles logout functionality.
 * 
 */
public class AirlineWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu flightsMenu;
    private JMenu planeMenu;

    private JMenuItem adminExit;
    private JMenuItem adminLogout;

    private JMenuItem flightsView;
    private JMenuItem flightsViewAll;
    private JMenuItem flightsAdd;
    private JMenuItem flightsDel;

    private JMenuItem planeView;
    private JMenuItem planeAdd;
    private int id;
    private FlightBookingSystem fbs;
    private JLabel adminLabel = new JLabel("Welcome to Admin Panel !");
    private ListSelectionModel flightModel;

    /**
     * Constructs an AirlineWindow for the specified FlightBookingSystem and airline ID.
     *
     * @param fbs the FlightBookingSystem instance
     * @param id  the ID of the airline associated with this window
     */
    public AirlineWindow(FlightBookingSystem fbs, int id) {
        initialize();
        this.id = id;
        this.fbs = fbs;
    }

    /**
     * Returns the FlightBookingSystem associated with this window.
     *
     * @return the FlightBookingSystem instance
     */
    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    /**
     * Initializes the GUI components of the window.
     * Sets up the menus, labels, and layout.
     */
    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        setIconImage(icon.getImage());
        setTitle("Flight Booking Management System - Airline Window");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        adminMenu = new JMenu("Profile");
        menuBar.add(adminMenu);
        adminLogout = new JMenuItem("Logout");
        adminMenu.add(adminLogout);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);

        adminExit.addActionListener(this);

        flightsMenu = new JMenu("Flights");
        menuBar.add(flightsMenu);

        flightsView = new JMenuItem("View");
        flightsViewAll = new JMenuItem("View All");
        flightsAdd = new JMenuItem("Add");
        flightsDel = new JMenuItem("Delete");
        flightsMenu.add(flightsViewAll);
        flightsMenu.add(flightsView);
        flightsMenu.add(flightsAdd);
        flightsMenu.add(flightsDel);

        for (int i = 0; i < flightsMenu.getItemCount(); i++) {
            flightsMenu.getItem(i).addActionListener(this);
        }

        planeMenu = new JMenu("Plane");
        menuBar.add(planeMenu);
        planeView = new JMenuItem("View");
        planeAdd = new JMenuItem("Add");
        planeMenu.add(planeView);
        planeMenu.add(planeAdd);

        for (int i = 0; i < planeMenu.getItemCount(); i++) {
            planeMenu.getItem(i).addActionListener(this);
        }

        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        // Create and configure adminLabel
        adminLabel = new JLabel("Welcome to Airline Panel ! ", SwingConstants.CENTER);
        adminLabel.setFont(new Font("Arial", Font.BOLD, 32));

        // Create a panel to hold the adminLabel
        JPanel adminLabelPanel = new JPanel(new BorderLayout());
        adminLabelPanel.add(adminLabel, BorderLayout.CENTER);

        // Add the panel to the content pane
        pane.add(adminLabelPanel, BorderLayout.CENTER);

        setSize(800, 500);
        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Handles actions performed on the menu items.
     * Performs actions based on the selected menu item.
     *
     * @param ae the ActionEvent triggered by the menu item
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            FlightBookingSystemData.load();
            FlightBookingSystemData.store(fbs);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException e) {
            e.printStackTrace();
        } catch (CustomerException e) {
            e.printStackTrace();
        }
        if (ae.getSource() == adminExit) {
            System.exit(0);
        } else if (ae.getSource() == adminLogout) {
            logout();
        } else if (ae.getSource() == flightsViewAll) {
            displayAllFlights();
        } else if (ae.getSource() == flightsView) {
            displayFlights();
        } else if (ae.getSource() == flightsAdd) {
            new AddFlightWindow(this);
        } else if (ae.getSource() == flightsDel) {
            deleteFlight();
        } else if (ae.getSource() == planeView) {
            displayPlane();
        } else if (ae.getSource() == planeAdd) {
            new AddPlaneWindow(this, this.id);
        }
    }

    /**
     * Displays planes associated with the logged-in airline.
     * Filters planes based on the airline ID.
     */
    public void displayPlane() {
        List<Plane> planeList = fbs.getPlanes();
        String[] columns = new String[]{"Plane ID", "Plane Name", "Capacity", "Airlines"};

        List<Plane> selfPlane = new ArrayList<>();
        for (Plane plane : planeList) {
            if (plane.getAirline().getId() == this.id) {
                selfPlane.add(plane);
            }
        }

        Object[][] data = new Object[selfPlane.size()][4];

        for (int i = 0; i < selfPlane.size(); i++) {
            Plane plane = selfPlane.get(i);
            data[i][0] = plane.getId();
            data[i][1] = plane.getModel();
            data[i][2] = plane.getCapacity();
            data[i][3] = plane.getAirline().getName();
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

    /**
     * Displays all flights associated with the logged-in airline.
     * Filters flights based on the airline ID.
     * Shows detailed flight information including dynamic price calculation.
     */
    public void displayAllFlights() {
        List<Flight> flightsList = fbs.getFlights();
        String[] columns = new String[]{"Flight ID", "Flight No", "Origin", "Destination", "Departure Date", "Price", "Capacity", "Plane", "Airline"};

        List<Flight> selfFlights = new ArrayList<>();
        for (Flight flight : flightsList) {
            if (flight.getPlane().getAirline().getId() == this.id) {
                selfFlights.add(flight);
            }
        }

        Object[][] data = new Object[selfFlights.size()][9];
        for (int i = 0; i < selfFlights.size(); i++) {
            Flight flight = selfFlights.get(i);
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
                            new ShowFlightWindow(flight);
                        } catch (FlightBookingSystemException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

    /**
     * Displays active flights associated with the logged-in airline.
     * Filters active flights based on the airline ID and future departure dates.
     * Shows detailed flight information including dynamic price calculation.
     */
    public void displayFlights() {
        List<Flight> flightsList = fbs.getFlights();
        String[] columns = new String[]{"Flight ID", "Flight No", "Origin", "Destination", "Departure Date", "Price", "Capacity", "Plane", "Airline"};

        List<Flight> activeFlights = new ArrayList<>();
        for (Flight flight : flightsList) {
            if (!flight.isRemoved() && flight.getDepartureDate().isAfter(LocalDate.now()) && flight.getPlane().getAirline().getId() == this.id) {
                activeFlights.add(flight);
            }
        }

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
                            new ShowFlightWindow(flight);
                        } catch (FlightBookingSystemException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

    /**
     * Deletes a selected flight associated with the logged-in airline.
     * Displays confirmation dialog before deleting.
     */
    public void deleteFlight() {
        List<Flight> flightsList = fbs.getFlights();
        String[] columns = new String[]{"Flight ID", "Flight No", "Origin", "Destination", "Departure Date", "Price", "Capacity", "Plane", "Airline"};

        List<Flight> activeFlights = new ArrayList<>();
        for (Flight flight : flightsList) {
            if (!flight.isRemoved() && flight.getDepartureDate().isAfter(LocalDate.now()) && flight.getPlane().getAirline().getId() == this.id) {
                activeFlights.add(flight);
            }
        }

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
                        Flight flight;
                        try {
                            flight = fbs.getFlightByID(selectedData);
                            int response = JOptionPane.showConfirmDialog(
                                    AirlineWindow.this,
                                    "Do you want to continue action?",
                                    "Confirm",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE
                            );

                            if (response == JOptionPane.YES_OPTION) {
                                flight.removeFlight();
                                deleteFlight();
                            }
                        } catch (FlightBookingSystemException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

    /**
     * Performs logout action by hiding the current window.
     */
    public void logout() {
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Displays a success message dialog.
     */
    public void success() {
        JOptionPane.showMessageDialog(this, null, "Success", JOptionPane.OK_OPTION);
    }
}
