package ca3ProductManagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProductManagementApp {
    private ArrayList<Product> productList;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextArea resultTextArea;
    private JTextField searchField;
    private JLabel averageLabel;

    public ProductManagementApp() {
        productList = new ArrayList<>();

        JFrame frame = new JFrame("Catalogue Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel productDetailsPanel = createProductDetailsPanel();
        JPanel searchByCategoryPanel = createSearchByCategoryPanel();

        tabbedPane.addTab("Product Details", productDetailsPanel);
        tabbedPane.addTab("Search by Category", searchByCategoryPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private JPanel createProductDetailsPanel() {
        JPanel productDetailsPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField codeField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        String[] categories = {"Electronic", "Gardening Deco", "Hardware Accessories", "Home Deco", "Plumbing"};
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);
        JTextField priceField = new JTextField(10);
        JButton addButton = new JButton("Add Product");

        inputPanel.add(new JLabel("Code:"));
        inputPanel.add(codeField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryComboBox);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(addButton);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Code");
        tableModel.addColumn("Name");
        tableModel.addColumn("Category");
        tableModel.addColumn("Price");

        productTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);

        productDetailsPanel.add(inputPanel, BorderLayout.NORTH);
        productDetailsPanel.add(tableScrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = codeField.getText();
                String name = nameField.getText();
                String category = categoryComboBox.getSelectedItem().toString();
                double price = Double.parseDouble(priceField.getText());

                Product product = new Product(code, name, category, price);
                addProductToTable(product);

                codeField.setText("");
                nameField.setText("");
                priceField.setText("");
            }
        });

        return productDetailsPanel;
    }

    private JPanel createSearchByCategoryPanel() {
        JPanel searchByCategoryPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        JButton clearButton = new JButton("Clear Search");

        searchPanel.add(new JLabel("Search Category:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        resultTextArea = new JTextArea(10, 20);
        resultTextArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultTextArea);

        averageLabel = new JLabel("Average Value: ");
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(averageLabel);

        searchByCategoryPanel.add(searchPanel, BorderLayout.NORTH);
        searchByCategoryPanel.add(resultScrollPane, BorderLayout.CENTER);
        searchByCategoryPanel.add(bottomPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchCategory = searchField.getText();
                ArrayList<Product> foundProducts = searchByCategory(searchCategory);
                displaySearchResults(foundProducts);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchField.setText("");
                resultTextArea.setText("");
                averageLabel.setText("Average Value: ");
            }
        });

        return searchByCategoryPanel;
    }

    private void addProductToTable(Product product) {
        Object[] rowData = {product.getCode(), product.getName(), product.getCategory(), product.getPrice()};
        tableModel.addRow(rowData);
        productList.add(product);
    }

    private ArrayList<Product> searchByCategory(String category) {
        ArrayList<Product> foundProducts = new ArrayList<>();
        for (Product product : productList) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    private void displaySearchResults(ArrayList<Product> products) {
        if (products.isEmpty()) {
            resultTextArea.setText("No products found for this category.");
            averageLabel.setText("Average Value: ");
            return;
        }

        StringBuilder sb = new StringBuilder();
        double totalPrice = 0.0;

        for (Product product : products) {
            sb.append("Code: ").append(product.getCode()).append(", Name: ").append(product.getName())
                    .append(", Category: ").append(product.getCategory()).append(", Price: ").append(product.getPrice())
                    .append("\n");
            totalPrice += product.getPrice();
        }

        double averagePrice = totalPrice / products.size();
        sb.append("\nAverage Price: ").append(averagePrice);

        resultTextArea.setText(sb.toString());
        averageLabel.setText("Average Value: " + averagePrice);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProductManagementApp();
            }
        });
    }

    
    private class Product {
        private String code;
        private String name;
        private String category;
        private double price;

        public Product(String code, String name, String category, double price) {
            this.code = code;
            this.name = name;
            this.category = category;
            this.price = price;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public double getPrice() {
            return price;
        }
    }
}





