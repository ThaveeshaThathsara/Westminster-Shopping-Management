import java.util.List;

public class Order {
    private String orderId;
    private List<Product> products;
    private double totalCost;
    private String customerName;
    private String customerEmail;
    private String orderStatus; // e.g., "Pending", "Shipped", "Delivered"

    // Constructor
    public Order(String orderId, List<Product> products, double totalCost,
                 String customerName, String customerEmail, String orderStatus) {
        this.orderId = orderId;
        this.products = products;
        this.totalCost = totalCost;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.orderStatus = orderStatus;
    }

    // Getters and Setters
    // Existing getters and setters
    // ...

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
