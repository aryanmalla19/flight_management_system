package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Airline;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Plane;

/**
 * Command to add a new plane to the flight booking system.
 * This class handles the creation and addition of a new plane.
 * 
 */
public class AddPlane implements Command {

    private final String model;
    private final int capacity;
    private final int airlineId;

    /**
     * Constructor to create an AddPlane command with specified plane details.
     * 
     * @param model the model of the plane
     * @param capacity the capacity of the plane
     * @param airlineId the ID of the airline
     */
    public AddPlane(String model, int capacity, int airlineId) {
        this.model = model;
        this.capacity = capacity;
        this.airlineId = airlineId;
    }

    /**
     * Executes the AddPlane command, adding a plane to the flight booking system.
     * 
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if there is an issue with the flight booking system
     * @throws CustomerException if there is an issue with the customer
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException, CustomerException {
        int maxId = 0;
        if (flightBookingSystem.getPlanes().size() > 0) {
            int lastIndex = flightBookingSystem.getPlanes().size() - 1;
            maxId = flightBookingSystem.getPlanes().get(lastIndex).getId();
        }
        Airline airline = flightBookingSystem.getAirlineByID(airlineId);
        Plane plane = new Plane(++maxId, model, capacity, airline);
        flightBookingSystem.addPlane(plane);
        System.out.println("Plane #" + plane.getId() + " " + plane.getModel() + " added.");
    }
}
