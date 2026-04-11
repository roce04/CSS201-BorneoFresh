package borneofresh.model;

/**
 * Abstract base class representing a user of the BorneoFresh Market system.
 * This class defines the common attributes and behaviours shared by all user
 * types, including Administrator and Customer. It cannot be instantiated
 * directly and must be extended by a concrete subclass.
 */
public abstract class User {

    /** The username used to log in to the system. */
    private String username;

    /** The password used to authenticate the user. */
    private String password;

    /**
     * Constructs a new User with the specified username and password.
     *
     * @param username the login identifier for the user
     * @param password the login credential for the user
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username of this user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of this user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Updates the username of this user.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Updates the password of this user.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the role of this user.
     * Each subclass must override this method to return its specific role,
     * such as "Administrator" or "Customer". This method is the primary
     * driver of polymorphism in the user hierarchy.
     *
     * @return a String representing the user's role
     */
    public abstract String getRole();

    /**
     * Displays the relevant information for this user.
     * Each subclass must override this method to display its own
     * specific fields and details.
     */
    public abstract void displayInfo();
}