package borneofresh.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a complete customer order in the BorneoFresh Market system.
 * An Order aggregates one or more OrderItems, tracks the customer who placed it,
 * and provides a full order summary including the total cost.
 */
public class Order {

    private String orderId;
    private Customer customer;
    private List<OrderItem> items;
    private String orderDate;

    /**
     * Constructs a new Order with the given details.
     *
     * @param orderId   the unique identifier for this order
     * @param customer  the Customer who placed this order
     * @param orderDate the date on which the order was placed
     */
    public Order(String orderId, Customer customer, String orderDate) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = orderDate;
        this.items = new ArrayList<>();
    }

    /** Returns the order's unique ID. */
    public String getOrderId() { return orderId; }

    /** Returns the customer who placed this order. */
    public Customer getCustomer() { return customer; }

    /** Returns the list of order items in this order. */
    public List<OrderItem> getItems() { return items; }

    /**
     * Adds an OrderItem to this order's item list.
     *
     * @param item the OrderItem to add
     */
    public void addItem(OrderItem item) { items.add(item); }

    /**
     * Calculates and returns the total cost of this order by summing
     * the subtotals of all OrderItems.
     *
     * @return the total cost of the order
     */
    public double getTotalCost() {
        double total = 0.0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    /**
     * Displays a complete order summary to the console, including the order ID,
     * date, customer name, list of purchased products with quantities and
     * subtotals, and the overall total cost.
     */
    public void displayOrderSummary() {
        System.out.println("===== Order Summary =====");
        System.out.println("Order ID  : " + orderId);
        System.out.println("Date      : " + orderDate);
        if (customer != null) {
            System.out.println("Customer  : " + customer.getFullName());
        }
        System.out.println("Items:");
        for (OrderItem item : items) {
            System.out.println("  - " + item.getQuantity() + "x "
                    + item.getProduct().getProductName()
                    + " (RM " + String.format("%.2f", item.getSubtotal()) + ")");
        }
        System.out.println("Total     : RM " + String.format("%.2f", getTotalCost()));
        System.out.println("=========================");
    }
}