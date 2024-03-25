import java.util.List;

public interface ShoppingManager {
    void addProduct(Product product);
    void removeProduct(String productId);
    Product getProduct(String productId);
    List<Product> getAllProducts();

    // Additional method signatures
    void updateProduct(String productId, Product updatedProduct);
    void displayProductDetails(String productId);
    List<Product> searchProducts(String keyword);
    void processOrder(Order order);
    // ... any other methods you think might be necessary for your application
}

