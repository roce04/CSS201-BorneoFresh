package borneofresh.model;

/**
 * Represents a certified organic product in the BorneoFresh Market system,
 * such as organic snacks and health foods. Extends the Product abstract class
 * and adds a certification code field specific to organic items.
 */
public class OrganicProduct extends Product {

    private String certificationCode;

    /**
     * Constructs a new OrganicProduct with the given details.
     *
     * @param productId         the unique identifier for this product
     * @param productName       the display name of the product
     * @param price             the price of the product
     * @param isAvailable       whether the product is currently available
     * @param certificationCode the organic certification code for this product
     */
    public OrganicProduct(String productId, String productName, double price,
                          boolean isAvailable, String certificationCode) {
        super(productId, productName, price, isAvailable);
        this.certificationCode = certificationCode;
    }

    /** Returns the organic certification code of this product. */
    public String getCertificationCode() { return certificationCode; }

    /** Updates the certification code of this product. */
    public void setCertificationCode(String certificationCode) {
        this.certificationCode = certificationCode;
    }

    /**
     * Returns the category of this product.
     *
     * @return "Organic Product"
     */
    @Override
    public String getCategory() { return "Organic Product"; }

    /**
     * Displays all product information including the certification code.
     * Calls the parent displayInfo() first, then adds the certification code.
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Cert Code    : " + certificationCode);
        System.out.println("-----------------------------");
    }
}