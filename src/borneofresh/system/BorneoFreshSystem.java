package borneofresh.system;

import borneofresh.model.Administrator;
import borneofresh.model.Customer;
import borneofresh.model.Product;
import borneofresh.model.User;
import java.util.Scanner;

/**
 * The main entry point and controller of the BorneoFresh Market system.
 * This class manages the top-level menu flow, handles user authentication,
 * and delegates actions to the appropriate modules based on the authenticated
 * user's role.
 */
public class BorneoFreshSystem {

    private ProductCatalogue catalogue;
    private CustomerRegistry registry;
    private Scanner scanner;

    /**
     * Constructs a new BorneoFreshSystem, initialising the product catalogue,
     * customer registry, and the shared scanner for console input.
     */
    public BorneoFreshSystem() {
        this.catalogue = new ProductCatalogue();
        this.registry = new CustomerRegistry();
        this.scanner = new Scanner(System.in);
    }

    /**
     * The application entry point. Creates a BorneoFreshSystem instance
     * and launches the main menu.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        BorneoFreshSystem system = new BorneoFreshSystem();
        system.showMainMenu();
    }

    /**
     * Displays the top-level menu and continues running until the user
     * enters 'E' to exit. Provides options to log in or register as
     * a new customer.
     */
    public void showMainMenu() {
        String choice;
        do {
            System.out.println("\n===== Welcome to BorneoFresh Market =====");
            System.out.println("1. Login");
            System.out.println("2. Register as Customer");
            System.out.println("E. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "1" -> handleLogin();
                case "2" -> registry.registerCustomer(scanner);
                case "E" -> System.out.println("Thank you for using BorneoFresh Market. Goodbye!");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (!choice.equals("E"));

        scanner.close();
    }

    /**
     * Prompts the user for a username and password, validates the credentials
     * against the predefined administrator account and the customer registry,
     * and redirects the authenticated user to the appropriate menu.
     *
     * @return the authenticated User object, or null if authentication fails
     */
    public User handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        if (username.equals("admin") && password.equals("admin123")) {
            Administrator admin = new Administrator("A001", username, password);
            System.out.println("Welcome, Administrator!");
            showAdminMenu(admin);
            return admin;
        }

        Customer customer = registry.getCustomerByUsername(username);
        if (customer != null && customer.getPassword().equals(password)) {
            System.out.println("Welcome, " + customer.getFullName() + "!");
            showCustomerMenu(customer);
            return customer;
        }

        System.out.println("Invalid username or password. Please try again.");
        return null;
    }

    /**
     * Displays the Administrator Module menu and handles all administrator
     * actions, including adding, updating, and viewing products.
     *
     * @param admin the authenticated Administrator
     */
    public void showAdminMenu(Administrator admin) {
        String choice;
        do {
            System.out.println("\n===== Administrator Menu =====");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. View Available Products");
            System.out.println("4. View Unavailable Products");
            System.out.println("5. View All Products");
            System.out.println("E. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "1" -> addProductFlow(admin);
                case "2" -> updateProductFlow(admin);
                case "3" -> catalogue.getAvailableProducts()
                                     .forEach(Product::displayInfo);
                case "4" -> catalogue.getUnavailableProducts()
                                     .forEach(Product::displayInfo);
                case "5" -> admin.viewProducts(catalogue);
                case "E" -> System.out.println("Logging out...");
                default  -> System.out.println("Invalid option. Please try again.");
            }
        } while (!choice.equals("E"));
    }

    /**
     * Displays the Customer Module menu and handles all customer actions,
     * including product browsing, order placement, order history viewing,
     * and account management.
     *
     * @param customer the authenticated Customer
     */
    public void showCustomerMenu(Customer customer) {
        String choice;
        do {
            System.out.println("\n===== Customer Menu =====");
            System.out.println("1. View All Available Products");
            System.out.println("2. Browse Products by Category");
            System.out.println("3. Place an Order");
            System.out.println("4. View Order History");
            System.out.println("5. View My Account");
            System.out.println("6. Update My Account");
            System.out.println("E. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "1" -> catalogue.getAvailableProducts()
                                     .forEach(Product::displayInfo);
                case "2" -> browseByCategory();
                case "3" -> customer.placeOrder(catalogue, scanner);
                case "4" -> customer.viewOrderHistory();
                case "5" -> customer.displayInfo();
                case "6" -> customer.updateAccount(scanner);
                case "E" -> System.out.println("Logging out...");
                default  -> System.out.println("Invalid option. Please try again.");
            }
        } while (!choice.equals("E"));
    }

    /**
     * Prompts the administrator to enter product details and adds the
     * new product to the catalogue.
     *
     * @param admin the authenticated Administrator
     */
    private void addProductFlow(Administrator admin) {
        System.out.print("Enter product type (1: Fresh Produce, " +
                         "2: Organic Product, 3: Daily Essential): ");
        String type = scanner.nextLine().trim();
        System.out.print("Enter product ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Is available? (true/false): ");
        boolean available = Boolean.parseBoolean(scanner.nextLine().trim());

        Product product = switch (type) {
            case "1" -> {
                System.out.print("Enter expiry date: ");
                String expiry = scanner.nextLine().trim();
                yield new borneofresh.model.FreshProduce(
                        id, name, price, available, expiry);
            }
            case "2" -> {
                System.out.print("Enter certification code: ");
                String code = scanner.nextLine().trim();
                yield new borneofresh.model.OrganicProduct(
                        id, name, price, available, code);
            }
            case "3" -> {
                System.out.print("Enter unit (e.g. 500ml, 1kg): ");
                String unit = scanner.nextLine().trim();
                yield new borneofresh.model.DailyEssential(
                        id, name, price, available, unit);
            }
            default -> null;
        };

        if (product != null) {
            admin.addProduct(catalogue, product);
        } else {
            System.out.println("Invalid product type.");
        }
    }

    /**
     * Prompts the administrator to enter the ID of a product to update
     * and collects the updated details.
     *
     * @param admin the authenticated Administrator
     */
    private void updateProductFlow(Administrator admin) {
        System.out.print("Enter the product ID to update: ");
        String productId = scanner.nextLine().trim();
        Product existing = catalogue.getProductById(productId);

        if (existing == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.print("Enter new name (or press Enter to keep current): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) existing.setProductName(name);

        System.out.print("Enter new price (or 0 to keep current): ");
        String priceInput = scanner.nextLine().trim();
        if (!priceInput.equals("0")) existing.setPrice(
                Double.parseDouble(priceInput));

        System.out.print("Update availability? (true/false, or press " +
                         "Enter to keep current): ");
        String availInput = scanner.nextLine().trim();
        if (!availInput.isEmpty()) existing.setAvailable(
                Boolean.parseBoolean(availInput));

        admin.updateProduct(catalogue, productId, existing);
    }

    /**
     * Prompts the customer to enter a category and displays all matching
     * available products.
     */
    private void browseByCategory() {
        System.out.println("Categories: Fresh Produce, " +
                           "Organic Product, Daily Essential");
        System.out.print("Enter category: ");
        String category = scanner.nextLine().trim();
        catalogue.getProductsByCategory(category).forEach(Product::displayInfo);
    }
}