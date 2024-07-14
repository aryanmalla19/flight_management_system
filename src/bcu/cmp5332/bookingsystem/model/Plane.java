package bcu.cmp5332.bookingsystem.model;

/**
 * Represents an aircraft (plane) in the flight booking system.
 */
public class Plane {
    
    private String model;
    private int capacity;
    private int id;
    private Airline airline;

    /**
     * Constructor to create a Plane object with the specified details.
     * 
     * @param id the ID of the plane
     * @param model the model of the plane
     * @param capacity the seating capacity of the plane
     * @param airline the airline that operates the plane
     */
    public Plane(int id, String model, int capacity, Airline airline) {
        this.id = id;
        this.model = model;
        this.capacity = capacity;
        this.airline = airline;
    }

    /**
     * Gets the model of the plane.
     * 
     * @return the model of the plane
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the plane.
     * 
     * @param model the new model of the plane
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the seating capacity of the plane.
     * 
     * @return the seating capacity of the plane
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the seating capacity of the plane.
     * 
     * @param capacity the new seating capacity of the plane
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the ID of the plane.
     * 
     * @return the ID of the plane
     */
    public int getId() {
        return id;
    }

    /**
     * Gets a short description of the plane.
     * 
     * @return a short description of the plane
     */
    public String getDetailsShort() {
        return "Plane #" + id + " - Model: " + model + " - Airline: " + airline.getName() + " - Capacity: " + capacity; 
    }

    /**
     * Gets the airline that operates the plane.
     * 
     * @return the airline that operates the plane
     */
    public Airline getAirline() {
        return airline;
    }

    /**
     * Sets the airline that operates the plane.
     * 
     * @param airline the new airline that operates the plane
     */
    public void setAirline(Airline airline) {
        this.airline = airline;
    }

	public void setId(int id) {
		this.id=id;
		// TODO Auto-generated method stub
		
	}
}
