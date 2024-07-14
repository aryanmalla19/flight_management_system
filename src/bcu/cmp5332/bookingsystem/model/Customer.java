package bcu.cmp5332.bookingsystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Customer class represents a customer in the flight booking system.
 * It contains attributes such as id, name, phone, email, age, and a list of bookings.
 */
public class Customer {

    private int id;
    private String name;
    private String phone;
    private String email;
    private int age;
    private boolean isRemoved;
    private final List<Booking> bookings = new ArrayList<>();

    /**
     * Constructs a Customer object with the specified id, name, age, phone, email, and removal status.
     * 
     * @param id The unique identifier of the customer.
     * @param name The name of the customer.
     * @param age The age of the customer.
     * @param phone The phone number of the customer.
     * @param email The email address of the customer.
     * @param isRemoved The removal status of the customer (true if removed, false otherwise).
     */
    public Customer(int id, String name, int age, String phone, String email, boolean isRemoved) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.isRemoved = isRemoved;
    }

    /**
     * Constructs a Customer object with the specified id, name, age, phone, and email.
     * Initializes the removal status to false.
     * 
     * @param id The unique identifier of the customer.
     * @param name The name of the customer.
     * @param age The age of the customer.
     * @param phone The phone number of the customer.
     * @param email The email address of the customer.
     */
    public Customer(int id, String name, int age, String phone, String email) {
        this(id, name, age, phone, email, false);
    }

    /**
     * Checks if the customer is removed from the system.
     * 
     * @return true if the customer is removed, false otherwise.
     */
    public boolean isRemoved() {
        return this.isRemoved;
    }

    /**
     * Marks the customer as removed from the system.
     */
    public void removeCustomer() {
        this.isRemoved = true;
    }

    /**
     * Retrieves the email address of the customer.
     * 
     * @return The email address of the customer.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets a new email address for the customer.
     * 
     * @param newEmail The new email address to set.
     */
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    /**
     * Retrieves the unique identifier of the customer.
     * 
     * @return The id of the customer.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Retrieves the list of bookings made by the customer.
     * 
     * @return The list of bookings made by the customer.
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Retrieves a short summary of customer details.
     * 
     * @return A short summary of customer details.
     */
    public String getDetailsShort() {
        return "Customer ID #" + id + ", Name - " + name + ", Phone - " + phone + ", Age - " + age;
    }

    /**
     * Retrieves a detailed summary of customer details, including bookings.
     * 
     * @return A detailed summary of customer details.
     */
    public String getDetailsLong() {
        StringBuilder result = new StringBuilder();
        result.append("Customer ID #").append(id).append("\nName: ").append(name)
                .append("\nEmail: ").append(email).append("\nPhone: ").append(phone)
                .append("\n-----------------\nBookings:");

        if (!bookings.isEmpty()) {
            int counter = 0;
            for (Booking booking : bookings) {
                result.append("\n* Booking date: ").append(booking.getBookingDate())
                        .append(" for Flight #").append(booking.getFlight().getId())
                        .append(" - ").append(booking.getFlight().getFlightNumber())
                        .append(" - ").append(booking.getFlight().getOrigin())
                        .append(" to ").append(booking.getFlight().getDestination())
                        .append(" on ").append(booking.getFlight().getDepartureDate())
                        .append(". Price: Rs.").append(booking.getPrice());
                counter++;
            }
            result.append("\n").append(counter).append(" booking(s)");
        } else {
            result.append("\n No Booking has been made by this customer");
        }
        return result.toString();
    }

    /**
     * Retrieves the name of the customer.
     * 
     * @return The name of the customer.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the phone number of the customer.
     * 
     * @return The phone number of the customer.
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Sets a new unique identifier for the customer.
     * 
     * @param newId The new id to set for the customer.
     */
    public void setId(int newId) {
        this.id = newId;
    }

    /**
     * Sets a new name for the customer.
     * 
     * @param newName The new name to set for the customer.
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Sets a new phone number for the customer.
     * 
     * @param newPhone The new phone number to set for the customer.
     */
    public void setPhone(String newPhone) {
        this.phone = newPhone;
    }

    /**
     * Adds a new booking to the list of bookings made by the customer.
     * 
     * @param booking The booking to add.
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Cancels a booking from the list of bookings made by the customer.
     * 
     * @param booking The booking to cancel.
     */
    public void cancelBooking(Booking booking) {
        bookings.remove(booking);
    }

    /**
     * Retrieves the age of the customer.
     * 
     * @return The age of the customer.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets a new age for the customer.
     * 
     * @param age The new age to set for the customer.
     */
    public void setAge(int age) {
        this.age = age;
    }

	public void setRemoved(boolean b) {
		this.isRemoved = b;
		// TODO Auto-generated method stub
		
	}
}
