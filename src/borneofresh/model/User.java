package borneofresh.model;

/**
 * Abstract base class representing a user of the BorneoFresh Market system.
 * Defines common attributes and behaviours shared by Administrator and Customer.
 * Cannot be instantiated directly — must be extended by a concrete subclass.
 */
public abstract class User {

    private String username;
    private String password;

    /**
     * Constructs a new User with the given username and password.
     *
     * @param username the login identifier
     * @param password the login credential
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /** Returns the username. */
    public String getUsername() { return username; }

    /** Returns the password. */
    public String getPassword() { return password; }

    /** Updates the username. */
    public void setUsername(String username) { this.username = username; }

    /** Updates the password. */
    public void setPassword(String password) { this.password = password; }

    /**
     * Returns the role of this user.
     * Overridden by each subclass to return "Administrator" or "Customer".
     *
     * @return a String representing the user's role
     */
    public abstract String getRole();

    /**
     * Displays the relevant information for this user.
     * Overridden by each subclass to show its own specific fields.
     */
    public abstract void displayInfo();
}