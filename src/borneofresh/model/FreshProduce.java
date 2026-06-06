package borneofresh.model;

/**
 * Represents a fresh produce product in the BorneoFresh Market system,
 * such as fruits and vegetables. Extends the Product abstract class
 * and adds an expiry date field specific to perishable items.
 */
public class FreshProduce extends Product {

    private String expiryDate;

    /**
     * Constructs a new FreshProduce product with the given details.
     *
     * @param productId   the unique identifier for this product
     * @param productName the display name of the product
     * @param price       the price of the product
     * @param isAvailable whether the product is currently available
     * @param expiryDate  the expiry or best-before date of the product
     */
    public FreshProduce(String productId, String productName, double price,
                        boolean isAvailable, String expiryDate) {
        super(productId, productName, price, isAvailable);
        this.expiryDate = expiryDate;
    }

    /** Returns the expiry date of this product. */
    public String getExpiryDate() { return expiryDate; }

    /** Updates the expiry date of this product. */
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    /**
     * Returns the category of this product.
     *
     * @return "Fresh Produce"
     */
    @Override
    public String getCategory() { return "Fresh Produce"; }

    /**
     * Displays all product information including the expiry date.
     * Calls the parent displayInfo() first, then adds the expiry date.
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Expiry Date  : " + expiryDate);
        System.out.println("-----------------------------");
    }
}