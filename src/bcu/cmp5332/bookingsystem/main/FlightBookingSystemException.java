package bcu.cmp5332.bookingsystem.main;

/**
 * Custom exception class for handling errors in the flight booking system.
 * Extends {@link Exception} class.
 */
public class FlightBookingSystemException extends Exception {

    /**
     * Constructs a new FlightBookingSystemException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public FlightBookingSystemException(String message) {
        super(message);
    }
}
