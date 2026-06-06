package borneofresh.model;

import borneofresh.system.ProductCatalogue;

/**
 * Represents an administrator of the BorneoFresh Market system.
 * Extends User and provides functionality for managing the product catalogue,
 * including adding, updating, and viewing products.
 */
public class Administrator extends User {

    private String adminId;

    /**
     * Constructs a new Administrator with the given ID, username, and password.
     *
     * @param adminId  the unique identifier for this administrator
     * @param username the login identifier
     * @param password the login credential
     */
    public Administrator(String adminId, String username, String password) {
        super(username, password);
        this.adminId = adminId;
    }

    /** Returns the administrator's unique ID. */
    public String getAdminId() { return adminId; }

    /**
     * Returns the role of this user.
     *
     * @return "Administrator"
     */
    @Override
    public String getRole() { return "Administrator"; }

    /**
     * Displays the administrator's ID and username to the console.
     */
    @Override
    public void displayInfo() {
        System.out.println("===== Administrator Info =====");
        System.out.println("Admin ID : " + adminId);
        System.out.println("Username : " + getUsername());
    }

    /**
     * Adds a new product to the given ProductCatalogue.
     *
     * @param catalogue the ProductCatalogue to add the product to
     * @param p         the Product to add
     */
    public void addProduct(ProductCatalogue catalogue, Product p) {
        if (catalogue != null && p != null) {
            catalogue.addProduct(p);
            System.out.println("Product added successfully: " + p.getProductName());
        }
    }

    /**
     * Updates an existing product in the given ProductCatalogue.
     *
     * @param catalogue      the ProductCatalogue containing the product
     * @param productId      the ID of the product to update
     * @param updatedProduct the replacement Product object
     */
    public void updateProduct(ProductCatalogue catalogue, String productId,
                              Product updatedProduct) {
        if (catalogue != null) {
            catalogue.updateProduct(productId, updatedProduct);
            System.out.println("Product updated successfully: " + productId);
        }
    }

    /**
     * Displays all products in the given ProductCatalogue.
     *
     * @param catalogue the ProductCatalogue to retrieve products from
     */
    public void viewProducts(ProductCatalogue catalogue) {
        if (catalogue != null) {
            catalogue.displayAllProducts();
        }
    }
}