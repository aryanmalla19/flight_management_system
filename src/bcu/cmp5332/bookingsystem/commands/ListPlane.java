package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Plane;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.util.List;

/**
 * Command to list all planes in the flight booking system.
 * This class implements the {@link Command} interface.
 * 
 * <p>This command retrieves and prints a list of all planes in the flight booking system.
 * It also prints the total number of planes.</p>
 * 
 * @see Command
 * @see Plane
 * @see FlightBookingSystem
 * @see FlightBookingSystemException
 * 
 * @version 1.0
 * 
 */
public class ListPlane implements Command {

    /**
     * Executes the ListPlane command, which retrieves and prints the details of all planes.
     * 
     * @param flightBookingSystem the flight booking system containing the list of planes.
     * @throws FlightBookingSystemException if an error occurs while retrieving the planes.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Plane> planes = flightBookingSystem.getPlanes();
        int counter = 0;
        for (Plane plane : planes) {
            counter++;
            System.out.println(plane.getDetailsShort());
        }
        System.out.println(counter + " plane(s)");
    }
}
