import java.util.ArrayList;

public interface ShoppingManager {
    void addNewProduct();
    void deleteProduct();
    void printProducts();
    void saveProducts(ArrayList<Product> products);
    void saveUsers(ArrayList<User> users);
    void loadProducts();
    void loadUsers();
}
