package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a flight in the flight booking system.
 * This class contains all the relevant details of a flight, such as flight number,
 * origin, destination, price, plane, and departure date. It also maintains a list of passengers.
 * 
 */
public class Flight {

    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private double price;
    private Plane plane;
    private boolean isRemoved;
    private LocalDate departureDate;
    private final Set<Customer> passengers;

    /**
     * Constructor to create a flight with the specified details.
     * 
     * @param id the ID of the flight
     * @param flightNumber the flight number
     * @param origin the origin of the flight
     * @param destination the destination of the flight
     * @param price the price of the flight
     * @param plane the plane associated with the flight
     * @param departureDate the departure date of the flight
     * @param isRemoved the removal status of the flight
     */
    public Flight(int id, String flightNumber, String origin, String destination, double price, Plane plane, LocalDate departureDate, boolean isRemoved) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.price = price;
        this.setPlane(plane);
        this.departureDate = departureDate;
        this.isRemoved = isRemoved;
        passengers = new HashSet<>();
    }

    /**
     * Constructor to create a flight with the specified details, with isRemoved set to false.
     * 
     * @param id the ID of the flight
     * @param flightNumber the flight number
     * @param origin the origin of the flight
     * @param destination the destination of the flight
     * @param price the price of the flight
     * @param plane the plane associated with the flight
     * @param departureDate the departure date of the flight
     */
    public Flight(int id, String flightNumber, String origin, String destination, double price, Plane plane, LocalDate departureDate) {
        this(id, flightNumber, origin, destination, price, plane, departureDate, false);
    }

    /**
     * Gets the price of the flight.
     * 
     * @return the price of the flight
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Checks if the flight is removed.
     * 
     * @return true if the flight is removed, false otherwise
     */
    public boolean isRemoved() {
        return this.isRemoved;
    }

    /**
     * Marks the flight as removed.
     */
    public void removeFlight() {
        this.isRemoved = true;
    }

    /**
     * Sets a new price for the flight.
     * 
     * @param newPrice the new price of the flight
     */
    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    /**
     * Sets a new capacity for the plane associated with the flight.
     * 
     * @param newCapacity the new capacity of the plane
     */
    public void setCapacity(int newCapacity) {
        this.plane.setCapacity(newCapacity);
    }

    /**
     * Gets the capacity of the plane associated with the flight.
     * 
     * @return the capacity of the plane
     */
    public int getCapacity() {
        return this.plane.getCapacity();
    }

    /**
     * Gets the ID of the flight.
     * 
     * @return the ID of the flight
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the flight.
     * 
     * @param id the new ID of the flight
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the flight number.
     * 
     * @return the flight number
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Gets the remaining capacity of the flight.
     * 
     * @return the remaining capacity of the flight
     */
    public int getRemainingCapacity() {
        return this.plane.getCapacity() - passengers.size();
    }

    /**
     * Sets the flight number.
     * 
     * @param flightNumber the new flight number
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * Gets the origin of the flight.
     * 
     * @return the origin of the flight
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the origin of the flight.
     * 
     * @param origin the new origin of the flight
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Gets the destination of the flight.
     * 
     * @return the destination of the flight
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the destination of the flight.
     * 
     * @param destination the new destination of the flight
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Gets the departure date of the flight.
     * 
     * @return the departure date of the flight
     */
    public LocalDate getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the departure date of the flight.
     * 
     * @param departureDate the new departure date of the flight
     */
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Gets the list of passengers on the flight.
     * 
     * @return a list of passengers on the flight
     */
    public List<Customer> getPassengers() {
        return new ArrayList<>(passengers);
    }

    /**
     * Gets a short description of the flight.
     * 
     * @return a short description of the flight
     */
    public String getDetailsShort() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to " 
                + destination + " on " + departureDate.format(dtf) + " - Price: " + Booking.calculateDynamicPrice(this) + " - Plane: " + plane.getModel() + " - Airline: " + plane.getAirline().getName();
    }

    /**
     * Gets a detailed description of the flight.
     * 
     * @return a detailed description of the flight
     */
    public String getDetailsLong() {
        StringBuilder result = new StringBuilder();
        result.append("Flight #").append(getId()).append("\nFlight No: ").append(getFlightNumber())
                .append("\nOrigin: ").append(getOrigin()).append("\nDestination: ").append(getDestination())
                .append("\nBase Price: ").append(getPrice()).append("\nCapacity: ").append(getCapacity())
                .append("\nRemaining Capacity: ").append(getRemainingCapacity())
                .append("\nDeparture Date: ").append(getDepartureDate())
                .append("\nPlane Model: ").append(plane.getModel())
                .append("\nAirline: ").append(plane.getAirline().getName())
                .append("\n-------------\nPassengers:\n");

        int counter = 0;
        if (!passengers.isEmpty()) {
            for (Customer passenger : passengers) {
                result.append("\n* Id: ").append(passenger.getId())
                        .append(" - ").append(passenger.getName())
                        .append(" - ").append(passenger.getPhone())
                        .append(" - Rs.");

                // Calculate the total booking price for this passenger on this flight
                List<Booking> bookings = passenger.getBookings();
                for (Booking booking : bookings) {
                    if (booking.getFlight() == this) {
                        result.append(booking.getPrice());
                    }
                }
                counter++;
            }
            result.append("\n").append(counter).append(" passenger(s)");
        } else {
            result.append("\n No passengers booked on this flight");
        }
        return result.toString();
    }

    /**
     * Adds a passenger to the flight.
     * 
     * @param passenger the passenger to add
     */
    public void addPassenger(Customer passenger) {
        passengers.add(passenger);
    }

    /**
     * Removes a passenger from the flight.
     * 
     * @param passenger the passenger to remove
     */
    public void removePassenger(Customer passenger) {
        passengers.remove(passenger);
    }

    /**
     * Gets the plane associated with the flight.
     * 
     * @return the plane associated with the flight
     */
    public Plane getPlane() {
        return plane;
    }

    /**
     * Sets the plane associated with the flight.
     * 
     * @param plane the new plane associated with the flight
     */
    public void setPlane(Plane plane) {
        this.plane = plane;
    }

	public Object getFlightName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRemoved(boolean b) {
		this.isRemoved = b;
		// TODO Auto-generated method stub
		
	}
}
