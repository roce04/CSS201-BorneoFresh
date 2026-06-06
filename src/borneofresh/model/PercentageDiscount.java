package borneofresh.model;

/**
 * A discount strategy that reduces a price by a percentage.
 * For example, a discountRate of 0.10 represents a 10% discount,
 * which would reduce a price of RM50.00 to RM45.00.
 * Implements the DiscountStrategy interface.
 */
public class PercentageDiscount implements DiscountStrategy {

    private double discountRate;

    /**
     * Constructs a new PercentageDiscount with the given rate.
     *
     * @param discountRate the percentage to discount as a decimal,
     *                     e.g. 0.10 for 10%, 0.25 for 25%
     */
    public PercentageDiscount(double discountRate) {
        this.discountRate = discountRate;
    }

    /** Returns the discount rate. */
    public double getDiscountRate() { return discountRate; }

    /**
     * Applies the percentage discount to the given price.
     * Calculated as price multiplied by (1 minus the discount rate).
     *
     * @param price the original price
     * @return the price after the percentage discount
     */
    @Override
    public double applyDiscount(double price) {
        return price * (1 - discountRate);
    }
}