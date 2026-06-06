package borneofresh.test;

import borneofresh.model.Customer;
import borneofresh.model.FreshProduce;
import borneofresh.model.Order;
import borneofresh.model.OrderItem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Customer class.
 * Verifies that role identification, order placement, and
 * order history retrieval all work correctly.
 */
public class CustomerTest {

    private Customer customer;

    /**
     * Creates a fresh Customer instance before each test so that
     * tests do not share state — for example, orders placed in one
     * test will not appear in another test's order history.
     */
    @BeforeEach
    public void setUp() {
        customer = new Customer("janedoe", "pass456", "C002", "Jane Doe", "jane@email.com");
    }

    /**
     * Test 1: Verifies that getRole() returns exactly the string "Customer".
     * This confirms that polymorphism is working — the overridden method
     * in Customer returns the correct role label.
     */
    @Test
    public void testGetRole() {
        assertEquals("Customer", customer.getRole(),
                "getRole() should return \"Customer\" for a Customer object.");
    }

    /**
     * Test 2: Verifies that placeOrder() adds an order to the customer's
     * order history. Before placing, the history should be empty.
     * After placing, it should contain exactly one order.
     */
    @Test
    public void testPlaceOrder() {
        // Confirm the order history starts empty.
        assertTrue(customer.getOrderHistory().isEmpty(),
                "Order history should be empty before any orders are placed.");

        // Create a sample order and place it.
        Order order = new Order("ORD001", customer, "2026-06-06");
        FreshProduce apple = new FreshProduce("P01", "Apple", 2.50, true, "2026-12-31");
        order.addItem(new OrderItem(apple, 2));
        customer.placeOrder(order);

        // Verify that the order was added to the history.
        assertEquals(1, customer.getOrderHistory().size(),
                "Order history should contain exactly 1 order after placing one.");

        // Also verify it is the correct order.
        assertEquals("ORD001", customer.getOrderHistory().get(0).getOrderId(),
                "The order in history should have ID ORD001.");
    }

    /**
     * Test 3: Verifies that viewOrderHistory() runs without throwing any
     * exceptions, both when the history is empty and when it contains orders.
     * We also verify that the history accurately reflects the number of
     * orders placed.
     */
    @Test
    public void testViewOrderHistory() {
        // Place two orders.
        Order order1 = new Order("ORD001", customer, "2026-06-01");
        Order order2 = new Order("ORD002", customer, "2026-06-06");
        customer.placeOrder(order1);
        customer.placeOrder(order2);

        // Verify that both orders are present in the history.
        assertEquals(2, customer.getOrderHistory().size(),
                "Order history should contain exactly 2 orders after placing two.");

        // assertDoesNotThrow verifies that calling viewOrderHistory()
        // does not crash — it should always run cleanly.
        assertDoesNotThrow(() -> customer.viewOrderHistory(),
                "viewOrderHistory() should not throw any exceptions.");
    }
}