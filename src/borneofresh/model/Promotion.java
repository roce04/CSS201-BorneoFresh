package borneofresh.model;

/**
 * Represents a promotional discount in the BorneoFresh Market system.
 * A Promotion holds a DiscountStrategy object that determines how the
 * discount is calculated. By delegating to the strategy, Promotion itself
 * does not need to know whether the discount is percentage-based or flat —
 * it simply calls applyDiscount() and lets the strategy handle the maths.
 * This demonstrates polymorphism through the Strategy design pattern.
 */
public class Promotion {

    private String promotionId;
    private String promotionName;
    private DiscountStrategy strategy;
    private boolean isActive;

    /**
     * Constructs a new Promotion. All new promotions are active by default.
     *
     * @param promotionId   the unique identifier for this promotion
     * @param promotionName the display name of this promotion
     * @param strategy      the discount strategy to use when applying the promotion
     */
    public Promotion(String promotionId, String promotionName, DiscountStrategy strategy) {
        this.promotionId = promotionId;
        this.promotionName = promotionName;
        this.strategy = strategy;
        this.isActive = true;
    }

    /** Returns the promotion's unique ID. */
    public String getPromotionId() { return promotionId; }

    /** Returns the promotion's display name. */
    public String getPromotionName() { return promotionName; }

    /** Returns true if this promotion is currently active. */
    public boolean isActive() { return isActive; }

    /** Activates or deactivates this promotion. */
    public void setActive(boolean isActive) { this.isActive = isActive; }

    /**
     * Applies this promotion to the given price if the promotion is active.
     * Delegates to the assigned DiscountStrategy to perform the calculation.
     * If the promotion is inactive, the original price is returned unchanged.
     *
     * @param price the original price to apply the promotion to
     * @return the discounted price if active, or the original price if inactive
     */
    public double applyPromotion(double price) {
        if (isActive) {
            return strategy.applyDiscount(price);
        }
        return price;
    }

    /**
     * Displays the promotion's details to the console.
     */
    public void displayInfo() {
        System.out.println("Promotion ID   : " + promotionId);
        System.out.println("Name           : " + promotionName);
        System.out.println("Status         : " + (isActive ? "Active" : "Inactive"));
    }
}