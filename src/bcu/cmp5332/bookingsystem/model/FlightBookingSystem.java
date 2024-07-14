package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.main.CustomerException;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents the Flight Booking System.
 * Manages customers, flights, bookings, planes, and airlines.
 * Provides methods to add, retrieve, and manage these entities.
 */
public class FlightBookingSystem {
    
    private final LocalDate systemDate = LocalDate.now();
    
    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();
    private final Map<Integer, Booking> bookings = new TreeMap<>();
    private final Map<Integer, Plane> planes = new TreeMap<>();
    private final Map<Integer, Airline> airlines = new TreeMap<>();

    /**
     * Gets the current system date.
     * 
     * @return the current system date
     */
    public LocalDate getSystemDate() {
        return systemDate;
    }
    
    /**
     * Retrieves all flights in the system.
     * 
     * @return an unmodifiable list of flights
     */
    public List<Flight> getFlights() {
        List<Flight> out = new ArrayList<>(flights.values());
        return Collections.unmodifiableList(out);
    }
    
    /**
     * Retrieves all planes in the system.
     * 
     * @return an unmodifiable list of planes
     */
    public List<Plane> getPlanes() {
        List<Plane> out = new ArrayList<>(planes.values());
        return Collections.unmodifiableList(out);
    }
    
    /**
     * Checks if a customer with the same phone and email already exists in the system.
     * 
     * @param customer the customer to check
     * @return true if the customer exists, false otherwise
     */
    public boolean customerExists(Customer customer) {
    	for (Customer existing : customers.values()) {
            if (existing.getPhone().equals(customer.getPhone()) 
                && existing.getEmail().equals(customer.getEmail())) {
                return true;
            }
        }
		return false;
    }

    /**
     * Retrieves a plane by its ID.
     * 
     * @param id the ID of the plane to retrieve
     * @return the plane with the specified ID
     * @throws FlightBookingSystemException if no plane with the ID is found
     */
    public Plane getPlaneByID(int id) throws FlightBookingSystemException {
        if (!planes.containsKey(id)) {
            throw new FlightBookingSystemException("There is no plane with that ID.");
        }
        return planes.get(id);
    }
    
    public Booking getBookingByID(int id) throws FlightBookingSystemException {
        if (!bookings.containsKey(id)) {
            throw new FlightBookingSystemException("There is no plane with that ID.");
        }
        return bookings.get(id);
    }
    
    /**
     * Retrieves all airlines in the system.
     * 
     * @return an unmodifiable list of airlines
     */
    public List<Airline> getAirlines() {
        List<Airline> out = new ArrayList<>(airlines.values());
        return Collections.unmodifiableList(out);
    }

    /**
     * Retrieves an airline by its ID.
     * 
     * @param id the ID of the airline to retrieve
     * @return the airline with the specified ID
     * @throws FlightBookingSystemException if no airline with the ID is found
     */
    public Airline getAirlineByID(int id) throws FlightBookingSystemException {
        if (!airlines.containsKey(id)) {
            throw new FlightBookingSystemException("There is no airline with that ID.");
        }
        return airlines.get(id);
    }

    /**
     * Retrieves a flight by its ID.
     * 
     * @param id the ID of the flight to retrieve
     * @return the flight with the specified ID
     * @throws FlightBookingSystemException if no flight with the ID is found
     */
    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        if (!flights.containsKey(id)) {
            throw new FlightBookingSystemException("There is no flight with that ID.");
        }
        return flights.get(id);
    }

    /**
     * Retrieves all customers in the system.
     * 
     * @return an unmodifiable list of customers
     */
    public List<Customer> getCustomers() {
        List<Customer> out = new ArrayList<>(customers.values());
        return Collections.unmodifiableList(out);
    }
    
    /**
     * Retrieves a customer by their ID.
     * 
     * @param id the ID of the customer to retrieve
     * @return the customer with the specified ID
     * @throws CustomerException if no customer with the ID is found
     */
    public Customer getCustomerByID(int id) throws CustomerException {
        if (!customers.containsKey(id)) {
            throw new CustomerException("There is no customer with that ID.");
        }
        return customers.get(id);
    }
    
    /**
     * Retrieves a customer by their phone number.
     * 
     * @param phone the phone number of the customer to retrieve
     * @return the customer with the specified phone number
     * @throws CustomerException if no customer with the phone number is found
     */
    public Customer getCustomerByPhone(String phone) throws CustomerException {
        for (Customer existing : customers.values()) {
            if (existing.getPhone().equals(phone)) {
            	return existing;
            }
        }
		return null;
    }

    /**
     * Retrieves all bookings in the system.
     * 
     * @return an unmodifiable list of bookings
     */
    public List<Booking> getBookings(){
    	List<Booking> out = new ArrayList<>(bookings.values());
        return Collections.unmodifiableList(out);
    }
    
    /**
     * Cancels a booking.
     * 
     * @param booking the booking to cancel
     */
    public void cancelBooking(Booking booking) {
    	bookings.remove(booking.getId());
    }
    
    /**
     * Adds a booking to the system.
     * 
     * @param booking the booking to add
     * @throws FlightBookingSystemException if there's an issue adding the booking
     */
    public void addBooking(Booking booking) throws FlightBookingSystemException{
        bookings.put(booking.getId(), booking);
    }
    
    /**
     * Adds a flight to the system.
     * 
     * @param flight the flight to add
     * @throws FlightBookingSystemException if there's an issue adding the flight
     */
    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException("Duplicate flight ID.");
        }
        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equals(flight.getFlightNumber()) 
                && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("There is a flight with the same number and departure date in the system");
            }
        }
        flights.put(flight.getId(), flight);
    }
    
    /**
     * Adds a plane to the system.
     * 
     * @param plane the plane to add
     * @throws FlightBookingSystemException if there's an issue adding the plane
     */
    public void addPlane(Plane plane) throws FlightBookingSystemException {
        if (planes.containsKey(plane.getId())) {
            throw new IllegalArgumentException("Duplicate plane ID.");
        }
        for (Plane existing : planes.values()) {
            if (existing.getModel().equals(plane.getModel()) 
                && existing.getCapacity() == plane.getCapacity()) {
                throw new FlightBookingSystemException("There is a plane with the same model and capacity in the system");
            }
        }
        planes.put(plane.getId(), plane);
    }
    
    /**
     * Adds an airline to the system.
     * 
     * @param airline the airline to add
     * @throws FlightBookingSystemException if there's an issue adding the airline
     */
    public void addAirline(Airline airline) throws FlightBookingSystemException {
        if (airlines.containsKey(airline.getId())) {
            throw new IllegalArgumentException("Duplicate airline ID.");
        }
        for (Airline existing : airlines.values()) {
            if (existing.getEmail().equals(airline.getEmail()) 
                && existing.getPassword().equals(airline.getPassword())) {
                throw new FlightBookingSystemException("There is an airline with the same email and password in the system");
            }
        }
        airlines.put(airline.getId(), airline);
    }

    /**
     * Adds a customer to the system.
     * 
     * @param customer the customer to add
     * @throws CustomerException if there's an issue adding the customer
     */
    public void addCustomer(Customer customer) throws CustomerException {
        if (customers.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Duplicate customer ID.");
        }
        for (Customer existing : customers.values()) {
            if (existing.getPhone().equals(customer.getPhone()) 
                && existing.getEmail().equals(customer.getEmail())) {
                throw new CustomerException("There is a customer with the same phone and email in the system");
            }
        }
        customers.put(customer.getId(), customer);
    }
}
