import java.util.ArrayList;

public class ShoppingCart {

    ArrayList<Product> listOfProducts = new ArrayList<Product>();


    private Product product;
    private int quantity;
    private double price;

    public ShoppingCart(Product product, int quantity, double price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void addProduct(Product product) {
        listOfProducts.add(product);
    }

    public void removeProduct(Product product) {
        listOfProducts.remove(product);
    }

    public void calculateTotalCost(){
        double totalCost = 0;

        for(int x = 0; x < listOfProducts.size(); x++){
            totalCost += listOfProducts.get(x).getPrice();
        }

        System.out.println("Total cost: " + totalCost);
    }


}
