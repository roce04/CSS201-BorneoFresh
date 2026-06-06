package borneofresh.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    /** The customer who owns this cart. */
    private Customer customer;

    /** Items currently held in the cart — uses existing OrderItem. */
    private List<OrderItem> cartItems;

    /** Creates a new, empty Cart for the given customer. */
    public Cart(Customer customer) {
        this.customer  = customer;
        this.cartItems = new ArrayList<>();
    }

    public Customer   getCustomer()  { return customer; }
    public List<OrderItem> getCartItems() { return cartItems; }
    public boolean    isEmpty()      { return cartItems.isEmpty(); }


public void addItem(Product product, int quantity) {
    if (product == null || quantity <= 0) {
        System.out.println("Invalid product or quantity.");
        return;
    }
    // If this product is already in the cart, merge the quantity
    for (OrderItem item : cartItems) {
        if (item.getProduct().getProductId().equals(product.getProductId())) {
            item.setQuantity(item.getQuantity() + quantity);
            System.out.println("Updated quantity for: " + product.getProductName());
            return;
        }
    }
    // New product — add a fresh line item
    cartItems.add(new OrderItem(product, quantity));
    System.out.println("Added to cart: " + product.getProductName() + " x" + quantity);
}

public void removeItem(String productId) {
    for (int i = 0; i < cartItems.size(); i++) {
        if (cartItems.get(i).getProduct().getProductId().equals(productId)) {
            System.out.println("Removed: " + cartItems.get(i).getProduct().getProductName());
            cartItems.remove(i);
            return;
        }
    }
    System.out.println("Product not found in cart: " + productId);
}

public void clearCart() {
    cartItems.clear();
    System.out.println("Checkout completed. Cart cleared.");
}

public double getRunningTotal() {
    double total = 0.0;
    for (OrderItem item : cartItems) total += item.getSubtotal();
    return total;
}

public Order checkout(String orderId, String orderDate) {
    if (isEmpty()) {
        System.out.println("Cannot checkout — cart is empty.");
        return null;
    }
    // Build an Order from the cart's contents
    Order order = new Order(orderId, customer, orderDate);
    for (OrderItem item : cartItems) {
        order.addItem(item);          // existing Order.addItem()
    }
    customer.placeOrder(order);       // existing Customer.placeOrder()
    order.displayOrderSummary();      // existing Order method
    clearCart();                      // reset for next session
    return order;
}

public void displayCart() {
    System.out.println("===== Your Cart =====");
    System.out.println("Customer: " + customer.getFullName());
    if (isEmpty()) {
        System.out.println("Your cart is empty.");
    } else {
        for (OrderItem item : cartItems) {
            System.out.printf("  - %dx %s  (RM %.2f)%n",
                item.getQuantity(),
                item.getProduct().getProductName(),
                item.getSubtotal());
        }
        System.out.printf("Running Total : RM %.2f%n", getRunningTotal());
    }
    System.out.println("=====================");
}
}