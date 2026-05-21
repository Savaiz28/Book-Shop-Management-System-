import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookshopManagementSystem extends JFrame {

    // Tables
    DefaultTableModel bookModel;
    DefaultTableModel customerModel;
    DefaultTableModel feedbackModel;

    JTable bookTable, customerTable, feedbackTable;

    // File Names
    final String BOOK_FILE = "books.txt";
    final String CUSTOMER_FILE = "customers.txt";
    final String FEEDBACK_FILE = "feedback.txt";

    public BookshopManagementSystem() {

        setTitle("Bookshop Sales and Customer Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        // ================= BOOK PANEL =================
        JPanel bookPanel = new JPanel(new BorderLayout());

        bookModel = new DefaultTableModel();
        bookModel.setColumnIdentifiers(new String[]{"Book ID", "Title", "Author", "Price"});

        bookTable = new JTable(bookModel);

        JPanel bookInputPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        JTextField bookIdField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField priceField = new JTextField();

        JButton addBookBtn = new JButton("Add Book");

        bookInputPanel.add(new JLabel("Book ID"));
        bookInputPanel.add(bookIdField);

        bookInputPanel.add(new JLabel("Title"));
        bookInputPanel.add(titleField);

        bookInputPanel.add(new JLabel("Author"));
        bookInputPanel.add(authorField);

        bookInputPanel.add(new JLabel("Price"));
        bookInputPanel.add(priceField);

        bookInputPanel.add(new JLabel());
        bookInputPanel.add(addBookBtn);

        addBookBtn.addActionListener(e -> {

            String id = bookIdField.getText();
            String title = titleField.getText();
            String author = authorField.getText();
            String price = priceField.getText();

            if (id.isEmpty() || title.isEmpty() || author.isEmpty() || price.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }

            bookModel.addRow(new Object[]{id, title, author, price});

            saveBooks();

            bookIdField.setText("");
            titleField.setText("");
            authorField.setText("");
            priceField.setText("");
        });

        bookPanel.add(bookInputPanel, BorderLayout.NORTH);
        bookPanel.add(new JScrollPane(bookTable), BorderLayout.CENTER);

        // ================= CUSTOMER PANEL =================
        JPanel customerPanel = new JPanel(new BorderLayout());

        customerModel = new DefaultTableModel();
        customerModel.setColumnIdentifiers(new String[]{"Customer ID", "Name", "Phone"});

        customerTable = new JTable(customerModel);

        JPanel customerInputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        JTextField customerIdField = new JTextField();
        JTextField customerNameField = new JTextField();
        JTextField customerPhoneField = new JTextField();

        JButton addCustomerBtn = new JButton("Add Customer");

        customerInputPanel.add(new JLabel("Customer ID"));
        customerInputPanel.add(customerIdField);

        customerInputPanel.add(new JLabel("Name"));
        customerInputPanel.add(customerNameField);

        customerInputPanel.add(new JLabel("Phone"));
        customerInputPanel.add(customerPhoneField);

        customerInputPanel.add(new JLabel());
        customerInputPanel.add(addCustomerBtn);

        addCustomerBtn.addActionListener(e -> {

            String id = customerIdField.getText();
            String name = customerNameField.getText();
            String phone = customerPhoneField.getText();

            if (id.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }

            customerModel.addRow(new Object[]{id, name, phone});

            saveCustomers();

            customerIdField.setText("");
            customerNameField.setText("");
            customerPhoneField.setText("");
        });

        customerPanel.add(customerInputPanel, BorderLayout.NORTH);
        customerPanel.add(new JScrollPane(customerTable), BorderLayout.CENTER);

        // ================= FEEDBACK PANEL =================
        JPanel feedbackPanel = new JPanel(new BorderLayout());

        feedbackModel = new DefaultTableModel();
        feedbackModel.setColumnIdentifiers(new String[]{"Customer Name", "Feedback"});

        feedbackTable = new JTable(feedbackModel);

        JPanel feedbackInputPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        JTextField feedbackNameField = new JTextField();
        JTextField feedbackField = new JTextField();

        JButton addFeedbackBtn = new JButton("Add Feedback");

        feedbackInputPanel.add(new JLabel("Customer Name"));
        feedbackInputPanel.add(feedbackNameField);

        feedbackInputPanel.add(new JLabel("Feedback"));
        feedbackInputPanel.add(feedbackField);

        feedbackInputPanel.add(new JLabel());
        feedbackInputPanel.add(addFeedbackBtn);

        addFeedbackBtn.addActionListener(e -> {

            String name = feedbackNameField.getText();
            String feedback = feedbackField.getText();

            if (name.isEmpty() || feedback.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }

            feedbackModel.addRow(new Object[]{name, feedback});

            saveFeedback();

            feedbackNameField.setText("");
            feedbackField.setText("");
        });

        feedbackPanel.add(feedbackInputPanel, BorderLayout.NORTH);
        feedbackPanel.add(new JScrollPane(feedbackTable), BorderLayout.CENTER);

        // ================= SALES REPORT PANEL =================
        JPanel salesPanel = new JPanel(new BorderLayout());

        JTextArea salesArea = new JTextArea();
        salesArea.setEditable(false);

        JButton generateReportBtn = new JButton("Generate Sales Report");

        generateReportBtn.addActionListener(e -> {

            StringBuilder report = new StringBuilder();

            report.append("===== SALES REPORT =====\n\n");
            report.append("Total Books: ").append(bookModel.getRowCount()).append("\n");
            report.append("Total Customers: ").append(customerModel.getRowCount()).append("\n");
            report.append("Total Feedbacks: ").append(feedbackModel.getRowCount()).append("\n");

            salesArea.setText(report.toString());
        });

        salesPanel.add(generateReportBtn, BorderLayout.NORTH);
        salesPanel.add(new JScrollPane(salesArea), BorderLayout.CENTER);

        // ================= ADD TABS =================
        tabs.add("Books", bookPanel);
        tabs.add("Customers", customerPanel);
        tabs.add("Feedback", feedbackPanel);
        tabs.add("Sales Report", salesPanel);

        add(tabs);

        // Load Existing Data
        loadBooks();
        loadCustomers();
        loadFeedback();

        setVisible(true);
    }

    // ================= SAVE METHODS =================

    private void saveBooks() {

        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOK_FILE))) {

            for (int i = 0; i < bookModel.getRowCount(); i++) {

                pw.println(
                        bookModel.getValueAt(i, 0) + "," +
                        bookModel.getValueAt(i, 1) + "," +
                        bookModel.getValueAt(i, 2) + "," +
                        bookModel.getValueAt(i, 3)
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveCustomers() {

        try (PrintWriter pw = new PrintWriter(new FileWriter(CUSTOMER_FILE))) {

            for (int i = 0; i < customerModel.getRowCount(); i++) {

                pw.println(
                        customerModel.getValueAt(i, 0) + "," +
                        customerModel.getValueAt(i, 1) + "," +
                        customerModel.getValueAt(i, 2)
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFeedback() {

        try (PrintWriter pw = new PrintWriter(new FileWriter(FEEDBACK_FILE))) {

            for (int i = 0; i < feedbackModel.getRowCount(); i++) {

                pw.println(
                        feedbackModel.getValueAt(i, 0) + "," +
                        feedbackModel.getValueAt(i, 1)
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ================= LOAD METHODS =================

    private void loadBooks() {

        try (BufferedReader br = new BufferedReader(new FileReader(BOOK_FILE))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                bookModel.addRow(data);
            }

        } catch (IOException e) {

        }
    }

    private void loadCustomers() {

        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                customerModel.addRow(data);
            }

        } catch (IOException e) {

        }
    }

    private void loadFeedback() {

        try (BufferedReader br = new BufferedReader(new FileReader(FEEDBACK_FILE))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                feedbackModel.addRow(data);
            }

        } catch (IOException e) {

        }
    }

    // ================= MAIN METHOD =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new BookshopManagementSystem();
        });
    }
}