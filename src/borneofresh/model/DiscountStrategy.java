package borneofresh.model;

/**
 * Interface defining the contract for all discount strategies in the
 * BorneoFresh Market system. Any class that implements this interface
 * must provide its own implementation of applyDiscount(), allowing
 * different discount behaviours to be swapped at runtime without
 * changing the classes that use them. This is the Strategy design pattern.
 */
public interface DiscountStrategy {

    /**
     * Applies a discount to the given price and returns the discounted value.
     *
     * @param price the original price before discount
     * @return the price after the discount has been applied
     */
    double applyDiscount(double price);
}