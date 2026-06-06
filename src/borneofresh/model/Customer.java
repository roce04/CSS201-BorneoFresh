package borneofresh.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a registered customer of the BorneoFresh Market system.
 * Extends the User abstract class and adds personal account details
 * and order management functionality.
 */
public class Customer extends User {

    private String customerId;
    private String fullName;
    private String email;
    private List<Order> orderHistory;

    /**
     * Constructs a new Customer with the given details.
     *
     * @param username   the login identifier
     * @param password   the login credential
     * @param customerId the unique identifier for this customer
     * @param fullName   the customer's full name
     * @param email      the customer's email address
     */
    public Customer(String username, String password, String customerId,
                    String fullName, String email) {
        super(username, password);
        this.customerId = customerId;
        this.fullName = fullName;
        this.email = email;
        this.orderHistory = new ArrayList<>();
    }

    /** Returns the customer's unique ID. */
    public String getCustomerId() { return customerId; }

    /** Returns the customer's full name. */
    public String getFullName() { return fullName; }

    /** Returns the customer's email address. */
    public String getEmail() { return email; }

    /** Returns the customer's complete order history. */
    public List<Order> getOrderHistory() { return orderHistory; }

    /** Updates the customer's full name. */
    public void setFullName(String fullName) { this.fullName = fullName; }

    /** Updates the customer's email address. */
    public void setEmail(String email) { this.email = email; }

    /**
     * Returns the role of this user.
     *
     * @return "Customer"
     */
    @Override
    public String getRole() { return "Customer"; }

    /**
     * Displays the customer's ID, full name, email, and username to the console.
     */
    @Override
    public void displayInfo() {
        System.out.println("===== Customer Info =====");
        System.out.println("Customer ID : " + customerId);
        System.out.println("Full Name   : " + fullName);
        System.out.println("Email       : " + email);
        System.out.println("Username    : " + getUsername());
    }

    /**
     * Adds a completed Order to this customer's order history.
     *
     * @param order the Order to add to the history
     */
    public void placeOrder(Order order) {
        orderHistory.add(order);
        System.out.println("Order placed successfully.");
    }

    /**
     * Displays a summary of all orders in this customer's order history.
     * If there are no past orders, a message is shown instead.
     */
    public void viewOrderHistory() {
        System.out.println("===== Order History for " + fullName + " =====");
        if (orderHistory.isEmpty()) {
            System.out.println("No past orders found.");
        } else {
            for (Order order : orderHistory) {
                order.displayOrderSummary();
            }
        }
    }
}