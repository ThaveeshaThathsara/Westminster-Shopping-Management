public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productId,String productType, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productId, productType,productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getters
    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    @Override
    public String toString() {
        return "Electronics{" +
                "productId='" + super.getProductId() + '\'' +
                ", productName='" + super.getProductName() + '\'' +
                ", availableItems=" + super.getAvailableItems() +
                ", price=" + super.getPrice() +
                ", productType='" + super.getProductType() + '\'' +
                "brand='" + brand + '\'' +
                ", warrantyPeriod=" + warrantyPeriod +
                '}';
    }

    // Setters
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
}

