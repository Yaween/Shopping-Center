public class Clothing extends Product{
    private String size;
    private String color;

    // Default constructor
    public Clothing() {
    }

    // Parameterized constructor (Using Product parameterized constructor)
    public Clothing(String productID, String productName, String size, String color) {
        super(productID, productName);
        this.size = size;
        this.color = color;
    }

    // Parameterized constructor (Using Product parameterized constructor)
    public Clothing(String productID, String productName, int numberOfAvailableItems, double price, String size, String color) {
        super(productID, productName, numberOfAvailableItems, price);
        this.size = size;
        this.color = color;
    }

    // Parameterized constructor (Using Product default constructor)
    public Clothing(String size, String color) {
        this.size = size;
        this.color = color;
    }

    // Getters and Setters
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
