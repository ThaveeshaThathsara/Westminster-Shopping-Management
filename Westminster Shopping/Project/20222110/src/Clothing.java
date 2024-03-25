public class Clothing extends Product {
    private int size;
    private String color;


    public Clothing(String productId,String productType, String productName, int availableItems, double price, int size, String color) {
        super(productId, productType,productName, availableItems, price);
        this.size = size;
        this.color = color;
    }

    // Getters
    public int getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Clothing{" +
                "productId='" + super.getProductId() + '\'' +
                ", productName='" + super.getProductName() + '\'' +
                ", availableItems=" + super.getAvailableItems() +
                ", price=" + super.getPrice() +
                ", productType='" + super.getProductType() + '\'' +
                "size='" + size + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    // Setters
    public void setSize(int size) {
        this.size = size;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
