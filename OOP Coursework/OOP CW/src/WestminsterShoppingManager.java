import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager{

    static WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
    static Scanner input = new Scanner(System.in);
    static private ArrayList<Product> products = new ArrayList<>();

    static private ArrayList<ShoppingCart> cart = new ArrayList<>();
    static private ArrayList<String> cartItems = new ArrayList<>();

    static private ArrayList<User> users = new ArrayList<>();

    static private User LoggedInUser = null;

    static private double total;

    public static void main(String[] args) {
        westminsterShoppingManager.loadProducts();
        westminsterShoppingManager.loadUsers();
        printMenu();
    }

    public static void printMenu(){
        System.out.println("\n------------------------------------------");
        System.out.println("Welcome to Westminster Shopping Menu\n");
        System.out.println("[1] Add a new item to the shopping cart");
        System.out.println("[2] Delete an item from the shopping cart");
        System.out.println("[3] Print products in shopping cart");
        System.out.println("[4] Save products to file");
        System.out.println("[5] Open Shopping Center");
        System.out.println("[6] Exit the system");
        System.out.println("------------------------------------------");

        System.out.print("Enter your choice (1 to 6) : ");
        String SelectedOption = input.next();

        switch (SelectedOption){
            case "1":
                westminsterShoppingManager.addNewProduct();
                printMenu();
                break;
            case "2":
                westminsterShoppingManager.deleteProduct();
                printMenu();
                break;
            case "3":
                westminsterShoppingManager.printProducts();
                printMenu();
                break;
            case "4":
                westminsterShoppingManager.saveProducts(products);
                printMenu();
                break;
            case "5":
                westminsterShoppingManager.userVerification();
                break;
            case "6":
                System.out.println("\nThank you for using Westminster Shopping Center");
                break;
            default:
                System.out.println("Invalid option! \nPlease try again");
                printMenu();
                break;
        }
    }


    @Override
    public void addNewProduct() {
        Scanner input = new Scanner(System.in);
        if(products.size() <= 50){
            System.out.println("\nDo you want add electronics or clothing? \n1. Electronics \n2. Clothing");
            System.out.print("Enter your choice (1 or 2) : ");
            String choice = input.next();

            if(!choice.equals("1") && !choice.equals("2")){
                System.out.println("\nInvalid option! \nPlease try again");
                addNewProduct();
            }

            System.out.print("\nEnter product ID (eg : p001, p002) : ");
            String productID = input.next();

            for(int x = 0; x < products.size(); x++) {
                if (products.get(x).getProductID().equals(productID)) {
                    System.out.println("\nProduct ID already exists! \nPlease try again");
                    westminsterShoppingManager.addNewProduct();
                    return;
                }
            }
            //Using Exceptions to handle Input miss matches
            try {
                System.out.print("Enter product name : ");
                String productName = input.next();
                System.out.print("Enter number of available items : ");
                int numberOfAvailableItems = input.nextInt();
                System.out.print("Enter price : ");
                double price = input.nextDouble();

                if(choice.equals("1")) {
                    System.out.print("Enter brand : ");
                    String brand = input.next();
                    System.out.print("Enter warranty period (In Weeks) : ");
                    String warrantyPeriod = input.next();

                    if(warrantyPeriod.equals("0")){
                        warrantyPeriod = "No warranty";
                    } else if(warrantyPeriod.equals("1")){
                        warrantyPeriod += " week warranty";
                    } else {
                        warrantyPeriod += " weeks warranty";
                    }

                    Electronics electronics = new Electronics(productID, productName, numberOfAvailableItems, price, brand, warrantyPeriod);
                    products.add(electronics);
                }

                else if(choice.equals("2")) {
                    System.out.print("Enter size : ");
                    String size = input.next();
                    System.out.print("Enter color : ");
                    String color = input.next();

                    Clothing clothing = new Clothing(productID, productName, numberOfAvailableItems, price, size, color);
                    products.add(clothing);
                }

                System.out.println("\n"+ productID + " product has been added successfully!");

            } catch (InputMismatchException e){
                System.out.print("Invalid Input. Please Try again\n");
                westminsterShoppingManager.addNewProduct();
            }

        }
        else{
            System.out.println("Maximum number of products reached");
        }

    }

    @Override
    public void deleteProduct() {
        System.out.print("\nEnter product ID (eg : p001, p002) : ");
        String productID = input.next();

        for(int x = 0; x < products.size(); x++){
            if(products.get(x).getProductID().equals(productID)){
                //checking if the item to be deleted is an Electronic
                if (products.get(x) instanceof Electronics){
                    products.remove(x);
                    System.out.println("\nThe following Product has been deleted successfully!");
                    System.out.println("\tProduct ID : " + productID +"\n\tCategory : Electronics");
                    System.out.println("\nThe number of Products remaining in the system : " + products.size());
                    return;
                }
                //checking if the item to be deleted is a Clothing
                else if (products.get(x) instanceof Clothing) {
                    products.remove(x);
                    System.out.println("\nThe following Product has been deleted successfully!");
                    System.out.println("\tProduct ID : " + productID +"\n\tCategory : Clothing");
                    System.out.println("\nThe number of Products remaining in the system : " + products.size());
                    return;
                }
            }
        }
        System.out.println("\nProduct not found!");
    }

    @Override
    public void printProducts() {
        // Cloning the products array list
        ArrayList<Product> list = new ArrayList<>(products);

        // Sort by product ID
        for(int x = 0; x < list.size(); ++x) {
            for(int y = x + 1; y < list.size(); ++y) {
                if (list.get(x).getProductID().compareTo(list.get(y).getProductID()) > 0) {
                    Product temp = list.get(x);
                    list.set(x, list.get(y));
                    list.set(y, temp);
                }
            }
        }

        // Print products
        for(int x = 0; x < list.size(); x++){
            if(list.get(x) instanceof Electronics){
                System.out.println("\nProduct Type : Electronics");
            }
            else if(list.get(x) instanceof Clothing){
                System.out.println("\nProduct Type : Clothing");
            }

            System.out.println("Product ID : " + list.get(x).getProductID());
            System.out.println("Product name : " + list.get(x).getProductName());
            System.out.println("Number of available items : " + list.get(x).getNumberOfAvailableItems());
            System.out.println("Price : " + list.get(x).getPrice());

            if(list.get(x) instanceof Electronics){
                System.out.println("Brand : " + ((Electronics) list.get(x)).getBrand());
                System.out.println("Warranty period : " + ((Electronics) list.get(x)).getWarrantyPeriod());
            }

            else if(list.get(x) instanceof Clothing){
                System.out.println("Size : " + ((Clothing) list.get(x)).getSize());
                System.out.println("Color : " + ((Clothing) list.get(x)).getColor());
            }
        }
    }

    @Override
    public void saveProducts(ArrayList<Product> products) {
        try {
            File file = new File("Products.txt");

            if (file.createNewFile()) {
                //Checking whether the file is created successfully
                System.out.println("File created: " + file.getName());
            }

            FileOutputStream writer = new FileOutputStream("Products.txt");
            ObjectOutputStream obj = new ObjectOutputStream(writer);

            obj.writeObject(products);
            writer.close();
            obj.close();

            System.out.println("\nAll products saved successfully!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUsers(ArrayList<User> users) {
        try {
            File file = new File("Users.txt");

            if (file.createNewFile()) {
                //Checking whether the file is created successfully
                System.out.println("File created: " + file.getName());
            }

            FileOutputStream writer = new FileOutputStream("Users.txt");
            ObjectOutputStream obj = new ObjectOutputStream(writer);

            obj.writeObject(users);
            writer.close();
            obj.close();

            System.out.println("\nAll users saved successfully!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadProducts() {
        try{
            FileInputStream readData = new FileInputStream("Products.txt");
            ObjectInputStream readStream = new ObjectInputStream(readData);

            products = (ArrayList<Product>) readStream.readObject();
            readStream.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.print("");
        }
    }

    @Override
    public void loadUsers() {
        try{
            FileInputStream readData = new FileInputStream("Users.txt");
            ObjectInputStream readStream = new ObjectInputStream(readData);

            users = (ArrayList<User>) readStream.readObject();
            readStream.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.print("");
        }
    }

    public void userVerification() {
        System.out.print("Do you already have an account? (Yes or No) : ");
        String accountOption = input.next().toUpperCase();

        if(accountOption.equals("NO")){
            System.out.println("Please sign up with your credentials");
            System.out.print("Enter your Username: ");
            String username = input.next();
            System.out.print("Enter your Password: ");
            String password = input.next();
            User user = new User(username, password, 0);
            users.add(user);
            LoggedInUser = user;
            saveUsers(users);
        }
        else if(accountOption.equals("YES")){
            System.out.println("Please login with your credentials");
            System.out.print("Enter your Username: ");
            String username = input.next();
            System.out.print("Enter your Password: ");
            String password = input.next();

            boolean loginSuccessful = false;

            for(int x = 0; x < users.size(); x++){
                if(users.get(x).getUsername().equals(username) && users.get(x).getPassword().equals(password)){
                    System.out.println("Log in Successful.");
                    User user = new User(username, password, users.get(x).getNumberOfPurchases());
                    LoggedInUser = user;
                    loginSuccessful = true;

                }
            }

            if (!loginSuccessful){
                System.out.println("Username or password incorrect! \nYou will be returned to the User verification center\n");
                userVerification();
            }
        }
        else{
            System.out.println("Invalid option! \nPlease try again");
            userVerification();
        }

        westminsterShoppingManager.guiFrame();
    }

    public void guiFrame() {
        JFrame frame = new JFrame("Westminster Shopping Center");

        //Creating the shopping cart button
        JButton shoppingCart = new JButton("Shopping Cart");
        shoppingCart.setBounds(600, 20, 130, 34);

        //Creating the label for the dropdown
        JLabel label01 = new JLabel("Select Product Category");
        label01.setBounds(100, 50, 200, 20);
        label01.setFont(new Font("Dialog", Font.PLAIN, 12));

        //Customizing the buttons
        Color buttonBackground01 = new Color(175, 205, 255);
        Color buttonForeground01 = new Color(0, 0, 0);
        Color cbBackground01 = new Color(255, 255, 255);
        Color cbForeground01 = new Color(0, 0, 0);

        //Creating the dropdown list
        String[] categories = { " All "," Electronics ", " Clothing " };
        JComboBox<String> cbCategories = new JComboBox(categories);
        cbCategories.setFont(new Font("Dialog", Font.PLAIN, 12));
        cbCategories.setBounds(300, 45, 200, 30);
        cbCategories.setSelectedIndex(0);

        String[] columnNames = { "Product ID", "Name", "Category", "Price(€)", "Info" };
        Object[][] data = new Object[products.size()][5];

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) cbCategories.getSelectedItem();
                if(selectedItem.equals(" All ")){
                    //Creating data[] using products array list
                    for(int x = 0; x < products.size(); x++){
                        data[x][0] = products.get(x).getProductID();
                        data[x][1] = products.get(x).getProductName();
                        if(products.get(x) instanceof Electronics){
                            data[x][2] = "Electronics";
                        }
                        else if(products.get(x) instanceof Clothing){
                            data[x][2] = "Clothing";
                        }
                        data[x][3] = products.get(x).getPrice();
                        if(products.get(x) instanceof Electronics){
                            data[x][4] = "Brand: " + ((Electronics) products.get(x)).getBrand() + ", Warranty: " + ((Electronics) products.get(x)).getWarrantyPeriod();
                        }
                        else if(products.get(x) instanceof Clothing){
                            data[x][4] = "Size: " + ((Clothing) products.get(x)).getSize() + ", Color: " + ((Clothing) products.get(x)).getColor();
                        }
                    }
                }
                else if(selectedItem.equals(" Electronics ")){
                    //Creating data[] using products array list where only electronics are included
                    for (int i = 0; i < data.length; i++) {
                        Arrays.fill(data[i], "");
                    }
                    int y = 0;
                    for(int x = 0; x < products.size(); x++){
                        if(products.get(x) instanceof Electronics){
                            data[y][0] = products.get(x).getProductID();
                            data[y][1] = products.get(x).getProductName();
                            data[y][2] = "Electronics";
                            data[y][3] = products.get(x).getPrice();
                            data[y][4] = "Brand: " + ((Electronics) products.get(x)).getBrand() + ", Warranty: " + ((Electronics) products.get(x)).getWarrantyPeriod();
                            y++;
                        }
                    }
                }
                else if(selectedItem.equals(" Clothing ")){
                    //Creating data[] using products array list where only clothing are included
                    for (int i = 0; i < data.length; i++) {
                        Arrays.fill(data[i], "");
                    }
                    int y = 0;
                    for(int x = 0; x < products.size(); x++){
                        if(products.get(x) instanceof Clothing){
                            data[y][0] = products.get(x).getProductID();
                            data[y][1] = products.get(x).getProductName();
                            data[y][2] = "Clothing";
                            data[y][3] = products.get(x).getPrice();
                            data[y][4] = "Size: " + ((Clothing) products.get(x)).getSize() + ", Color: " + ((Clothing) products.get(x)).getColor();
                            y++;
                        }
                    }
                }
            }
        };

        cbCategories.addActionListener(listener);
        listener.actionPerformed(null); // Trigger event handler for default item

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(150, 10, 10, 10));

        JPanel tablePanel = new JPanel(new BorderLayout());

        JTable table = new JTable(data, columnNames);


        //Setting the color for table rows with less available products
//        for (int j = 0; j< table.getRowCount(); j++){
//            if (products.get(j).getNumberOfAvailableItems() <= 3){
//                table.setBackground( Color.RED);
//            }
//        }

        table.setRowHeight(30);
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn infoColumn = columnModel.getColumn(4);
        infoColumn.setPreferredWidth(230);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Create a panel for product details
        JPanel detailsPanel = new JPanel(new FlowLayout());
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 50, 20));
        detailsPanel.setLayout(new GridLayout(0, 1));

        Font titleFont = new Font("Dialog", Font.BOLD, 12);
        Font detailsFont = new Font("Dialog", Font.PLAIN, 12);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                detailsPanel.removeAll(); // Remove all components from the details panel

                JSeparator separator = new JSeparator(); // Create a horizontal line separator
                separator.setForeground(Color.BLACK);
                separator.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
                detailsPanel.add(separator); // Add the separator to the panel

                int selectRow = table.getSelectedRow();
                String productID = (String) table.getValueAt(selectRow, 0);

                for(int x = 0; x < products.size(); x++) {
                    if (products.get(x).getProductID() == productID) {
                        JLabel title = new JLabel("Selected Product Details");
                        JLabel productId = new JLabel("Product ID: " + products.get(x).getProductID());

                        title.setFont(titleFont);
                        productId.setFont(detailsFont);

                        detailsPanel.add(title);
                        detailsPanel.add(productId);

                        if (products.get(x) instanceof Electronics) {
                            JLabel category = new JLabel("Category: Electronics");
                            JLabel productName = new JLabel("Product name: " + products.get(x).getProductName());
                            JLabel brand = new JLabel("Brand: " + ((Electronics) products.get(x)).getBrand());
                            JLabel warranty = new JLabel("Warranty: " + ((Electronics) products.get(x)).getWarrantyPeriod());
                            JLabel itemsAvailable = new JLabel("Items available: " + products.get(x).getNumberOfAvailableItems());

                            category.setFont(detailsFont);
                            productName.setFont(detailsFont);
                            brand.setFont(detailsFont);
                            warranty.setFont(detailsFont);
                            itemsAvailable.setFont(detailsFont);

                            detailsPanel.add(category);
                            detailsPanel.add(productName);
                            detailsPanel.add(brand);
                            detailsPanel.add(warranty);
                            detailsPanel.add(itemsAvailable);

                        } else if (products.get(x) instanceof Clothing) {
                            JLabel category = new JLabel("Category: Clothing");
                            JLabel productName = new JLabel("Product name: " + products.get(x).getProductName());
                            JLabel size = new JLabel("Size: " + ((Clothing) products.get(x)).getSize());
                            JLabel color = new JLabel("Color: " + ((Clothing) products.get(x)).getColor());
                            JLabel itemsAvailable = new JLabel("Items available: " + products.get(x).getNumberOfAvailableItems());

                            category.setFont(detailsFont);
                            productName.setFont(detailsFont);
                            size.setFont(detailsFont);
                            color.setFont(detailsFont);
                            itemsAvailable.setFont(detailsFont);

                            detailsPanel.add(category);
                            detailsPanel.add(productName);
                            detailsPanel.add(size);
                            detailsPanel.add(color);
                            detailsPanel.add(itemsAvailable);
                        }

                        JButton addToCart = new JButton("Add to Shopping Cart");
                        addToCart.setPreferredSize(new Dimension(200, 30));

                        Product product = products.get(x);
                        int index = x;

                        addToCart.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                //Reducing the available items
                                products.get(index).setNumberOfAvailableItems(products.get(index).getNumberOfAvailableItems() - 1);
                                cartItems.add(product.getProductID());

                                // Check if a ShoppingCart item with this productID already exists
                                int existingItemIndex = -1;
                                for (int i = 0; i < cart.size(); i++) {
                                    if (cart.get(i).getProduct().getProductID().equals(product.getProductID())) {
                                        existingItemIndex = i;
                                        break;
                                    }
                                }

                                int quantity = Collections.frequency(cartItems, product.getProductID());

                                String productID = product.getProductID();
                                String productName = product.getProductName();

                                if(product instanceof Electronics){
                                    String brand = ((Electronics) product).getBrand();
                                    String warranty = ((Electronics) product).getWarrantyPeriod();

                                    Product electronics = new Electronics(productID, productName, brand, warranty);
                                    ShoppingCart shoppingCart = new ShoppingCart(electronics, quantity, product.getPrice());

                                    // If a matching item exists, update its quantity
                                    if (existingItemIndex != -1) {
                                        cart.get(existingItemIndex).setQuantity(quantity);
                                        cart.get(existingItemIndex).setPrice(product.getPrice() * quantity);
                                    } else {
                                        cart.add(shoppingCart);
                                    }

                                }
                                else if(product instanceof Clothing) {
                                    String size = ((Clothing) product).getSize();
                                    String color = ((Clothing) product).getColor();

                                    Product clothing = new Clothing(productID, productName, size, color);
                                    ShoppingCart shoppingCart = new ShoppingCart(clothing, quantity, product.getPrice());

                                    // If a matching item exists, update its quantity
                                    if (existingItemIndex != -1) {
                                        cart.get(existingItemIndex).setQuantity(quantity);
                                        cart.get(existingItemIndex).setPrice(product.getPrice() * quantity);
                                    } else {
                                        cart.add(shoppingCart);
                                    }
                                }
                            }
                        });

                        // Formatting the button
                        addToCart.setBackground(buttonBackground01);
                        addToCart.setForeground(buttonForeground01);
                        addToCart.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                        addToCart.setFocusPainted(false);

                        // Create a panel to center the button within
                        JPanel buttonPanel = new JPanel(new GridBagLayout());
                        GridBagConstraints c = new GridBagConstraints();
                        c.gridwidth = GridBagConstraints.REMAINDER;
                        c.anchor = GridBagConstraints.CENTER;
                        buttonPanel.add(addToCart, c);

                        // Add the button panel to the details panel
                        detailsPanel.add(buttonPanel);
                        mainPanel.add(detailsPanel, BorderLayout.SOUTH); // Add details panel to the main panel
                        mainPanel.revalidate(); // Revalidate the main panel for layout updates
                    }
                }
            }
        });

        //Creating the scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        shoppingCart.setBackground(buttonBackground01);
        shoppingCart.setForeground(buttonForeground01);
        shoppingCart.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        shoppingCart.setFocusPainted(false);

        cbCategories.setBackground(cbBackground01);
        cbCategories.setForeground(cbForeground01);
        cbCategories.setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setBackground(cbBackground01);
                setForeground(cbForeground01);
                super.paint(g);
            }
        });

        shoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //saving the updated product quantity to the file
                saveProducts(products);
                JFrame frame02 = new JFrame("Shopping Cart");

                JPanel mainPanel02 = new JPanel(new BorderLayout());
                mainPanel02.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JPanel tablePanel02 = new JPanel(new BorderLayout());

                String[] columnNames02 = {"Product", "Quantity", "Price(€)"};
                Object[][] data02 = new Object[cart.size()][3];

                for (int x = 0; x < cart.size(); x++) {
                    String product = "";
                    if(cart.get(x).getProduct() instanceof Electronics){
                        String brand = ((Electronics) cart.get(x).getProduct()).getBrand();
                        String warranty = ((Electronics) cart.get(x).getProduct()).getWarrantyPeriod();
                        product = cart.get(x).getProduct().getProductID() + ", "
                                + cart.get(x).getProduct().getProductName() + ", "
                                + brand + ", " + warranty;
                    }
                    else if(cart.get(x).getProduct() instanceof Clothing) {
                        String size = ((Clothing) cart.get(x).getProduct()).getSize();
                        String color = ((Clothing) cart.get(x).getProduct()).getColor();

                        product = cart.get(x).getProduct().getProductID() + ", "
                                + cart.get(x).getProduct().getProductName() + ", "
                                + size + ", " + color;
                    }

                    data02[x][0] = product;
                    data02[x][1] = cart.get(x).getQuantity();
                    data02[x][2] = cart.get(x).getPrice();
                }

                JTable table02 = new JTable(data02, columnNames02);

                //Setting the table Read-Only
                for (int i = 0; i < table02.getColumnCount(); i++) {
                    table02.getColumnModel().getColumn(i).setCellEditor(null);
                }

                table02.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
                table02.setRowHeight(30);

                TableColumnModel columnModel = table02.getColumnModel();
                TableColumn productColumn = columnModel.getColumn(0);
                productColumn.setPreferredWidth(250);

                DefaultTableCellRenderer centerRenderer02 = new DefaultTableCellRenderer();
                centerRenderer02.setHorizontalAlignment(JLabel.CENTER);
                table02.setDefaultRenderer(Object.class, centerRenderer02);

                //Creating the scroll pane
                JScrollPane scrollPane02 = new JScrollPane(table02);
                tablePanel02.add(scrollPane02, BorderLayout.CENTER);
                mainPanel02.add(tablePanel02, BorderLayout.CENTER);

                // Create a panel for total
                JPanel totalPanel = new JPanel(new FlowLayout());
                totalPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 50, 20));
                totalPanel.setLayout(new GridLayout(0, 1));

                for(int x = 0; x < cart.size(); x++){
                    total += cart.get(x).getPrice();
                }

                JLabel totalLabel = new JLabel("Total : \t" + total + "€");
                totalLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
                totalPanel.add(totalLabel);

                boolean discount1 = false;
                if(LoggedInUser.getNumberOfPurchases() == 0){
                    double firstPurchase = total * 0.1;
                    JLabel firstPurchaseLabel = new JLabel("First Purchase Discount (10%) : \t- " + firstPurchase + "€");
                    firstPurchaseLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
                    totalPanel.add(firstPurchaseLabel);
                    discount1 = true;
                }

                for(int x = 0; x < users.size(); x++){
                    if(users.get(x).getUsername().equals(LoggedInUser.getUsername())){
                        users.get(x).setNumberOfPurchases(users.get(x).getNumberOfPurchases() + 1);
                    }
                }
                saveUsers(users);

                int electronicsCount = 0;
                int clothingCount = 0;

                for(int x = 0; x < cart.size(); x++){
                    if(cart.get(x).getProduct() instanceof Electronics){
                        electronicsCount += cart.get(x).getQuantity();
                    }
                    else if(cart.get(x).getProduct() instanceof Clothing){
                        clothingCount += cart.get(x).getQuantity();
                    }
                }

                boolean discount2 = false;
                if(electronicsCount >= 3 || clothingCount >= 3){
                    double threePurchases = total * 0.2;
                    JLabel threePurchasesLabel = new JLabel("Three Items in the Same Category Discount (20%) : \t- " + (threePurchases * 0.2) + "€");
                    threePurchasesLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
                    totalPanel.add(threePurchasesLabel);
                    discount2 = true;
                }

                double finalTotal;

                if(discount1 && !discount2) {
                    finalTotal = total - (total * 0.1);
                }
                else if(!discount1 && discount2){
                    finalTotal = total - (total * 0.2);
                }
                else if(discount1 && discount2){
                    finalTotal = total - ((total * 0.1) + (total * 0.2));
                }
                else{
                    finalTotal = total;
                }

                JLabel finalTotalLabel = new JLabel("Final Total : \t" + finalTotal + "€");
                totalPanel.add(finalTotalLabel);

                mainPanel02.add(totalPanel, BorderLayout.SOUTH); // Add details panel to the main panel
                mainPanel02.revalidate(); // Revalidate the main panel for layout updates

                frame02.getContentPane().add(mainPanel02);

                frame02.setSize(600, 400); // Setting the size of the frame
                frame02.setVisible(true);// Making the frame visible
            }
        });

        frame.add(label01);
        frame.add(cbCategories);
        frame.add(shoppingCart);
        frame.getContentPane().add(mainPanel);

        frame.setSize(800, 700); // Setting the size of the frame
        frame.setVisible(true);// Making the frame visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // The frame will close and the program will stop running when the close button is clicked ...

    }
}