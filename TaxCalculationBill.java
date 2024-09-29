import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

// Product Class
class Product {
    private String productId;
    private String productName;
    private double price;
    private double taxRate;

    public Product(String productId, String productName, double price, double taxRate) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.taxRate = taxRate;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public double calculateTax() {
        return price * (taxRate / 100);
    }

    public double getTotalPrice() {
        return price + calculateTax();
    }

    @Override
    public String toString() {
        return String.format("%s - Price: %.2f, Tax: %.2f, Total: %.2f", productName, price, calculateTax(), getTotalPrice());
    }
}

// Bill Class
class Bill {
    private String billNumber;
    private java.util.List<Product> products;
    private double totalAmount;

    public Bill(String billNumber) {
        this.billNumber = billNumber;
        this.products = new ArrayList<>();
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public java.util.List<Product> getProducts() {
        return products;
    }

    public double calculateTotalAmount() {
        totalAmount = 0.0;
        for (Product product : products) {
            totalAmount += product.getTotalPrice();
        }
        return totalAmount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bill Number: ").append(billNumber).append("\n");
        sb.append("-------------------------------------------------\n");
        for (Product product : products) {
            sb.append(product).append("\n");
        }
        sb.append("-------------------------------------------------\n");
        sb.append("Total Amount: ").append(totalAmount).append("\n");
        return sb.toString();
    }
}

// TaxCalculation Class
class TaxCalculation {
    public static double getTaxRate(String category) {
        switch (category.toLowerCase()) {
            case "food":
                return 5.0;  // 5% tax
            case "electronics":
                return 18.0; // 18% tax
            case "clothing":
                return 12.0; // 12% tax
            default:
                return 10.0; // default tax
        }
    }
}

// Main Class with Swing UI
public class TaxCalculationBillingSystem extends JFrame {
    private DefaultTableModel tableModel;
    private Bill currentBill;
    private Map<String, Bill> billHistory;
    private JLabel billCountLabel;
    private JLabel currentBillLabel;
    private JLabel totalAmountLabel;
    private JLabel totalBillCountLabel;
    private JLabel totalSalesLabel;
    private JLabel lastBillNumberLabel;
    private JLabel lastBillTotalLabel;
    private double totalSales;
    private Bill previousBill;

    public TaxCalculationBillingSystem() {
        setTitle("Tax Calculation Billing System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        billHistory = new HashMap<>();
        totalSales = 0.0;

        // Bill Info Panel
        JPanel billInfoPanel = new JPanel(new GridLayout(1, 3));
        billCountLabel = new JLabel("Total Bills: 0");
        currentBillLabel = new JLabel("Current Bill: N/A");
        totalAmountLabel = new JLabel("Total Amount: $0.00");
        billInfoPanel.add(billCountLabel);
        billInfoPanel.add(currentBillLabel);
        billInfoPanel.add(totalAmountLabel);
        billInfoPanel.setVisible(false); // Initially hidden

        // Top Panel for total bills and sales
        JPanel topPanel = new JPanel(new GridLayout(3, 2));
        totalBillCountLabel = new JLabel("Total Number of Bills Generated: 0");
        totalSalesLabel = new JLabel("Total Sales: $0.00");
        lastBillNumberLabel = new JLabel("Last Bill Number: N/A");
        lastBillTotalLabel = new JLabel("Last Bill Total: $0.00");
        topPanel.add(totalBillCountLabel);
        topPanel.add(totalSalesLabel);
        topPanel.add(lastBillNumberLabel);
        topPanel.add(lastBillTotalLabel);

        add(topPanel, BorderLayout.NORTH); // Add the top panel first
        add(billInfoPanel, BorderLayout.CENTER); // Then add the bill info panel below it

        // UI Components for Product Entry
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2));

        JLabel idLabel = new JLabel("Product ID:");
        JTextField idField = new JTextField();

        JLabel nameLabel = new JLabel("Product Name:");
        JTextField nameField = new JTextField();

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();

        JLabel categoryLabel = new JLabel("Category:");
        JTextField categoryField = new JTextField();

        JButton addButton = new JButton("Add Product");

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryField);
        inputPanel.add(new JLabel()); // Empty label for layout
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.WEST);

        // Table to display products
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Price", "Tax", "Total"}, 0);
        JTable productTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        tableScrollPane.setVisible(false); // Initially hidden
        add(tableScrollPane, BorderLayout.CENTER);

        // Invoice and Search Panel
        JPanel actionPanel = new JPanel(new GridLayout(1, 3));
        JButton invoiceButton = new JButton("Generate Invoice");
        JButton searchButton = new JButton("Search Bill");
        JTextField searchField = new JTextField();
        actionPanel.add(invoiceButton);
        actionPanel.add(searchField);
        actionPanel.add(searchButton);
        add(actionPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                String category = categoryField.getText();

                double taxRate = TaxCalculation.getTaxRate(category);
                Product product = new Product(id, name, price, taxRate);
                currentBill.addProduct(product);

                // Add product to table
                tableModel.addRow(new Object[]{
                        product.getProductId(),
                        product.getProductName(),
                        product.getPrice(),
                        product.calculateTax(),
                        product.getTotalPrice()
                });

                // Clear input fields
                idField.setText("");
                nameField.setText("");
                priceField.setText("");
                categoryField.setText("");

                // Show the table and bill info if hidden
                if (!tableScrollPane.isVisible()) {
                    tableScrollPane.setVisible(true);
                    billInfoPanel.setVisible(true);
                }
            }
        });

        invoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double totalAmount = currentBill.calculateTotalAmount();
                totalSales += totalAmount;

                JOptionPane.showMessageDialog(TaxCalculationBillingSystem.this,
                        "Bill Number: " + currentBill.getBillNumber() + "\nTotal Invoice Amount: " + totalAmount,
                        "Invoice", JOptionPane.INFORMATION_MESSAGE);

                saveBill(currentBill);
                updateBillInfo();

                // Hide the table and bill info after the bill is finalized
                previousBill = currentBill;  // Store the previous bill
                lastBillNumberLabel.setText("Last Bill Number: " + previousBill.getBillNumber());
                lastBillTotalLabel.setText("Last Bill Total: $" + String.format("%.2f", previousBill.calculateTotalAmount()));

                tableModel.setRowCount(0);  // Clear the table
                tableScrollPane.setVisible(false);
                billInfoPanel.setVisible(false);

                generateNewBill();  // Prepare for the next bill
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String billNumber = searchField.getText();
                if (billHistory.containsKey(billNumber)) {
                    Bill foundBill = billHistory.get(billNumber);
                    JOptionPane.showMessageDialog(TaxCalculationBillingSystem.this,
                            foundBill.toString(),
                            "Bill Details", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(TaxCalculationBillingSystem.this,
                            "Bill not found!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        generateNewBill();  // Initialize the first bill
    }

    // Generate new bill with random bill number
    private void generateNewBill() {
        String billNumber = "BILL-" + new Random().nextInt(10000);
        currentBill = new Bill(billNumber);
        updateBillInfo();
    }

    // Save the bill to the bill history
    private void saveBill(Bill bill) {
        billHistory.put(bill.getBillNumber(), bill);
    }

    // Update the displayed information about the current bill and total bills
    private void updateBillInfo() {
        billCountLabel.setText("Total Bills: " + billHistory.size());
        currentBillLabel.setText("Current Bill: " + currentBill.getBillNumber());
        totalAmountLabel.setText("Total Amount: $" + String.format("%.2f", currentBill.calculateTotalAmount()));
        totalBillCountLabel.setText("Total Number of Bills Generated: " + billHistory.size());
        totalSalesLabel.setText("Total Sales: $" + String.format("%.2f", totalSales));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaxCalculationBillingSystem frame = new TaxCalculationBillingSystem();
            frame.setVisible(true);
        });
    }
}
