package bcu.cmp5332.bookingsystem.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.gui.LandingGUI;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Main class for the Flight Booking System application.
 * This class contains the main method which is the entry point of the application.
 */
public class Main {
    
    /**
     * The main method that runs the Flight Booking System application.
     * @param args Command line arguments (not used).
     * @throws IOException If an input or output exception occurs.
     * @throws FlightBookingSystemException If an exception specific to the flight booking system occurs.
     * @throws CustomerException If an exception related to customer operations occurs.
     */
    public static void main(String[] args) throws IOException, FlightBookingSystemException, CustomerException {
        
        // Load the flight booking system data from persistent storage
        FlightBookingSystem fbs = FlightBookingSystemData.load();
        
        new LandingGUI(fbs);
        // Determine the role of the user (admin, airline, customer)
        String role = CommandParser.checkRole(fbs);
        
        // Create a BufferedReader to read input from the console
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // Display the welcome message and instructions
        System.out.println("Flight Booking System");
        System.out.println("Enter 'help' to see a list of available commands.");
        
        // Command processing loop
        while (true) {
            System.out.print("> ");
            String line = br.readLine();
            
            // Exit the loop if the user types "exit"
            if (line.equals("exit")) {
                break;
            }
            
            try {
                // Parse the user input into a Command object and execute it
                Command command = CommandParser.parse(line, role);
                command.execute(fbs);
                
                // Save the updated flight booking system data to persistent storage
                FlightBookingSystemData.store(fbs);
                
            } catch (FlightBookingSystemException ex) {
                // Print the exception message if a FlightBookingSystemException occurs
                System.out.println(ex.getMessage());
            }
        }
        
        // Close the BufferedReader
        br.close();
        
        // Exit the application
        System.exit(0);
    }
}
