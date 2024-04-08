public class Electronics extends Product{
    private String brand;
    private String warrantyPeriod;

    // Default constructor
    public Electronics() {
    }

    // Parameterized constructor (Using Product parameterized constructor)
    public Electronics(String productID, String productName, String brand, String warrantyPeriod) {
        super(productID, productName);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Parameterized constructor (Using Product parameterized constructor)
    public Electronics(String productID, String productName, int numberOfAvailableItems, double price, String brand, String warrantyPeriod) {
        super(productID, productName, numberOfAvailableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Parameterized constructor (Using Product default constructor)
    public Electronics(String brand, String warrantyPeriod) {
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getters and Setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(String warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
}
