package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Airline;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class handles the loading and storing of airline data for the Flight Booking System.
 * It implements the DataManager interface to provide the functionality for managing airline data.
 * 
 * @version 1.0
 * 
 */
public class AirlineDataManager implements DataManager {
    
    private final String RESOURCE = "./resources/data/airlines.txt";
    
    /**
     * Loads airline data from a file into the Flight Booking System.
     * 
     * @param fbs the Flight Booking System instance into which data will be loaded.
     * @throws IOException if an I/O error occurs during data loading.
     * @throws FlightBookingSystemException if a general error occurs in the flight booking system.
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
                    String name = properties[1];
                    String email = properties[2];
                    String password = properties[3];
                    Airline airline = new Airline(id, name, email, password);
                    fbs.addAirline(airline);
                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to parse airline id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }
    
    /**
     * Stores airline data from the Flight Booking System to a file.
     * 
     * @param fbs the Flight Booking System instance from which data will be stored.
     * @throws IOException if an I/O error occurs during data storage.
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Airline airline : fbs.getAirlines()) {
                out.print(airline.getId() + SEPARATOR);
                out.print(airline.getName() + SEPARATOR);
                out.print(airline.getEmail() + SEPARATOR);
                out.print(airline.getPassword() + SEPARATOR);
                out.println();
            }
        }
    }
}
