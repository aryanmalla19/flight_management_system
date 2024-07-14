package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The FlightBookingSystemData class manages loading and storing of data for the Flight Booking System.
 * It initializes and uses multiple DataManager instances to handle different data entities.
 * 
 * @version 1.0
 * 
 */
public class FlightBookingSystemData {
    
    /**
     * List of DataManagers that handle different types of data.
     */
    private static final List<DataManager> dataManagers = new ArrayList<>();
    
    /**
     * Static initialization block to add DataManagers for different entities.
     */
    static {
        dataManagers.add(new AirlineDataManager());
        dataManagers.add(new PlaneDataManager());
        dataManagers.add(new FlightDataManager());
        
        /* Uncomment the two lines below when the implementation of their 
         * loadData() and storeData() methods is complete */
        dataManagers.add(new CustomerDataManager());
        dataManagers.add(new BookingDataManager());
    }
    
    /**
     * Loads the entire Flight Booking System from persistent storage.
     * 
     * @return an instance of FlightBookingSystem loaded with data.
     * @throws FlightBookingSystemException if an error occurs during system loading.
     * @throws IOException if an I/O error occurs during data loading.
     * @throws CustomerException if an error occurs related to customer operations.
     */
    public static FlightBookingSystem load() throws FlightBookingSystemException, IOException, CustomerException {
        FlightBookingSystem fbs = new FlightBookingSystem();
        for (DataManager dm : dataManagers) {
            dm.loadData(fbs);
        }
        return fbs;
    }

    /**
     * Stores the entire Flight Booking System to persistent storage.
     * 
     * @param fbs the FlightBookingSystem instance to be stored.
     * @throws IOException if an I/O error occurs during data storage.
     */
    public static void store(FlightBookingSystem fbs) throws IOException {
        for (DataManager dm : dataManagers) {
            dm.storeData(fbs);
        }
    }  
}
