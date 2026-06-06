package borneofresh.system;

import borneofresh.model.*;
import borneofresh.model.Administrator;
import borneofresh.model.Customer;
import borneofresh.model.DailyEssential;
import borneofresh.model.FreshProduce;
import borneofresh.model.Order;
import borneofresh.model.OrderItem;
import borneofresh.model.OrganicProduct;
import borneofresh.model.Product;
import borneofresh.model.User;

import java.util.Scanner;

/**
 * Main entry point and controller of the BorneoFresh Market application.
 *
 * Responsibilities:
 *   - Owns the single ProductCatalogue and CustomerRegistry instances.
 *   - Drives all console menus (main, admin, customer).
 *   - Delegates business logic to the appropriate model/system classes
 *     rather than implementing it inline.
 *
 * A single Scanner on System.in is created here and reused throughout.
 * Opening multiple Scanners on the same stream causes problems because
 * closing one closes the underlying stream for all of them.
 */
public class BorneoFreshSystem {

    // --- Attributes ---
    private ProductCatalogue catalogue;
    private CustomerRegistry registry;
    private Scanner scanner;

    // Hardcoded admin credentials — in a real system these would come
    // from a secure configuration source, not be embedded in source code.
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String ADMIN_ID       = "A001";

    // --- Constructor ---
    public BorneoFreshSystem() {
        this.catalogue = new ProductCatalogue();
        this.registry  = new CustomerRegistry();
        this.scanner   = new Scanner(System.in);
        seedData();
    }

    // --- Entry point ---
    public static void main(String[] args) {
        BorneoFreshSystem system = new BorneoFreshSystem();
        system.showMainMenu();
    }

    // --- Seed data ---
    /**
     * Populates the system with a small set of demo products and one demo
     * customer so the application is usable immediately on first run.
     * Customer constructor signature: (username, password, customerId, fullName, email)
     */
    private void seedData() {
        catalogue.addProduct(new FreshProduce("P01", "Apple",           2.50, true,  "2025-12-31"));
        catalogue.addProduct(new FreshProduce("P02", "Banana",          1.80, true,  "2025-11-15"));
        catalogue.addProduct(new OrganicProduct("P03", "Organic Spinach", 4.00, true, "ORG-9921"));
        catalogue.addProduct(new DailyEssential("P04", "Milk",           3.20, true,  "1 Liter"));
        catalogue.addProduct(new DailyEssential("P05", "Eggs",           5.50, true,  "12 pieces"));

        // Demo customer — password is "pass123"
        registry.registerCustomer(
            new Customer("johndoe", "pass123", "C001", "John Doe", "john@email.com")
        );
    }

    // --- Menus ---

    /**
     * Top-level menu. Keeps running until the user chooses to exit.
     * After a successful login, control is delegated to either
     * showAdminMenu() or showCustomerMenu() before returning here.
     */
    public void showMainMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Welcome to Borneo Fresh Market ===");
            System.out.println("1. Login");
            System.out.println("2. Register as new Customer");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    User loggedInUser = handleLogin();
                    if (loggedInUser instanceof Administrator) {
                        showAdminMenu((Administrator) loggedInUser);
                    } else if (loggedInUser instanceof Customer) {
                        showCustomerMenu((Customer) loggedInUser);
                    } else {
                        System.out.println("Login failed. Incorrect username or password.");
                    }
                    break;

                case "2":
                    handleRegistration();
                    break;

                case "3":
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }

        // Close the scanner only when the application is truly finished
        // to avoid closing System.in prematurely.
        scanner.close();
    }

    /**
     * Prompts the user for credentials and returns the matching User.
     *
     * Admin login is handled separately from customer login because
     * admins are not stored in the CustomerRegistry. If the entered
     * username matches the hardcoded admin username AND the password
     * matches, a new Administrator instance is returned. Otherwise
     * the registry is queried, which also validates the password.
     * Returns null if authentication fails.
     */
    public User handleLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        // Check for admin first
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            return new Administrator(ADMIN_USERNAME, ADMIN_PASSWORD, ADMIN_ID);
        }

        // Delegate customer credential check to the registry
        return registry.login(username, password);
    }

    /**
     * Collects registration details, validates the username is not taken,
     * then creates and registers the new Customer.
     */
    private void handleRegistration() {
        System.out.println("\n--- Customer Registration ---");

        System.out.print("Choose a username: ");
        String username = scanner.nextLine().trim();

        if (registry.isUsernameTaken(username)) {
            System.out.println("That username is already taken. Please try a different one.");
            return;
        }

        System.out.print("Choose a password: ");
        String password = scanner.nextLine().trim();
        System.out.print("Customer ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Full Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        Customer newCustomer = new Customer(username, password, id, name, email);
        registry.registerCustomer(newCustomer);
    }

    /**
     * Displays and handles the Administrator dashboard.
     * Loops until the admin logs out.
     */
    public void showAdminMenu(Administrator admin) {
        boolean adminRunning = true;

        while (adminRunning) {
            System.out.println("\n=== Administrator Dashboard ===");
            System.out.println("Logged in as Admin: " + admin.getAdminId());
            System.out.println("1. View All Products");
            System.out.println("2. View Registered Customers");
            System.out.println("3. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    admin.viewProducts(catalogue);
                    break;
                case "2":
                    registry.displayAllCustomers();
                    break;
                case "3":
                    System.out.println("Logging out admin...");
                    adminRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }

    /**
     * Displays and handles the Customer dashboard.
     * Loops until the customer logs out.
     */
    public void showCustomerMenu(Customer customer) {
        boolean customerRunning = true;

        while (customerRunning) {
            System.out.println("\n=== Customer Dashboard ===");
            System.out.println("Welcome back, " + customer.getFullName() + "!");
            System.out.println("1. Browse Products");
            System.out.println("2. Place an Order");
            System.out.println("3. View Order History");
            System.out.println("4. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    catalogue.displayAllProducts();
                    break;
                case "2":
                    placeOrder(customer);
                    break;
                case "3":
                    customer.viewOrderHistory();
                    break;
                case "4":
                    System.out.println("Logging out...");
                    customerRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter 1, 2, 3, or 4.");
            }
        }
    }

    /**
     * Guides the customer through selecting a product and quantity,
     * creates an Order with one OrderItem, and records it in the
     * customer's order history.
     *
     * Order ID is generated from the last four digits of the current
     * timestamp — sufficient for a demo; a UUID would be better in
     * a production system.
     */
    private void placeOrder(Customer customer) {
        System.out.println("\n--- Place an Order ---");
        catalogue.displayAllProducts();

        System.out.print("Enter the Product ID you want to buy: ");
        String productId = scanner.nextLine().trim();

        Product p = catalogue.getProductById(productId);
        if (p == null) {
            System.out.println("Product not found. Please check the ID and try again.");
            return;
        }

        if (!p.isAvailable()) {
            System.out.println("Sorry, that product is currently unavailable.");
            return;
        }

        System.out.print("Enter quantity: ");
        int qty;
        try {
            qty = Integer.parseInt(scanner.nextLine().trim());
            if (qty <= 0) {
                System.out.println("Quantity must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity. Please enter a whole number.");
            return;
        }

        String orderId = "ORD" + (System.currentTimeMillis() % 10000);
        Order newOrder = new Order(orderId, customer, java.time.LocalDate.now().toString());
        newOrder.addItem(new OrderItem(p, qty));

        customer.placeOrder(newOrder);
        newOrder.displayOrderSummary();
    }
}