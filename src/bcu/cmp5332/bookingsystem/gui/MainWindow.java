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
import bcu.cmp5332.bookingsystem.model.Airline;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Plane;

public class MainWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu flightsMenu;
    private JMenu bookingsMenu;
    private JMenu customersMenu;
    private JMenu planeMenu;
    private JMenu airlineMenu;

    private JMenuItem adminExit;

    private JMenuItem flightsView;
    private JMenuItem flightsViewAll;
    private JMenuItem flightsAdd;
    private JMenuItem flightsDel;
    
    private JMenuItem bookingsIssue;
    private JMenuItem bookingsView;
    private JMenuItem bookingsViewAll;
    private JMenuItem bookingsUpdate;
    private JMenuItem bookingsCancel;

    private JMenuItem custView;
    private JMenuItem custViewAll;
    private JMenuItem custAdd;
    private JMenuItem custDel;

    private JMenuItem planeView;
    private JMenuItem planeAdd;
    
    private JMenuItem airlineView;
    private JMenuItem airlineAdd;
    
    private FlightBookingSystem fbs;
    private JLabel adminLabel = new JLabel("Welcome to Admin Panel !");
    private ListSelectionModel flightModel;
    private ListSelectionModel airlineModel;
    private ListSelectionModel customerModel;

    public MainWindow(FlightBookingSystem fbs) {
        initialize();
        this.fbs = fbs;
    }
    
    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        setTitle("Flight Booking Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

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

        bookingsMenu = new JMenu("Bookings");

        bookingsIssue = new JMenuItem("Issue");
        bookingsUpdate = new JMenuItem("Update");
        bookingsCancel = new JMenuItem("Cancel");
        bookingsView = new JMenuItem("View");
        bookingsViewAll = new JMenuItem("View All");
        
        bookingsMenu.add(bookingsViewAll);
        bookingsMenu.add(bookingsView);
        bookingsMenu.add(bookingsIssue);
        bookingsMenu.add(bookingsUpdate);
        bookingsMenu.add(bookingsCancel);

        for (int i = 0; i < bookingsMenu.getItemCount(); i++) {
            bookingsMenu.getItem(i).addActionListener(this);
        }

        menuBar.add(bookingsMenu);

        customersMenu = new JMenu("Customers");
        menuBar.add(customersMenu);

        custView = new JMenuItem("View");
        custViewAll = new JMenuItem("View All");
        custAdd = new JMenuItem("Add");
        custDel = new JMenuItem("Delete");

        customersMenu.add(custViewAll);
        customersMenu.add(custView);
        customersMenu.add(custAdd);
        customersMenu.add(custDel);

        for (int i = 0; i < customersMenu.getItemCount(); i++) {
        	customersMenu.getItem(i).addActionListener(this);
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
        
        airlineMenu = new JMenu("Airline");
        menuBar.add(airlineMenu);

        airlineView = new JMenuItem("View");
        airlineAdd = new JMenuItem("Add");

        
        airlineMenu.add(airlineView);
        airlineMenu.add(airlineAdd);

        for (int i = 0; i < airlineMenu.getItemCount(); i++) {
        	airlineMenu.getItem(i).addActionListener(this);
        }
        
        Container pane = getContentPane();
        pane.setLayout(new BorderLayout());

        // Create and configure adminLabel
        adminLabel = new JLabel("Welcome to Admin Panel ! ", SwingConstants.CENTER);
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
        }else if (ae.getSource() == flightsViewAll) {
            displayAllFlights();
        } else if (ae.getSource() == flightsView) {
            displayFlights();
        } else if (ae.getSource() == flightsAdd) {
            new AddFlightWindow(this);
        } else if (ae.getSource() == flightsDel) {
            deleteFlight();
        } else if (ae.getSource() == bookingsIssue) {
        	new AddBookingWindow(this);
        } else if (ae.getSource() == bookingsCancel) {
        	new CancelBookingWindow(this);
        } else if (ae.getSource() == custViewAll) {
            displayAllCustomers();
        } else if (ae.getSource() == custView) {
            displayCustomers();
        } else if (ae.getSource() == custAdd) {
            new AddCustomerWindow(this);
        } else if (ae.getSource() == custDel) {
            deleteCustomer();
        } else if (ae.getSource() == planeView) {
            displayPlane();
        } else if (ae.getSource() == planeAdd) {
            new AddPlaneWindow(this);
         } else if (ae.getSource() == airlineView) {
             displayAirlines();
         } else if (ae.getSource() == airlineAdd) {
             new AddAirlineWindow(this);
         } else if (ae.getSource() == bookingsView) {
             displayBookings();
         } else if (ae.getSource() == bookingsViewAll) {
             displayAllBookings();
         } else if (ae.getSource() == bookingsUpdate) {
             new UpdateBookingWindow(fbs);
         }
    }
    
    public void displayAllBookings() {
        List<Booking> bookingsList = fbs.getBookings();
        String[] columns = new String[]{"Bookings ID","Customer Name", "Flight No", "Origin", "Destination", "Departure Date", "Booking Date"};


        Object[][] data = new Object[bookingsList.size()][7];
        for (int i = 0; i < bookingsList.size(); i++) {
            Booking booking = bookingsList.get(i);
            data[i][0] = booking.getId();
            data[i][1] = booking.getCustomer().getName();
            data[i][2] = booking.getFlight().getId();
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
    
    public void displayBookings() {
        List<Booking> bookingsList = fbs.getBookings();
        String[] columns = new String[]{"Bookings ID","Customer Name", "Flight No", "Origin", "Destination", "Departure Date", "Booking Date"};

        
        List<Booking> activeBookings = new ArrayList<>();
        for (Booking booking : bookingsList) {
            if ( booking.getBookingDate().isAfter(LocalDate.now())) {
            	activeBookings.add(booking);
            }
        }


        Object[][] data = new Object[activeBookings.size()][7];
        for (int i = 0; i < activeBookings.size(); i++) {
            Booking booking = activeBookings.get(i);
            data[i][0] = booking.getId();
            data[i][1] = booking.getCustomer().getName();
            data[i][2] = booking.getCustomer().getName();
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
    

    public void displayPlane() {
        List<Plane> planeList = fbs.getPlanes();
        String[] columns = new String[]{"Plane ID", "Plane Name", "Capacity","Airlines"};

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
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }

    public void displayAllFlights() {
        List<Flight> flightsList = fbs.getFlights();
        String[] columns = new String[]{"Flight ID", "Flight No", "Origin", "Destination", "Departure Date", "Price", "Capacity","Plane","Airline"};


        Object[][] data = new Object[flightsList.size()][9];
        for (int i = 0; i < flightsList.size(); i++) {
            Flight flight = flightsList.get(i);
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
    
    
    public void displayAirlines() {
        List<Airline> airlineList = fbs.getAirlines();
        String[] columns = new String[]{"Airline ID", "Airline Name", "Email", "Password"};



        Object[][] data = new Object[airlineList.size()][4];
        for (int i = 0; i < airlineList.size(); i++) {
            Airline airline = airlineList.get(i);
            data[i][0] = airline.getId();
            data[i][1] = airline.getName();
            data[i][2] = airline.getEmail();
            data[i][3] = airline.getPassword();
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
                    }
                }
            }
        });
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }
    
    

    public void displayFlights() {
        List<Flight> flightsList = fbs.getFlights();
        String[] columns = new String[]{"Flight ID", "Flight No", "Origin", "Destination", "Departure Date", "Price", "Capacity","Plane","Airline"};

        List<Flight> activeFlights = new ArrayList<>();
        for (Flight flight : flightsList) {
            if (!flight.isRemoved() && flight.getDepartureDate().isAfter(LocalDate.now())) {
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
    
    public void deleteCustomer() {
        List<Customer> customerList = fbs.getCustomers();
        String[] columns = new String[]{"Customer ID", "Name","Age", "Email", "Phone Number", "Number of Booking"};

        List<Customer> activeCustomers = new ArrayList<>();
        for (Customer customer : customerList) {
            if (!customer.isRemoved()) {
                activeCustomers.add(customer);
            }
        }
        
        Object[][] data = new Object[activeCustomers.size()][6];
        for (int i = 0; i < activeCustomers.size(); i++) {
            Customer customer = activeCustomers.get(i);
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getAge();
            data[i][3] = customer.getEmail();
            data[i][4] = customer.getPhone();
            data[i][5] = customer.getBookings().size();
        }

        JTable table = new JTable(data, columns);
        customerModel = table.getSelectionModel();
        customerModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !customerModel.isSelectionEmpty()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int selectedData = (int) table.getValueAt(selectedRow, 0);
                        try {
                            Customer customer = fbs.getCustomerByID(selectedData);
                            int response = JOptionPane.showConfirmDialog(
                                MainWindow.this,
                                "Do you want to continue action?",
                                "Confirm",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                            );
                            
                            if (response == JOptionPane.YES_OPTION) {
                                customer.removeCustomer();
                                deleteCustomer();
                            }

                        } catch (CustomerException e1) {
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
    
    public void deleteFlight() {
        List<Flight> flightsList = fbs.getFlights();
        String[] columns = new String[]{"Flight ID", "Flight No", "Origin", "Destination", "Departure Date", "Price", "Capacity","Plane","Airline"};

        List<Flight> activeFlights = new ArrayList<>();
        for (Flight flight : flightsList) {
            if (!flight.isRemoved() && flight.getDepartureDate().isAfter(LocalDate.now())) {
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
									MainWindow.this,
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
							// TODO Auto-generated catch block
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
    

    public void displayCustomers() {
        List<Customer> customerList = fbs.getCustomers();
        String[] columns = new String[]{"Customer ID", "Name","Age", "Email", "Phone Number", "Number of Booking"};

        List<Customer> activeCustomers = new ArrayList<>();
        for (Customer customer : customerList) {
            if (!customer.isRemoved()) {
                activeCustomers.add(customer);
            }
        }
        
        Object[][] data = new Object[activeCustomers.size()][6];
        for (int i = 0; i < activeCustomers.size(); i++) {
            Customer customer = activeCustomers.get(i);
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getAge();
            data[i][3] = customer.getEmail();
            data[i][4] = customer.getPhone();
            data[i][5] = customer.getBookings().size();
        }

        JTable table = new JTable(data, columns);
        customerModel = table.getSelectionModel();
        customerModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !customerModel.isSelectionEmpty()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int selectedData = (int) table.getValueAt(selectedRow, 0);
                        try {
                            Customer customer = fbs.getCustomerByID(selectedData);
                            new ShowCustomerWindow(customer);
                        } catch (CustomerException e1) {
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
    

    public void sucess() {
    	JOptionPane.showMessageDialog(this, null, "Success", JOptionPane.OK_OPTION);
    }
    
    public void displayAllCustomers() {
        List<Customer> customerList = fbs.getCustomers();
        String[] columns = new String[]{"Customer ID", "Name","Age", "Email", "Phone Number", "Number of Booking"};
        
        Object[][] data = new Object[customerList.size()][6];
        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getAge();
            data[i][3] = customer.getEmail();
            data[i][4] = customer.getPhone();
            data[i][5] = customer.getBookings().size();
        }

        JTable table = new JTable(data, columns);
        customerModel = table.getSelectionModel();
        customerModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !customerModel.isSelectionEmpty()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        int selectedData = (int) table.getValueAt(selectedRow, 0);
                        try {
                            Customer customer = fbs.getCustomerByID(selectedData);
                            new ShowCustomerWindow(customer);
                        } catch (CustomerException e1) {
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

}
