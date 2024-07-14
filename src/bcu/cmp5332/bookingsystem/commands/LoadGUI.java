package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.gui.LandingGUI;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

/**
 * Command to load the graphical user interface (GUI) of the flight booking system.
 * This class implements the {@link Command} interface.
 * 
 * <p>This command initializes and displays the landing GUI of the flight booking system.</p>
 * 
 * @see Command
 * @see FlightBookingSystem
 * @see FlightBookingSystemException
 * @see LandingGUI
 * 
 * @version 1.0
 * 
 */
public class LoadGUI implements Command {

    /**
     * Executes the LoadGUI command, which initializes and displays the landing GUI.
     * 
     * @param flightBookingSystem the flight booking system to be managed through the GUI.
     * @throws FlightBookingSystemException if an error occurs while initializing the GUI.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        new LandingGUI(flightBookingSystem);
    }
}
