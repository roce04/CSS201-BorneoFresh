package borneofresh.model;

/**
 * Abstract base class representing a product in the BorneoFresh Market system.
 * Defines common attributes and behaviours shared by all product types.
 * Cannot be instantiated directly — must be extended by a concrete subclass
 * such as FreshProduce, OrganicProduct, or DailyEssential.
 */
public abstract class Product {

    private String productId;
    private String productName;
    private double price;
    private boolean isAvailable;

    /**
     * Constructs a new Product with the given details.
     *
     * @param productId   the unique identifier for this product
     * @param productName the display name of the product
     * @param price       the price of the product
     * @param isAvailable whether the product is currently available
     */
    public Product(String productId, String productName, double price, boolean isAvailable) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    /** Returns the product's unique ID. */
    public String getProductId() { return productId; }

    /** Returns the product's name. */
    public String getProductName() { return productName; }

    /** Returns the product's price. */
    public double getPrice() { return price; }

    /** Returns true if the product is available, false otherwise. */
    public boolean isAvailable() { return isAvailable; }

    /** Updates the product's name. */
    public void setProductName(String productName) { this.productName = productName; }

    /** Updates the product's price. */
    public void setPrice(double price) { this.price = price; }

    /** Updates the product's availability status. */
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }

    /**
     * Returns the category of this product.
     * Overridden by each subclass to return its specific category string.
     *
     * @return a String representing the product category
     */
    public abstract String getCategory();

    /**
     * Displays the product's ID, name, category, price, and availability
     * to the console. Subclasses call super.displayInfo() and then add
     * their own specific fields.
     */
    public void displayInfo() {
        System.out.println("Product ID   : " + productId);
        System.out.println("Name         : " + productName);
        System.out.println("Category     : " + getCategory());
        System.out.println("Price        : RM " + String.format("%.2f", price));
        System.out.println("Available    : " + (isAvailable ? "Yes" : "No"));
    }
}