package borneofresh.test;

import borneofresh.model.FreshProduce;
import borneofresh.model.OrganicProduct;
import borneofresh.model.Product;
import borneofresh.system.ProductCatalogue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Unit tests for the ProductCatalogue class.
 * Verifies that products can be added, retrieved, and filtered correctly.
 */
public class ProductCatalogueTest {

    // We declare the catalogue here so every test method can use it.
    private ProductCatalogue catalogue;

    /**
     * @BeforeEach means this method runs automatically before every single
     * test method below. It gives each test a fresh, clean catalogue to
     * work with so tests do not interfere with each other.
     */
    @BeforeEach
    public void setUp() {
        catalogue = new ProductCatalogue();
        catalogue.addProduct(new FreshProduce("P01", "Apple", 2.50, true, "2026-12-31"));
        catalogue.addProduct(new FreshProduce("P02", "Spinach", 1.80, false, "2026-07-15"));
        catalogue.addProduct(new OrganicProduct("P03", "Organic Oats", 5.90, true, "ORG-001"));
    }

    /**
     * Test 1: Verifies that a product added to the catalogue can be
     * retrieved by its ID. If getProductById returns null, the product
     * was not stored correctly.
     */
    @Test
    public void testGetProductById() {
        Product result = catalogue.getProductById("P01");

        // assertNotNull checks that result is not null — i.e. the product was found.
        assertNotNull(result, "Product P01 should be found in the catalogue.");

        // assertEquals checks that the retrieved product has the correct name.
        assertEquals("Apple", result.getProductName(),
                "Product retrieved by ID P01 should be named Apple.");
    }

    /**
     * Test 2: Verifies that getAvailableProducts returns only products
     * where isAvailable is true. We added two available products (P01, P03)
     * and one unavailable (P02), so the result should contain exactly 2 items.
     */
    @Test
    public void testGetAvailableProducts() {
        List<Product> available = catalogue.getAvailableProducts();

        // assertEquals checks that exactly 2 products are returned.
        assertEquals(2, available.size(),
                "There should be exactly 2 available products.");

        // assertTrue checks that the unavailable product (Spinach) is NOT in the list.
        assertTrue(available.stream().noneMatch(p -> p.getProductId().equals("P02")),
                "Unavailable product P02 (Spinach) should not appear in available products.");
    }

    /**
     * Test 3: Verifies that getProductsByCategory returns only products
     * matching the given category string. We added two FreshProduce items
     * (P01 and P02), so filtering by "Fresh Produce" should return exactly 2.
     */
    @Test
    public void testGetProductsByCategory() {
        List<Product> freshProducts = catalogue.getProductsByCategory("Fresh Produce");

        assertEquals(2, freshProducts.size(),
                "There should be exactly 2 Fresh Produce products.");

        // Also verify that the organic product is NOT included in the fresh produce list.
        assertTrue(freshProducts.stream().noneMatch(p -> p.getProductId().equals("P03")),
                "Organic product P03 should not appear in Fresh Produce results.");
    }
}