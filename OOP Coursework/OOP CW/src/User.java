import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private int numberOfPurchases;

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Parameterized constructor
    public User(String username, String password, int numberOfPurchases) {
        this.username = username;
        this.password = password;
        this.numberOfPurchases = numberOfPurchases;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumberOfPurchases() {
        return numberOfPurchases;
    }

    public void setNumberOfPurchases(int numberOfPurchases) {
        this.numberOfPurchases = numberOfPurchases;
    }
}
