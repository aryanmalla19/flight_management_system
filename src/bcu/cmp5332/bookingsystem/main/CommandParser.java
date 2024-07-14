package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.*;
import bcu.cmp5332.bookingsystem.model.Airline;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * The CommandParser class parses user input commands and creates Command objects accordingly.
 * It validates user roles and permissions, and interacts with the FlightBookingSystem.
 * 
 */
public class CommandParser {

    /**
     * Parses the user input command and returns the corresponding Command object.
     * 
     * @param line The user input command line
     * @param role The role of the user (admin, airline, customer)
     * @return Command object corresponding to the user input command
     * @throws IOException If an I/O error occurs
     * @throws FlightBookingSystemException If there is an error in command parsing or execution
     */
    public static Command parse(String line, String role) throws IOException, FlightBookingSystemException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String[] parts = line.split(" ", 3);
            String cmd = parts[0];

            if (cmd.equals("addflight")) {
                checkPermissions(role, "airline");
                System.out.print("Flight Number: ");
                String flightNumber = reader.readLine();
                System.out.print("Origin: ");
                String origin = reader.readLine();
                System.out.print("Destination: ");
                String destination = reader.readLine();
                System.out.print("Price: ");
                double price = Double.parseDouble(reader.readLine());
                System.out.print("Plane ID: ");
                int planeId = Integer.parseInt(reader.readLine());
                LocalDate departureDate = parseDateWithAttempts(reader);
                return new AddFlight(flightNumber, origin, destination, price, planeId, departureDate);
            } else if (cmd.equals("addcustomer")) {
                checkPermissions(role, "customer");
                System.out.print("Customer Name: ");
                String name = reader.readLine();
                System.out.print("Customer Age: ");
                int age = Integer.parseInt(reader.readLine());
                System.out.print("Phone: ");
                String phone = reader.readLine();
                System.out.print("Email: ");
                String email = reader.readLine();
                return new AddCustomer(name, age, phone, email);
            } else if (cmd.equals("addplane")) {
                checkPermissions(role, "airline");
                System.out.print("Plane Model Name: ");
                String name = reader.readLine();
                System.out.print("Capacity: ");
                int capacity = Integer.parseInt(reader.readLine());
                System.out.print("Airline ID: ");
                int airlineId = Integer.parseInt(reader.readLine());
                return new AddPlane(name, capacity, airlineId);
            } else if (cmd.equals("addairline")) {
                checkPermissions(role, "admin");
                System.out.print("Airline Name: ");
                String name = reader.readLine();
                System.out.print("Email: ");
                String email = reader.readLine();
                System.out.print("Password: ");
                String password = reader.readLine();
                return new AddAirline(name, email, password);
            } else if (cmd.equals("loadgui")) {
                return new LoadGUI();
            } else if (parts.length == 1) {
                if (line.equals("listflights")) {
                    return new ListFlights();
                } else if (line.equals("listplanes")) {
                    return new ListPlane();
                } else if (line.equals("listcustomers")) {
                    return new ListCustomers();
                } else if (line.equals("help")) {
                    return new Help(role);
                }
            } else if (parts.length == 2) {
                int id = Integer.parseInt(parts[1]);

                if (cmd.equals("showflight")) {
                    return new ShowFlight(--id); // Adjusting id to zero-based index
                } else if (cmd.equals("showcustomer")) {
                    return new ShowCustomer(--id); // Adjusting id to zero-based index
                }
            } else if (parts.length == 3) {
                int customer_id = Integer.parseInt(parts[1]);
                int flight_id = Integer.parseInt(parts[2]);

                if (cmd.equals("addbooking")) {
                    checkPermissions(role, "customer");
                    return new AddBooking(customer_id, flight_id);
                } else if (cmd.equals("cancelbooking")) {
                    checkPermissions(role, "customer");
                    return new CancelBooking(customer_id, flight_id);
                } else if (cmd.equals("editbooking")) {
                    checkPermissions(role, "customer");
                    return new EditBooking(customer_id, flight_id);
                }
            }
        } catch (NumberFormatException ex) {
            throw new FlightBookingSystemException("Invalid number format.");
        }

        throw new FlightBookingSystemException("Invalid command.");
    }

    /**
     * Checks if the user has the required permissions to execute a command.
     * 
     * @param role The role of the user
     * @param requiredRole The required role to execute the command
     * @throws FlightBookingSystemException If the user doesn't have the required permissions
     */
    private static void checkPermissions(String role, String requiredRole) throws FlightBookingSystemException {
        if (!role.equals("admin") && !role.equals(requiredRole)) {
            throw new FlightBookingSystemException("You don't have permissions to execute this command.");
        }
    }

    /**
     * Verifies if the user is an admin based on provided credentials.
     * 
     * @param reader BufferedReader for reading user input
     * @return true if user is verified as admin, false otherwise
     * @throws IOException If an I/O error occurs
     */
    private static boolean verifyAdmin(BufferedReader reader) throws IOException {
        System.out.print("Username: ");
        String username = reader.readLine();
        System.out.print("Password: ");
        String password = reader.readLine();
        return "admin".equals(username) && "admin".equals(password);
    }

    /**
     * Verifies if the user is an airline based on provided credentials.
     * 
     * @param reader BufferedReader for reading user input
     * @param airlines List of airlines for verification
     * @return true if user is verified as airline, false otherwise
     * @throws IOException If an I/O error occurs
     */
    private static boolean verifyAirline(BufferedReader reader, List<Airline> airlines) throws IOException {
        System.out.print("Email: ");
        String email = reader.readLine();
        System.out.print("Password: ");
        String password = reader.readLine();
        for (Airline airline : airlines) {
            if (airline.getEmail().equals(email) && airline.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses a LocalDate from user input with multiple attempts.
     * 
     * @param br BufferedReader for reading user input
     * @param attempts Number of attempts allowed to parse the date correctly
     * @return LocalDate parsed from user input
     * @throws IOException If an I/O error occurs
     * @throws FlightBookingSystemException If the date cannot be parsed correctly within attempts
     */
    private static LocalDate parseDateWithAttempts(BufferedReader br, int attempts) throws IOException, FlightBookingSystemException {
        if (attempts < 1) {
            throw new IllegalArgumentException("Number of attempts should be higher than 0");
        }
        while (attempts > 0) {
            attempts--;
            System.out.print("Departure Date (\"YYYY-MM-DD\" format): ");
            try {
                LocalDate departureDate = LocalDate.parse(br.readLine());
                if (departureDate.isAfter(LocalDate.now()) || departureDate.isEqual(LocalDate.now())) {
                    return departureDate;
                } else {
                    System.out.println("Date should be after " + LocalDate.now() + ". " + attempts + " attempts remaining...");
                }
            } catch (DateTimeParseException dtpe) {
                System.out.println("Date must be in YYYY-MM-DD format. " + attempts + " attempts remaining...");
            }
        }
        throw new FlightBookingSystemException("Incorrect departure date provided. Cannot create flight.");
    }

    /**
     * Prompts the user to select a role (admin, airline, customer) and verifies credentials.
     * 
     * @param fbs The FlightBookingSystem instance for accessing data
     * @return The role selected by the user
     * @throws IOException If an I/O error occurs
     */
    public static String checkRole(FlightBookingSystem fbs) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print("Who are you? (admin, airline, customer): ");
            String role = reader.readLine().toLowerCase();

            if (role.equals("admin")) {
                if (verifyAdmin(reader)) {
                    return "admin";
                } else {
                    System.out.println("Invalid admin credentials.");
                }
            } else if (role.equals("airline")) {
                if (verifyAirline(reader, fbs.getAirlines())) {
                    return "airline";
                } else {
                    System.out.println("Invalid airline credentials.");
                }
            } else if (role.equals("customer")) {
                return "customer";
            } else {
                System.out.println("Invalid role. Please specify admin, airline, or customer.");
            }
        }
    }

    /**
     * Parses a LocalDate from user input with default attempts (3).
     * 
     * @param br BufferedReader for reading user input
     * @return LocalDate parsed from user input
     * @throws IOException If an I/O error occurs
     * @throws FlightBookingSystemException If the date cannot be parsed correctly within attempts
     */
    private static LocalDate parseDateWithAttempts(BufferedReader br) throws IOException, FlightBookingSystemException {
        return parseDateWithAttempts(br, 3);
    }
}
