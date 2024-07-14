package bcu.cmp5332.bookingsystem.gui;

import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;

/**
 * Represents a window for displaying detailed information about a flight.
 * Displays a table of passengers on the flight along with their details and booking price.
 * 
 * @version 1.0
 * @author Aryan Malla
 */
public class ShowFlightWindow extends JFrame {
    
    private JLabel customerID;
    private JLabel customerName;
    private JLabel customerEmail;
    private JLabel customerPhone;
    private JLabel customerBooking;

    /**
     * Constructs a ShowFlightWindow object to display information about the given flight.
     * 
     * @param flight the flight whose details are to be displayed
     */
    public ShowFlightWindow(Flight flight) {
        super("Flight Details");
        setSize(580, 420);
        setVisible(true);
        ImageIcon icon = new ImageIcon("resources/images/logo.png");
        setIconImage(icon.getImage());
        initialize(flight);
    }

    /**
     * Initializes the components and layout of the window.
     * 
     * @param flight the flight whose details are to be displayed
     */
    private void initialize(Flight flight) {
        Container contentPane = getContentPane();
        
        String[] columns = new String[]{"Customer ID", "Name", "Email", "Phone Number", "Price"};
        
        Object[][] data = new Object[flight.getPassengers().size()][5];
        for (int i = 0; i < flight.getPassengers().size(); i++) {
            Customer customer = flight.getPassengers().get(i);
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getEmail();
            data[i][3] = customer.getPhone();
            for (Booking booking : customer.getBookings()) {
                if (booking.getFlight() == flight) {
                    data[i][4] = booking.getPrice();
                }
            }
        }

        JTable table = new JTable(data, columns);
        contentPane.removeAll();
        contentPane.add(new JScrollPane(table));
        revalidate();
    }
}
