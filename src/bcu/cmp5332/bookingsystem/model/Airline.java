package bcu.cmp5332.bookingsystem.model;

/**
 * The Airline class represents an airline entity in the flight booking system.
 * It contains attributes such as id, name, email, and password.
 */
public class Airline {

    private int id;
    private String name;
    private String email;
    private String password;

    /**
     * Constructs an Airline object with the specified id, name, email, and password.
     * @param id The unique identifier of the airline.
     * @param name The name of the airline.
     * @param email The email address associated with the airline.
     * @param password The password used for authentication by the airline.
     */
    public Airline(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Retrieves the name of the airline.
     * @return The name of the airline.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the airline.
     * @param name The new name of the airline.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the email address associated with the airline.
     * @return The email address of the airline.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address associated with the airline.
     * @param email The new email address of the airline.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the password used for authentication by the airline.
     * @return The password of the airline.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password used for authentication by the airline.
     * @param password The new password of the airline.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the unique identifier of the airline.
     * @return The id of the airline.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the airline.
     * @param id The new id of the airline.
     */
    public void setId(int id) {
        this.id = id;
    }
}
