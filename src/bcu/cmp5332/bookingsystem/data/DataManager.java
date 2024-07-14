package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;

/**
 * Interface for managing data operations related to the Flight Booking System.
 * 
 * <p>This interface defines methods for loading and storing data from/to
 * persistent storage. Implementing classes should provide the concrete
 * mechanisms for these operations.</p>
 * 
 * @version 1.0
 * 
 */
public interface DataManager {
    
    /**
     * Separator used in data files for splitting fields.
     */
    public static final String SEPARATOR = "::";
    
    /**
     * Loads data into the specified Flight Booking System instance.
     * 
     * @param fbs the Flight Booking System instance into which data will be loaded.
     * @throws IOException if an I/O error occurs during data loading.
     * @throws FlightBookingSystemException if a general error occurs in the flight booking system.
     * @throws CustomerException if an error specific to customers occurs.
     */
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException, CustomerException;
    
    /**
     * Stores data from the specified Flight Booking System instance to persistent storage.
     * 
     * @param fbs the Flight Booking System instance from which data will be stored.
     * @throws IOException if an I/O error occurs during data storage.
     */
    public void storeData(FlightBookingSystem fbs) throws IOException;
}
