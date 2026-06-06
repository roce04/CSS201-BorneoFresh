package borneofresh.model;

/**
 * A discount strategy that deducts a fixed amount from a price.
 * For example, a discountAmount of 5.00 would reduce a price of
 * RM50.00 to RM45.00. Implements the DiscountStrategy interface.
 */
public class FlatDiscount implements DiscountStrategy {

    private double discountAmount;

    /**
     * Constructs a new FlatDiscount with the given fixed deduction amount.
     *
     * @param discountAmount the fixed amount to deduct from the price
     */
    public FlatDiscount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    /** Returns the fixed discount amount. */
    public double getDiscountAmount() { return discountAmount; }

    /**
     * Applies the flat discount by subtracting the discount amount from
     * the price. Returns 0.0 if the discount would result in a negative price.
     *
     * @param price the original price
     * @return the price after the flat discount, minimum of 0.0
     */
    @Override
    public double applyDiscount(double price) {
        return Math.max(0.0, price - discountAmount);
    }
}