import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<MenuItem> menu = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();

    static int orderCounter = 1;

    public static void main(String[] args) {

        // Default Admin
        users.add(new User("admin", "admin123", "admin"));

        // Menu Items
        menu.add(new MenuItem(1, "Chocolate Donut", 35, 20));
        menu.add(new MenuItem(2, "Strawberry Donut", 40, 15));
        menu.add(new MenuItem(3, "Vanilla Donut", 30, 10));
        menu.add(new MenuItem(4, "Coffee", 50, 25));

        while (true) {

            System.out.println("\n===== RESTAURANT ORDERING SYSTEM =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    register();
                    break;

                case 2:
                    login();
                    break;

                case 3:
                    System.out.println("Thank you for using the system!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void register() {

        System.out.println("\n===== REGISTER =====");

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        users.add(new User(username, password, "customer"));

        System.out.println("Registration successful!");
    }

    public static void login() {

        System.out.println("\n===== LOGIN =====");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        for (User user : users) {

            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {

                System.out.println("Login successful!");

                if (user.getRole().equals("admin")) {
                    adminMenu();
                } else {
                    customerMenu();
                }

                return;
            }
        }

        System.out.println("Invalid username or password.");
    }

    public static void customerMenu() {

        ArrayList<CartItem> cart = new ArrayList<>();

        while (true) {

            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. View Menu");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    displayMenu();
                    break;

                case 2:
                    addToCart(cart);
                    break;

                case 3:
                    viewCart(cart);
                    break;

                case 4:
                    checkout(cart);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void displayMenu() {

        System.out.println("\n===== MENU =====");

        for (MenuItem item : menu) {

            System.out.println(
                    item.getId() + ". "
                    + item.getName()
                    + " - PHP " + item.getPrice()
                    + " | Stock: " + item.getStock());
        }
    }

    public static void addToCart(ArrayList<CartItem> cart) {

        displayMenu();

        System.out.print("Enter item ID: ");
        int id = scanner.nextInt();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();

        for (MenuItem item : menu) {

            if (item.getId() == id) {

                if (quantity > item.getStock()) {

                    System.out.println("Not enough stock.");
                    return;
                }

                cart.add(new CartItem(item, quantity));

                item.setStock(item.getStock() - quantity);

                System.out.println("Item added to cart.");
                return;
            }
        }

        System.out.println("Item not found.");
    }

    public static void viewCart(ArrayList<CartItem> cart) {

        System.out.println("\n===== CART =====");

        double total = 0;

        for (CartItem cartItem : cart) {

            System.out.println(
                    cartItem.getItem().getName()
                    + " x "
                    + cartItem.getQuantity()
                    + " = PHP "
                    + cartItem.getSubtotal());

            total += cartItem.getSubtotal();
        }

        System.out.println("Total: PHP " + total);
    }

    public static void checkout(ArrayList<CartItem> cart) {

        if (cart.isEmpty()) {

            System.out.println("Cart is empty.");
            return;
        }

        Order order = new Order(orderCounter, new ArrayList<>(cart));

        orders.add(order);

        System.out.println("\n===== ORDER RECEIPT =====");
        System.out.println("Order ID: " + order.getOrderId());

        for (CartItem item : cart) {

            System.out.println(
                    item.getItem().getName()
                    + " x "
                    + item.getQuantity());
        }

        System.out.println("Total: PHP " + order.getTotal());
        System.out.println("Order Status: " + order.getStatus());
        System.out.println("Checkout successful!");

        orderCounter++;
        cart.clear();
    }

    public static void adminMenu() {

        while (true) {

            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. View Inventory");
            System.out.println("2. Add Product");
            System.out.println("3. View Orders");
            System.out.println("4. Update Order Status");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    displayMenu();
                    break;

                case 2:
                    addProduct();
                    break;

                case 3:
                    viewOrders();
                    break;

                case 4:
                    updateOrderStatus();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void addProduct() {

        System.out.print("Enter product ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter stock: ");
        int stock = scanner.nextInt();

        menu.add(new MenuItem(id, name, price, stock));

        System.out.println("Product added successfully.");
    }

    public static void viewOrders() {

        System.out.println("\n===== CUSTOMER ORDERS =====");

        for (Order order : orders) {

            System.out.println(
                    "Order ID: "
                    + order.getOrderId()
                    + " | Total: PHP "
                    + order.getTotal()
                    + " | Status: "
                    + order.getStatus());
        }
    }

    public static void updateOrderStatus() {

        System.out.print("Enter order ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Order order : orders) {

            if (order.getOrderId() == id) {

                System.out.println("1. Pending");
                System.out.println("2. Preparing");
                System.out.println("3. Completed");

                System.out.print("Choose status: ");
                int statusChoice = scanner.nextInt();

                switch (statusChoice) {

                    case 1:
                        order.setStatus("Pending");
                        break;

                    case 2:
                        order.setStatus("Preparing");
                        break;

                    case 3:
                        order.setStatus("Completed");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                        return;
                }

                System.out.println("Order updated successfully.");
                return;
            }
        }

        System.out.println("Order not found.");
    }
}