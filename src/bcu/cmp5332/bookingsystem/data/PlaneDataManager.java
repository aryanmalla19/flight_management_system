package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Airline;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Plane;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The PlaneDataManager class manages loading and storing plane data to/from a text file.
 * It implements the DataManager interface.
 * 
 */
public class PlaneDataManager implements DataManager {
    
    private final String RESOURCE = "./resources/data/planes.txt";
    
    /**
     * Loads plane data from the planes data file into the FlightBookingSystem.
     *
     * @param fbs The FlightBookingSystem instance to load data into
     * @throws IOException If an I/O error occurs while reading the data file
     * @throws FlightBookingSystemException If there is an error in parsing the data or adding planes to fbs
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
                    String model = properties[1];
                    int capacity = Integer.parseInt(properties[2]);
                    int airlineID = Integer.parseInt(properties[3]);
                    Airline airline = fbs.getAirlineByID(airlineID);
                    Plane plane = new Plane(id, model, capacity, airline);
                    fbs.addPlane(plane);
                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to parse plane id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }
    
    /**
     * Stores plane data from the FlightBookingSystem into the planes data file.
     *
     * @param fbs The FlightBookingSystem instance containing the planes to store
     * @throws IOException If an I/O error occurs while writing the data file
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Plane plane : fbs.getPlanes()) {
                out.print(plane.getId() + SEPARATOR);
                out.print(plane.getModel() + SEPARATOR);
                out.print(plane.getCapacity() + SEPARATOR);
                out.print(plane.getAirline().getId() + SEPARATOR);
                out.println();
            }
        }
    }
}
