public abstract class Product {
    private String productId;
    private String productName;
    private int availableItems;
    private double price;
    private String productType;

    public Product(String productId, String productType, String productName, int availableItems, double price) {
        this.productId = productId;
        this.productType = productType;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public String getProductType() {
        return productType;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", availableItems=" + availableItems +
                ", price=" + price +
                ", productType='" + productType + '\'' +
                '}';
    }

    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

