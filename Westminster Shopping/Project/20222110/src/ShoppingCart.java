import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends JFrame {

    public static List<String> cart = new ArrayList<>();
    private JTable shoppingcartAddProductDetails;
    private DefaultTableModel shoppingcartTableModel;
    private JTextArea cartTextArea;
    private JPanel bottomPanel;
    private JLabel totalPriceLabel;
    private JLabel FirstPurchaseDiscountLabel;
    private JLabel ThreeItemsInSameCategoryDiscountLabel;
    private JLabel FinalTotalLabel;

    ShoppingCart() {
        setTitle("Shopping Cart");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table for added product details
        String[] columnName = {"Product", "Quantity", "Price"};
        shoppingcartTableModel = new DefaultTableModel(columnName, 0);
        shoppingcartAddProductDetails = new JTable(shoppingcartTableModel);

        add(new JScrollPane(shoppingcartAddProductDetails), BorderLayout.CENTER);

        cartTextArea = new JTextArea();
        cartTextArea.setEditable(false);
        add(new JScrollPane(cartTextArea), BorderLayout.SOUTH);

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        totalPriceLabel = new JLabel("Total Price: $0.00");
        bottomPanel.add(totalPriceLabel);

        FirstPurchaseDiscountLabel = new JLabel("First Purchase Discount: $0.00");
        bottomPanel.add(FirstPurchaseDiscountLabel);

        ThreeItemsInSameCategoryDiscountLabel = new JLabel("Three Items in Same Category Discount: $0.00");
        bottomPanel.add(ThreeItemsInSameCategoryDiscountLabel);

        FinalTotalLabel = new JLabel("Final Total: $0.00");
        bottomPanel.add(FinalTotalLabel);

        add(bottomPanel, BorderLayout.PAGE_END);
    }
    public void updateCartDetails() {
        updateCartTable();
        updateTotalPriceLabel();
        updateFirstPurchaseDiscountLabel();
        updateThreeItemsDiscountLabel();
        updateFinalTotalLabel();

    }

    public static void main(String[] args) {
        ShoppingCart displayShoppingCart = new ShoppingCart();
        displayShoppingCart.setVisible(true);
    }

    public  void addToCart(String productId, String productName, String category, double price) {
        String[] item = {productId, productName, category, String.format("%.2f", price)};
        cart.add(String.join(", ", item));
        ShoppingCart shoppingCartGUI = new ShoppingCart();

        shoppingCartGUI.updateTotalPriceLabel();
        shoppingCartGUI.updateFirstPurchaseDiscountLabel();
        shoppingCartGUI.updateThreeItemsDiscountLabel();
        shoppingCartGUI.updateFinalTotalLabel();

    }

    private void updateCartTable() {
        shoppingcartTableModel.setRowCount(0);

        for (String item : cart) {
            String[] details = item.split(", ");

            String[] rowData = new String[3];

            rowData[0] = details[0]+" "+ details[1]+" "+details[2]; // Value for the first column
            rowData[1] = String.valueOf(1); // Value for the second column
            rowData[2] = details[3]; // Value for the third column

            shoppingcartTableModel.addRow(rowData);
        }
    }


    private void updateTotalPriceLabel() {
        double total = 0;

        for (String item : cart) {
            String[] details = item.split(", ");
            double price = Double.parseDouble(details[3]);
            total += price;
        }

        totalPriceLabel.setText("Total Price: $" + String.format("%.2f", total));
    }

    private void updateFirstPurchaseDiscountLabel() {
        // Implement logic for first purchase discount if needed
        double discount = 0.0;
        FirstPurchaseDiscountLabel.setText("First Purchase Discount: $" + String.format("%.2f", discount));
    }

    private void updateThreeItemsDiscountLabel() {
        // Implement logic for three items in the same category discount if needed
        double discount = 0.0;
        ThreeItemsInSameCategoryDiscountLabel.setText("Three Items in Same Category Discount: $" + String.format("%.2f", discount));
    }

    private void updateFinalTotalLabel() {
        // Implement logic for calculating the final total if needed
        double finalTotal = 0.0;
        FinalTotalLabel.setText("Final Total: $" + String.format("%.2f", finalTotal));
    }
}
