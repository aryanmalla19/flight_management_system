package bcu.cmp5332.bookingsystem.main;

/**
 * Custom exception class for handling errors related to customers in the booking system.
 * Extends {@link Exception} class.
 */
public class CustomerException extends Exception {

    /**
     * Constructs a new CustomerException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public CustomerException(String message) {
        super(message);
    }
}
