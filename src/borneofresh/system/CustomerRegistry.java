package borneofresh.system;

import borneofresh.model.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all registered Customer accounts.
 * Provides registration, username uniqueness checking, credential
 * validation for login, and listing operations.
 */
public class CustomerRegistry {

    // --- Attributes ---
    private List<Customer> customers = new ArrayList<>();

    // --- Methods ---

    /**
     * Registers a new customer only if the reference is non-null and the
     * username has not already been claimed.
     */
    public void registerCustomer(Customer c) {
        if (c != null && !isUsernameTaken(c.getUsername())) {
            customers.add(c);
            System.out.println("Customer registered successfully.");
        } else {
            System.out.println("Registration failed. Username might already be taken.");
        }
    }

    /**
     * Looks up a customer purely by username (used internally and by the
     * admin display). Returns null if no match is found.
     */
    public Customer getCustomerByUsername(String username) {
        for (Customer c : customers) {
            if (c.getUsername() != null && c.getUsername().equals(username)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Validates credentials for login. Returns the matching Customer if
     * both username AND password are correct, or null otherwise.
     * This is the method BorneoFreshSystem should call during login so
     * that password verification is centralised here rather than spread
     * across the menu code.
     */
    public Customer login(String username, String password) {
        Customer c = getCustomerByUsername(username);
        if (c != null && c.getPassword().equals(password)) {
            return c;
        }
        return null;
    }

    public boolean isUsernameTaken(String username) {
        return getCustomerByUsername(username) != null;
    }

    public void displayAllCustomers() {
        System.out.println("=== Registered Customers ===");
        if (customers.isEmpty()) {
            System.out.println("No customers registered yet.");
        } else {
            for (Customer c : customers) {
                c.displayInfo();
                System.out.println("-------------------------");
            }
        }
    }
}