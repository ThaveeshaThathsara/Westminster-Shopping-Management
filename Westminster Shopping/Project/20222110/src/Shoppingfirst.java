import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Shoppingfirst extends JFrame {

    private JPanel topPanel, centerPanel, bottomPanel;
    private JButton addToShoppingCartBtn, shoppingCartBtn;
    private JComboBox<String> productBox;
    private DefaultTableModel model;
    private JLabel selectCategory;
    private String[] columnName = {"Product ID", "Name", "Category", "Price", "Number of Items"};
    private JTable productTable;
    private JScrollPane scrollPane;
    private JPanel additionalInfoTextArea;
    private JLabel descriptionLabel1, descriptionLabel2, descriptionLabel3, descriptionLabel4, descriptionLabel5;
    private ArrayList<Product> products = new ArrayList<>();
    private String type="All";

    private Object[][] allData = null;

    private ShoppingCart shoppingCart;  // Declare a member variable

    public Shoppingfirst() {
        loadfromFile();
        allData = convertListTo2DArray(products);
        setTitle("Westminster Shopping Center");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel
        topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectCategory = new JLabel("Select product Cat");
        selectCategory.setFont(new Font("Arial", Font.BOLD, 15));
        topPanel.add(selectCategory);

        String[] products = {"All", "Electronics", "Clothing"};
        productBox = new JComboBox<>(products);
        productBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory =  (String) productBox.getSelectedItem();


                if (selectedCategory.equals("Electronics")){
                    type="Electronics";

                }
                else if (selectedCategory.equals("Clothing")){
                    type="Clothing";

                }
                else if (selectedCategory.equals("All")){
                    type="All";
                }
                updateTableData(type);
            }
        });
        topPanel.add(productBox);

        shoppingCartBtn = new JButton("Shopping Cart");
        shoppingCartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openShoppingCart();
            }
        });
        topPanel.add(shoppingCartBtn);

        add(topPanel, BorderLayout.NORTH);

        productBox.setBorder(BorderFactory.createEmptyBorder(0, 200, 0, 200));

        // Center Panel (Table)
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Initialize the shopping cart instance
        shoppingCart = new ShoppingCart();

        // Initialize table with all data
        DefaultTableModel model = new DefaultTableModel(allData, columnName);
        productTable = new JTable(model);
        productTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer()); // Set custom renderer
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                addToLabels();
            }
        });

        // Add a scrollbar to the table
        scrollPane = new JScrollPane(productTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

//        // Initially show only 5 rows
        productTable.setPreferredScrollableViewportSize(new Dimension(productTable.getPreferredScrollableViewportSize().width,
                productTable.getRowHeight() * 5));

        // Adjust the preferred size to allow vertical scrolling
        scrollPane.setPreferredSize(new Dimension(productTable.getPreferredScrollableViewportSize().width, 150));

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel
        bottomPanel = new JPanel(new BorderLayout());

        // Add JTextArea to display additional information
        additionalInfoTextArea = new JPanel(new GridLayout(4, 1));
        descriptionLabel1 = new JLabel("Description: ");
        descriptionLabel2 = new JLabel("Description: ");
        descriptionLabel3 = new JLabel("Description: ");
        descriptionLabel4 = new JLabel("Description: ");
        descriptionLabel5 = new JLabel("Description: ");

        additionalInfoTextArea.add(descriptionLabel1);
        additionalInfoTextArea.add(descriptionLabel2);
        additionalInfoTextArea.add(descriptionLabel3);
        additionalInfoTextArea.add(descriptionLabel4);
        additionalInfoTextArea.add(descriptionLabel5);

        //add empty with extra space
        additionalInfoTextArea.setBorder(BorderFactory.createCompoundBorder(
                additionalInfoTextArea.getBorder(),
                BorderFactory.createEmptyBorder(50, 50, 50, 50)
        ));
        bottomPanel.add(additionalInfoTextArea, BorderLayout.CENTER);

        // Add button under the text area
        addToShoppingCartBtn = new JButton("Add To Shopping Cart");
        addToShoppingCartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToShoppingCart();
            }
        });
        bottomPanel.add(addToShoppingCartBtn, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // Add window listener to detect when the main window is closed
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0); // Terminate the application when the main window is closed
            }
        });
    }

    private void openShoppingCart() {
        // Show the existing shopping cart instance
        shoppingCart.updateCartDetails();
        shoppingCart.setVisible(true);
    }

    private void loadfromFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;

            // Read lines from the file
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",\\s*"); // Split by comma and optional space

                if (values.length >= 6) {
                    String id = values[0].trim();
                    String category = values[1].trim();
                    String name = values[2].trim();

                    int quantity = Integer.parseInt(values[3].trim());
                    double price = Double.parseDouble(values[4].trim());

                    if (category.equals("electronics")) {
                        String brand = values[5].trim();
                        int warranty = Integer.parseInt(values[6].trim());
                        Electronics electronics = new Electronics(id, category, name, quantity, price, brand, warranty);
                        products.add(electronics);
                    } else if (category.equals("clothing")) {
                        String colour = values[5].trim();
                        int size = Integer.parseInt(values[5].trim());
                        Clothing clothing = new Clothing(id, category, name, quantity, price, size, colour);
                        products.add(clothing);
                    }


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadClothingFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;

            // Read lines from the file
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",\\s*"); // Split by comma and optional space

                if (values.length >= 6) {
                    String id = values[0].trim();
                    String category = values[1].trim();
                    String name = values[2].trim();

                    int quantity = Integer.parseInt(values[3].trim());
                    double price = Double.parseDouble(values[4].trim());

                    if (category.equals("clothing")) {
                        String colour = values[5].trim();
                        int size = Integer.parseInt(values[5].trim());
                        Clothing clothing = new Clothing(id, category, name, quantity, price, size, colour);
                        products.add(clothing);
                    }


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadElectronicsFile() {

        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;

            // Read lines from the file
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",\\s*"); // Split by comma and optional space

                if (values.length >= 6) {
                    String id = values[0].trim();
                    String category = values[1].trim();
                    String name = values[2].trim();

                    int quantity = Integer.parseInt(values[3].trim());
                    double price = Double.parseDouble(values[4].trim());

                    if (category.equals("electronics")) {
                        String brand = values[5].trim();
                        int warranty = Integer.parseInt(values[6].trim());
                        Electronics electronics = new Electronics(id, category, name, quantity, price, brand, warranty);
                        products.add(electronics);
                    }


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object[][] convertListTo2DArray(ArrayList<Product> products) {
        Object[][] data = new Object[products.size()][columnName.length];

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            data[i][0] = product.getProductId();
            data[i][1] = product.getProductName();
            data[i][2] = product.getProductType();
            data[i][3] = product.getPrice();
            data[i][4] = product.getAvailableItems();
        }

        return data;
    }

    private void updateTableData(String type) {
         model = (DefaultTableModel) productTable.getModel();
model.setRowCount(0);

        if ("All".equals(type)) {
            // Show all data
            products.clear();
            System.out.println("All");
            loadfromFile();
        } else if("Electronics".equals(type)){
            System.out.println("Electronics");
            products.clear();
            loadElectronicsFile();
        }else if("Clothing".equals(type)){
            System.out.println("Clothing");
products.clear();
            loadClothingFile();
        }
        for (Product product : products) {
            model.addRow(new Object[]{
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductType(),
                    product.getPrice(),
                    product.getAvailableItems()
            });
        }

        model.fireTableDataChanged();
        // Convert the list to a 2D array

    }

    private void addToLabels() {
        descriptionLabel1.setText(displaySelectedProductDetails(0));
        descriptionLabel2.setText(displaySelectedProductDetails(1));
        descriptionLabel3.setText(displaySelectedProductDetails(2));
        descriptionLabel4.setText(displaySelectedProductDetails(3));
        descriptionLabel5.setText(displaySelectedProductDetails(4));
    }

    private String displaySelectedProductDetails(int index) {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            StringBuilder details = new StringBuilder();

            details.append(productTable.getColumnName(index))
                    .append(": ")
                    .append(productTable.getValueAt(selectedRow, index))
                    .append("\n");


            return details.toString();
        }
        return null;
    }

    private void updateAvailableItemsCount(int selectedRow, int quantityToAdd) {
        int availableItems = (int) productTable.getValueAt(selectedRow, 4);
        availableItems -= quantityToAdd;
        productTable.setValueAt(availableItems, selectedRow, 4);
    }

    private void addToShoppingCart() {
        int selectedRow = productTable.getSelectedRow();

        if (selectedRow != -1) {
            // Get product details from the selected row
            String productId = (String) productTable.getValueAt(selectedRow, 0);
            String productName = (String) productTable.getValueAt(selectedRow, 1);
            String category = (String) productTable.getValueAt(selectedRow, 2);
            double price = (double) productTable.getValueAt(selectedRow, 3);

            // Check if the product is already in the shopping cart
            boolean productExists = false;

            for (String cartItem : ShoppingCart.cart) {
                String[] cartItemDetails = cartItem.split(", ");
                if (productId.equals(cartItemDetails[0])) {
                    // Product with the same ID is already in the shopping cart
                    int currentQuantity = Integer.parseInt(cartItemDetails[4]);
                    if (currentQuantity >= 0) {
                        ShoppingCart.cart.set(ShoppingCart.cart.indexOf(cartItem),
                                String.format("%s, %s, %s, %.2f, %d", productId, productName, category, price, currentQuantity + 1));
                        productExists = true;
                        updateAvailableItemsCount(selectedRow, 1);
                        break;
                    } else {
                        // Display a message if the item count is already zero
                        JOptionPane.showMessageDialog(this, "Item count is already zero.");
                        return; // Exit the method to avoid further processing
                    }
                }
            }

            // If the product is not in the shopping cart, add a new row
            if (!productExists) {
                ShoppingCart.cart.add(String.format("%s, %s, %s, %.2f, %d", productId, productName, category, price, 1));
                updateAvailableItemsCount(selectedRow, 1);
            }

            // Display a message to the user
            JOptionPane.showMessageDialog(this, "Product added to the shopping cart!");
        } else {
            // Display an error message if no product is selected
            JOptionPane.showMessageDialog(this, "Please select a product to add to the shopping cart.");
        }
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Shoppingfirst dashboard = new Shoppingfirst();
            dashboard.setVisible(true);
        });
    }

    // Custom table cell renderer
    private class CustomTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Check if the value in the "Number of Items" column is less than or equal to 3
            int numberOfItems = (int) table.getModel().getValueAt(row, 4);
            if (numberOfItems <= 3) {
                cellComponent.setBackground(Color.RED);
            } else {
                cellComponent.setBackground(table.getBackground());
            }

            return cellComponent;
        }
    }
}
