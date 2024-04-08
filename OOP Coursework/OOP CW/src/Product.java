import java.io.Serializable;

public abstract class Product implements Serializable {
    private String productID;
    private String productName;
    private int numberOfAvailableItems;
    private double price;

    // Default constructor
    public Product() {
    }

    // Parameterized constructor
    public Product(String productID, String productName) {
        this.productID = productID;
        this.productName = productName;
    }

    // Parameterized constructor
    public Product(String productID, String productName, int numberOfAvailableItems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.numberOfAvailableItems = numberOfAvailableItems;
        this.price = price;
    }

    // Parameterized constructor
    public Product(String productID, String productName, double price) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
    }

    // Getters and Setters
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumberOfAvailableItems() {
        return numberOfAvailableItems;
    }

    public void setNumberOfAvailableItems(int numberOfAvailableItems) {
        this.numberOfAvailableItems = numberOfAvailableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
}
