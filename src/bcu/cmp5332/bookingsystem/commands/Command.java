package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * This interface represents a command that can be executed in the flight booking system.
 * Implementing classes need to define the execute method.
 * 
 */
public interface Command {

    /**
     * Executes the command using the provided flight booking system.
     * 
     * @param flightBookingSystem the flight booking system
     * @throws FlightBookingSystemException if there is an issue with the flight booking system
     * @throws CustomerException if there is an issue with the customer
     */
    void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException, CustomerException;

    /**
     * Provides help messages for different roles in the flight booking system.
     * 
     * @param role the role of the user (admin, airline, customer)
     * @return the help message as a string
     */
    static String getMessage(String role) {
        switch (role.toLowerCase()) {
            case "admin":
                return "Commands:\n"
                    + "\tlistflights                               print all flights\n"
                    + "\tlistcustomers                             print all customers\n"
                    + "\taddflight                                 add a new flight\n"
                    + "\taddcustomer                               add a new customer\n"
                    + "\taddplane                                  add a new plane\n"
                    + "\taddairline                                add a new airline\n"
                    + "\tlistplanes                                print all planes\n"
                    + "\tshowflight [flight id]                    show flight details\n"
                    + "\tshowcustomer [customer id]                show customer details\n"
                    + "\taddbooking [customer id] [flight id]      add a new booking\n"
                    + "\tcancelbooking [customer id] [flight id]   cancel a booking\n"
                    + "\teditbooking [booking id] [flight id]      update a booking\n"
                    + "\tloadgui                                   loads the GUI version of the app\n"
                    + "\thelp                                      prints this help message\n"
                    + "\texit                                      exits the program";
            case "airline":
                return "Commands:\n"
                    + "\tlistflights                               print all flights\n"
                    + "\tloadgui                                   loads the GUI version of the app\n"                    
                    + "\taddflight                                 add a new flight\n"
                    + "\taddplane                                  add a new plane\n"
                    + "\tlistplanes                                print all planes\n"
                    + "\tshowflight [flight id]                    show flight details\n"
                    + "\thelp                                      prints this help message\n"
                    + "\texit                                      exits the program";
            case "customer":
                return "Commands:\n"
                    + "\tlistflights                               print all flights\n"
                    + "\taddcustomer                               add a new customer\n"
                    + "\tshowflight [flight id]                    show flight details\n"
                    + "\taddbooking [customer id] [flight id]      add a new booking\n"
                    + "\tcancelbooking [customer id] [flight id]   cancel a booking\n"
                    + "\thelp                                      prints this help message\n"
                    + "\tloadgui                                   loads the GUI version of the app\n"
                    + "\texit                                      exits the program";
            default:
                return "Invalid role.";
        }
    }
}
