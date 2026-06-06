package borneofresh.system;

import borneofresh.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the full list of products available in the BorneoFresh system.
 * Works with the abstract Product type so it can hold FreshProduce,
 * OrganicProduct, and DailyEssential objects in a single list
 * (polymorphism via ArrayList<Product>).
 */
public class ProductCatalogue {

    // --- Attributes ---
    private List<Product> products = new ArrayList<>();

    // --- Methods ---
    public void addProduct(Product p) {
        products.add(p);
    }

    /**
     * Replaces the product whose ID matches productId with updatedProduct.
     * If no match is found, prints an informative message instead of
     * throwing silently.
     */
    public void updateProduct(String productId, Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                products.set(i, updatedProduct);
                System.out.println("Product updated successfully.");
                return;
            }
        }
        System.out.println("Product not found: " + productId);
    }

    /**
     * Returns the product with the given ID, or null if it does not exist.
     * Callers should null-check the return value before using it.
     */
    public Product getProductById(String productId) {
        for (Product p : products) {
            if (p.getProductId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    public List<Product> getAvailableProducts() {
        List<Product> available = new ArrayList<>();
        for (Product p : products) {
            if (p.isAvailable()) {
                available.add(p);
            }
        }
        return available;
    }

    public List<Product> getUnavailableProducts() {
        List<Product> unavailable = new ArrayList<>();
        for (Product p : products) {
            if (!p.isAvailable()) {
                unavailable.add(p);
            }
        }
        return unavailable;
    }

    /**
     * Case-insensitive category filter — so "fresh produce" and
     * "Fresh Produce" both work.
     */
    public List<Product> getProductsByCategory(String category) {
        List<Product> categorized = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                categorized.add(p);
            }
        }
        return categorized;
    }

    public void displayAllProducts() {
        System.out.println("=== Product Catalogue ===");
        if (products.isEmpty()) {
            System.out.println("Catalogue is currently empty.");
        } else {
            for (Product p : products) {
                p.displayInfo();
                System.out.println("-------------------------");
            }
        }
    }
}