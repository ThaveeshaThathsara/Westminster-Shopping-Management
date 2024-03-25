import java.io.*;
import java.util.*;

public abstract class WestminsterShoppingManager implements ShoppingManager {
    private List<Product> products;
    private Scanner scanner;

    public WestminsterShoppingManager() {
        products = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);

        System.out.println("Product added successfully.");
    }

    @Override
    public void removeProduct(String productId) {
        boolean removed = products.removeIf(product -> product.getProductId().equals(productId));
        if (removed) {
            saveProductsToFile();  // Save updated products to file after removal
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    @Override
    public Product getProduct(String productId) {
        return products.stream()
                .filter(product -> product.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    private void addProductInteractively() {

        try {

            System.out.print("Enter Product Type (Electronics/Clothing): ");
            String productType = scanner.next().toLowerCase();

            System.out.print("Enter Product ID: ");
            String productId = scanner.next();
            System.out.print("Enter Product Name: ");
            scanner.nextLine();  // Consume newline left-over
            String productName = scanner.nextLine();
            System.out.print("Enter Available Items: ");
            int availableItems = scanner.nextInt();
            System.out.print("Enter Price: ");
            double price = scanner.nextDouble();

            Product product = null;
            if (productType.equals("electronics")) {
                System.out.print("Enter Brand: ");
                String brand = scanner.next();
                System.out.print("Enter Warranty Period: ");
                int warrantyPeriod = scanner.nextInt();
                product = new Electronics(productId, productType, productName, availableItems, price, brand, warrantyPeriod);
            } else if (productType.equals("clothing")) {
                System.out.print("Enter Size: ");
                int size = scanner.nextInt();
                System.out.print("Enter Color: ");
                String color = scanner.next();
                product = new Clothing(productId, productType, productName, availableItems, price, size, color);
            }

            if (product != null) {
                addProduct(product);
            } else {
                System.out.println("Invalid product type entered.");
            }
        }catch (InputMismatchException e){
            System.out.println("Invalid input. Please enter valid input");
            scanner.nextLine();
        }catch (NoSuchElementException e){
            System.out.println("Input not found. Please provide valid input.");
            scanner.nextLine();
        }catch (Exception e){
            System.out.println("An unexpected error occurred:"+e.getMessage());
            e.printStackTrace();
            scanner.nextLine();
        }

    }

    private void deleteProductInteractively() {
        try {
            System.out.print("Enter Product ID to delete: ");
            String productId = scanner.next();

            Product product = getProduct(productId);
            if (product != null) {
                removeProduct(productId);
                System.out.println("Product removed successfully.");
            } else {
                System.out.println("Product not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid input");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            scanner.nextLine();
        }
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println("\n--- Westminster Shopping Manager Menu ---");
                System.out.println("1. Add Product");
                System.out.println("2. Delete Product");
                System.out.println("3. Display All Products");
                System.out.println("4. Save Products");
                System.out.println("5. Load Products");
                System.out.println("6. Open GUI");
                System.out.println("7. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();

                // Validate the choice to prevent infinite loop
                if (choice < 1 || choice > 7) {
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        addProductInteractively();
                        break;
                    case 2:
                        deleteProductInteractively();
                        break;
                    case 3:
                        printAllProducts();
                        break;
                    case 4:
                        saveProductsToFile();
                        break;
                    case 5:
                        readProducts();
                        break;
                    case 6:
                        System.out.println("Open GUI");
                        openShoppingFirstGUI();
                        break;
                    case 7:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();  // Consume the invalid input
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
                scanner.nextLine();  // Consume any leftover input
            }
        }
    }


    private void printAllProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            Collections.sort(products, Comparator.comparing(Product::getProductName));

            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    private void saveProductsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))) {
            for (Product product : products) {
                if (product instanceof Electronics) {
                    writer.println(electronicsToString(product));
                } else if (product instanceof Clothing) {
                    writer.println(clothingToString(product));
                }
            }
            System.out.println("Products saved to 'products.txt'.");
        } catch (FileNotFoundException e) {
            System.out.println("Error saving products to file.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String electronicsToString(Product product) {
        Electronics electronics = (Electronics) product;
        // Convert product details to a string format
        return electronics.getProductId() + ", " +
                electronics.getProductType()+ ", " +
                electronics.getProductName() + ", " +
                electronics.getAvailableItems() + ", " +
                electronics.getPrice() + ", " +
                electronics.getBrand() + ", " +
                electronics.getWarrantyPeriod();

    }
    private String clothingToString(Product product) {
        Clothing clothing = (Clothing) product;
        // Convert product details to a string format
        return clothing.getProductId() + ", " +
                clothing.getProductType()+ ", " +
                clothing.getProductName() + ", " +
                clothing.getAvailableItems() + ", " +
                clothing.getPrice() + ", " +
                clothing.getSize() + ", " +
                clothing.getColor();

    }
    private void readProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;

            // Read lines from the file
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",\\s*"); // Split by comma and optional space

                if (values.length >= 6) {
                    String id = values[0].trim();
                    String category = values[1].trim();
                    String name = values[2].trim();

                    int quantity = Integer.parseInt(values[3].trim());
                    double price = Double.parseDouble(values[4].trim());


                    if(category.equals("electronics")){
                        String brand = values[5].trim();
                        int warranty = Integer.parseInt(values[6].trim());
                        Electronics electronics = new Electronics(id,category,name,quantity,price,brand,warranty);
                        addProduct(electronics);
                    }else if(category.equals("clothing")){
                        String colour = values[5].trim();
                        int size = Integer.parseInt(values[5].trim());
                        Clothing clothing = new Clothing(id,category,name,quantity,price,size,colour);
                        addProduct(clothing);
                    }

                    // Do something with the extracted values
                    System.out.println("ID: " + id);
                    System.out.println("Category: " + category);
                    System.out.println("Quantity: " + quantity);
                    System.out.println("Price: " + price);


                    System.out.println("------------------------");
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading products from file.");
            e.printStackTrace();
        }catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void openShoppingFirstGUI(){
        Shoppingfirst WestminsterCenterGUI=new Shoppingfirst();
        WestminsterCenterGUI.setVisible(true);
    }

    public static void main(String[] args) {
        WestminsterShoppingManager manager = new WestminsterShoppingManager() {
            @Override
            public void updateProduct(String productId, Product updatedProduct) {

            }

            @Override
            public void displayProductDetails(String productId) {

            }

            @Override
            public List<Product> searchProducts(String keyword) {
                return null;
            }

            @Override
            public void processOrder(Order order) {

            }
        };
        manager.displayMenu();
    }
}
