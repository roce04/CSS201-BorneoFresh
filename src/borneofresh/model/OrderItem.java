package borneofresh.model;

/**
 * Represents a single line item within an Order in the BorneoFresh Market system.
 * Stores a reference to a Product and the quantity ordered. An OrderItem cannot
 * exist independently — it is always part of an Order.
 */
public class OrderItem {

    private Product product;
    private int quantity;

    /**
     * Constructs a new OrderItem for the given product and quantity.
     *
     * @param product  the Product being ordered
     * @param quantity the number of units ordered
     */
    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /** Returns the product associated with this order item. */
    public Product getProduct() { return product; }

    /** Returns the quantity of this order item. */
    public int getQuantity() { return quantity; }

    /** Updates the quantity of this order item. */
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /**
     * Calculates and returns the subtotal for this line item.
     * Computed as the product's price multiplied by the quantity.
     *
     * @return the subtotal, or 0.0 if the product reference is null
     */
    public double getSubtotal() {
        if (product != null) {
            return product.getPrice() * quantity;
        }
        return 0.0;
    }
}