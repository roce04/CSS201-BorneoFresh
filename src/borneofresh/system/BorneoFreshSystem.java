package borneofresh.system;

import borneofresh.model.*;

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
    private PromotionCatalogue promotionCatalogue;
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
        this.promotionCatalogue = new PromotionCatalogue();
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
        promotionCatalogue.addPromotion(new Promotion("PR01", "10% Off All Orders", 
            new PercentageDiscount(0.10)));
        promotionCatalogue.addPromotion(new Promotion("PR02", "RM3 Off Your Order",
            new FlatDiscount(3.00)));

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
            System.out.println("3. Add Product");
            System.out.println("4. Update Product");
            System.out.println("5. View Available Products");
            System.out.println("6. View Unavailable Products");
            System.out.println("7. Add Promotion");
            System.out.println("8. View All Promotions");
            System.out.println("9. Logout");
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
                    addProductFlow(admin);
                    break;
                case "4":
                    updateProductFlow(admin);
                    break;
                case "5":
                    catalogue.getAvailableProducts().forEach(Product::displayInfo);
                    break;
                case "6":
                    catalogue.getUnavailableProducts().forEach(Product::displayInfo);
                    break;
                case "7":
                    addPromotionFlow();
                    break;
                case "8":
                    promotionCatalogue.displayAllPromotions();
                    break;
                case "9":
                    System.out.println("Logging out admin...");
                    adminRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays and handles the Customer dashboard.
     * Loops until the customer logs out.
     */
    private void showCustomerMenu(Customer customer) {
    // Create ONE cart for this entire login session.
    // This is what was missing — the Cart was never created.
    Cart cart = new Cart(customer);

    boolean running = true;
    while (running) {
        System.out.println("\n=== Customer Dashboard ===");
        System.out.println("Welcome back, " + customer.getFullName() + "!");
        System.out.println("1. Browse Products");
        System.out.println("2. Add Item to Cart");      // replaces old "Place an Order"
        System.out.println("3. View Cart");
        System.out.println("4. Checkout");
        System.out.println("5. View Order History");
        System.out.println("6. View My Account");
        System.out.println("7. Logout");
        System.out.print("Select an option: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                // Just browse — show everything available
                catalogue.displayAllProducts();
                break;

            case "2":
                // ADD TO CART — this is the key new flow
                catalogue.displayAllProducts();
                System.out.print("Enter the Product ID to add to cart: ");
                String pid = scanner.nextLine().trim();
                Product chosen = catalogue.getProductById(pid);

                if (chosen == null) {
                    System.out.println("Product not found.");
                } else if (!chosen.isAvailable()) {
                    System.out.println("Sorry, that product is currently unavailable.");
                } else {
                    System.out.print("Enter quantity: ");
                    try {
                        int qty = Integer.parseInt(scanner.nextLine().trim());
                        cart.addItem(chosen, qty);  // Cart handles duplicate merging
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid quantity entered.");
                    }
                }
                break;

            case "3":
                // VIEW CART — customer can see everything before committing
                cart.displayCart();
                System.out.println("\nWould you like to remove an item? (yes/no)");
                if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                    System.out.print("Enter Product ID to remove: ");
                    cart.removeItem(scanner.nextLine().trim());
                }
                break;

            case "4":
                // CHECKOUT — only now do we create an Order
                if (cart.isEmpty()) {
                    System.out.println("Your cart is empty. Add some items first!");
                } else {
                    cart.displayCart();

                    // Apply a promotion if any are active
                    promotionCatalogue.displayAllPromotions();
                    System.out.print("Enter promotion ID to apply (or press Enter to skip): ");
                    String promoId = scanner.nextLine().trim();
                    double total = cart.getRunningTotal();

                    if (!promoId.isEmpty()) {
                        Promotion promo = promotionCatalogue.getPromotionById(promoId);
                        if (promo != null && promo.isActive()) {
                            total = promo.applyPromotion(total);
                            System.out.printf("Promotion applied! New total: RM %.2f%n", total);
                        } else {
                            System.out.println("Promotion not found or inactive.");
                        }
                    }

                    System.out.print("Confirm order? (yes/no): ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                        // Generate a unique order ID using timestamp
                        String orderId = "ORD" + System.currentTimeMillis();
                        String date = java.time.LocalDate.now().toString();
                        cart.checkout(orderId, date); // Cart builds Order, calls customer.placeOrder()
                    } else {
                        System.out.println("Order cancelled.");
                    }
                }
                break;

            case "5":
                customer.viewOrderHistory();
                break;

            case "6":
                customer.displayInfo();
                break;

            case "7":
                System.out.println("Logging out...");
                running = false;
                break;

            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
}

    /**
 * Guides the customer through selecting a product and quantity,
 * optionally applying an active promotion, and recording the
 * completed order in the customer's order history.
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
    newOrder.displayOrderSummary();

    // Show active promotions and allow the customer to apply one
    java.util.List<Promotion> activePromos = promotionCatalogue.getActivePromotions();
    double finalTotal = newOrder.getTotalCost();

    if (!activePromos.isEmpty()) {
        System.out.println("\n===== Available Promotions =====");
        for (Promotion promo : activePromos) {
            promo.displayInfo();
        }
        System.out.print("Enter promotion ID to apply (or press Enter to skip): ");
        String promoId = scanner.nextLine().trim();
        if (!promoId.isEmpty()) {
            Promotion selected = promotionCatalogue.getPromotionById(promoId);
            if (selected != null && selected.isActive()) {
                finalTotal = selected.applyPromotion(newOrder.getTotalCost());
                System.out.printf("Promotion applied! New total: RM %.2f%n", finalTotal);
            } else {
                System.out.println("Promotion not found or inactive. No discount applied.");
            }
        }
    }

    System.out.print("Confirm order? (yes/no): ");
    String confirm = scanner.nextLine().trim();
    if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
        customer.placeOrder(newOrder);
        System.out.printf("Order confirmed! You paid: RM %.2f%n", finalTotal);
    } else {
        System.out.println("Order cancelled.");
    }
}

        /**
     * Handles the add product flow for administrators.
     * Prompts for product type and details, then creates the appropriate
     * Product subclass and adds it to the catalogue.
     */
    private void addProductFlow(Administrator admin) {
        System.out.println("\n===== Add New Product =====");
        System.out.println("Product type: 1. Fresh Produce  2. Organic Product  3. Daily Essential");
        System.out.print("Enter type: ");
        String type = scanner.nextLine().trim();

        System.out.print("Enter product ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter price (RM): ");
        double price;
        try {
            price = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Product not added.");
            return;
        }
        System.out.print("Is available? (true/false): ");
        boolean available = Boolean.parseBoolean(scanner.nextLine().trim());

        Product product = null;
        switch (type) {
            case "1":
                System.out.print("Enter expiry date: ");
                product = new FreshProduce(id, name, price, available,
                        scanner.nextLine().trim());
                break;
            case "2":
                System.out.print("Enter certification code: ");
                product = new OrganicProduct(id, name, price, available,
                        scanner.nextLine().trim());
                break;
            case "3":
                System.out.print("Enter unit (e.g. 1kg, 500ml): ");
                product = new DailyEssential(id, name, price, available,
                        scanner.nextLine().trim());
                break;
            default:
                System.out.println("Invalid product type.");
                return;
        }
        admin.addProduct(catalogue, product);
    }

    /**
     * Handles the update product flow for administrators.
     * Finds the product by ID and allows updating name, price,
     * and availability individually.
     */
    private void updateProductFlow(Administrator admin) {
        System.out.print("\nEnter the product ID to update: ");
        String productId = scanner.nextLine().trim();
        Product existing = catalogue.getProductById(productId);

        if (existing == null) {
            System.out.println("Product not found.");
            return;
        }

        existing.displayInfo();

        System.out.print("New name (press Enter to keep current): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) existing.setProductName(name);

        System.out.print("New price (press Enter to keep current): ");
        String priceInput = scanner.nextLine().trim();
        if (!priceInput.isEmpty()) {
            try {
                existing.setPrice(Double.parseDouble(priceInput));
            } catch (NumberFormatException e) {
                System.out.println("Invalid price, keeping current value.");
            }
        }

        System.out.print("Update availability? (true/false, or press Enter to keep): ");
        String availInput = scanner.nextLine().trim();
        if (!availInput.isEmpty()) {
            existing.setAvailable(Boolean.parseBoolean(availInput));
        }

        admin.updateProduct(catalogue, productId, existing);
    }

    /**
     * Handles the add promotion flow for administrators.
     * Prompts for promotion type and details, creates the appropriate
     * DiscountStrategy, and wraps it in a new Promotion object.
     */
        private void addPromotionFlow() {
        System.out.println("\n===== Add New Promotion =====");
        System.out.print("Enter promotion ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter promotion name: ");
        String name = scanner.nextLine().trim();
        System.out.println("Discount type: 1. Percentage  2. Flat Amount");
        System.out.print("Enter type: ");
        String type = scanner.nextLine().trim();

        DiscountStrategy strategy;
        if (type.equals("1")) {
            System.out.print("Enter percentage (e.g. 10 for 10%): ");
            try {
                double rate = Double.parseDouble(scanner.nextLine().trim()) / 100;
                strategy = new PercentageDiscount(rate);
            } catch (NumberFormatException e) {
                System.out.println("Invalid percentage. Promotion not added.");
                return;
            }
        } else if (type.equals("2")) {
            System.out.print("Enter flat discount amount (RM): ");
            try {
                double amount = Double.parseDouble(scanner.nextLine().trim());
                strategy = new FlatDiscount(amount);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Promotion not added.");
                return;
            }
        } else {
            System.out.println("Invalid type. Promotion not added.");
            return;
        }

        Promotion promo = new Promotion(id, name, strategy);
        promotionCatalogue.addPromotion(promo);
        System.out.println("Promotion added successfully: " + name);
    }
}