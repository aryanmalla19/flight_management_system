package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Plane;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * The FlightDataManager class manages loading and storing flight data to/from a text file.
 * It implements the DataManager interface.
 * 
 */
public class FlightDataManager implements DataManager {
    
    private final String RESOURCE = "./resources/data/flights.txt";
    
    /**
     * Loads flight data from the flights data file into the FlightBookingSystem.
     *
     * @param fbs The FlightBookingSystem instance to load data into
     * @throws IOException If an I/O error occurs while reading the data file
     * @throws FlightBookingSystemException If there is an error in parsing the data or adding flights to fbs
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int id = Integer.parseInt(properties[0]);
                    String flightNumber = properties[1];
                    String origin = properties[2];
                    String destination = properties[3];
                    double price = Double.parseDouble(properties[4]);
                    int planeId = Integer.parseInt(properties[5]);
                    LocalDate departureDate = LocalDate.parse(properties[6]);
                    Boolean isRemoved = Boolean.parseBoolean(properties[7]);

                    Plane plane = fbs.getPlaneByID(planeId);
                    Flight flight = new Flight(id, flightNumber, origin, destination, price, plane, departureDate, isRemoved);

                    fbs.addFlight(flight);
                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to parse flight id " + properties[0] + " on line " + line_idx
                            + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }
    
    /**
     * Stores flight data from the FlightBookingSystem into the flights data file.
     *
     * @param fbs The FlightBookingSystem instance containing the flights to store
     * @throws IOException If an I/O error occurs while writing the data file
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Flight flight : fbs.getFlights()) {
                out.print(flight.getId() + SEPARATOR);
                out.print(flight.getFlightNumber() + SEPARATOR);
                out.print(flight.getOrigin() + SEPARATOR);
                out.print(flight.getDestination() + SEPARATOR);
                out.print(flight.getPrice() + SEPARATOR);
                out.print(flight.getPlane().getId() + SEPARATOR);
                out.print(flight.getDepartureDate() + SEPARATOR);
                out.print(flight.isRemoved() + SEPARATOR);
                out.println();
            }
        }
    }
}
