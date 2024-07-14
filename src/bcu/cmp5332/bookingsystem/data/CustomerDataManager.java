package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.CustomerException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * The CustomerDataManager class manages the loading and storing of customer data for the Flight Booking System.
 * It implements the DataManager interface to provide functionality for handling customer data operations.
 * 
 * @version 1.0
 * 
 */
public class CustomerDataManager implements DataManager {

    /**
     * The resource path where customer data is stored or loaded from.
     */
    private final String RESOURCE = "./resources/data/customers.txt";

    /**
     * Loads customer data from a file into the Flight Booking System.
     * 
     * @param fbs the Flight Booking System instance into which data will be loaded.
     * @throws IOException if an I/O error occurs during data loading.
     * @throws CustomerException if an error occurs related to customer operations.
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, CustomerException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int id = Integer.parseInt(properties[0]);
                    String name = properties[1];
                    int age = Integer.parseInt(properties[2]);
                    String phone = properties[3];
                    String email = properties[4];
                    boolean isRemoved = Boolean.parseBoolean(properties[5]);
                    Customer customer = new Customer(id, name, age, phone, email, isRemoved);
                    fbs.addCustomer(customer);
                } catch (NumberFormatException ex) {
                    throw new CustomerException("Unable to parse customer id " + properties[0] + " on line " + line_idx
                            + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }

    /**
     * Stores customer data from the Flight Booking System to a file.
     * 
     * @param fbs the Flight Booking System instance from which data will be stored.
     * @throws IOException if an I/O error occurs during data storage.
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : fbs.getCustomers()) {
                out.print(customer.getId() + SEPARATOR);
                out.print(customer.getName() + SEPARATOR);
                out.print(customer.getAge() + SEPARATOR);
                out.print(customer.getPhone() + SEPARATOR);
                out.print(customer.getEmail() + SEPARATOR);
                out.print(customer.isRemoved() + SEPARATOR);
                out.println();
            }
        }
    }
}
