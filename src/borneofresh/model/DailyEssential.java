package borneofresh.model;

/**
 * Represents a daily essential product in the BorneoFresh Market system,
 * such as dairy products and household necessities. Extends the Product
 * abstract class and adds a unit field describing the measurement or
 * packaging unit of the product.
 */
public class DailyEssential extends Product {

    private String unit;

    /**
     * Constructs a new DailyEssential product with the given details.
     *
     * @param productId   the unique identifier for this product
     * @param productName the display name of the product
     * @param price       the price of the product
     * @param isAvailable whether the product is currently available
     * @param unit        the unit of measurement or packaging (e.g. "1L", "1kg")
     */
    public DailyEssential(String productId, String productName, double price,
                          boolean isAvailable, String unit) {
        super(productId, productName, price, isAvailable);
        this.unit = unit;
    }

    /** Returns the unit of measurement for this product. */
    public String getUnit() { return unit; }

    /** Updates the unit of measurement for this product. */
    public void setUnit(String unit) { this.unit = unit; }

    /**
     * Returns the category of this product.
     *
     * @return "Daily Essential"
     */
    @Override
    public String getCategory() { return "Daily Essential"; }

    /**
     * Displays all product information including the unit.
     * Calls the parent displayInfo() first, then adds the unit.
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Unit         : " + unit);
        System.out.println("-----------------------------");
    }
}