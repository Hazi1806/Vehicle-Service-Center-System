import java.io.*;
import java.util.List;
import java.util.Stack;

// Class to manage a stack of completed transactions
public class CompleteStack {
    // Stack to store transactions
    private Stack<Transaction> transactions = new Stack<>();

    // Method to add a new transaction to the stack
    public void addTransaction(String customerName, double totalCost) {
        transactions.push(new Transaction(customerName, totalCost));
    }

    // Method to get the stack of transactions
    public Stack<Transaction> getTransaction() {
        return transactions;
    }
    
    // Method to get the transactions as a list
    public List<Transaction> getTransactions() {
        return transactions;
    }
    
    // Inner class to represent a single transaction
    public static class Transaction {
        private String customerName; // Name of the customer
        private double totalCost;    // Total cost of the transaction

        // Constructor for Transaction
        public Transaction(String customerName, double totalCost) {
            this.customerName = customerName;
            this.totalCost = totalCost;
        }

        // Getter for customer name
        public String getCustomerName() {
            return customerName;
        }

        // Getter for total cost
        public double getTotalCost() {
            return totalCost;
        }

        // String representation of the transaction
        @Override
        public String toString() {
            return "Customer: " + customerName + ", Total Cost: RM " + totalCost;
        }
    }

    // Method to display all completed transactions
    public void displayCompletedTransactions() {
        if (transactions.isEmpty()) { // Check if the stack is empty
            System.out.println("No completed transactions available.");
            return;
        }
        // Iterate through and print each transaction
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    // Method to load transactions from a file
    public void loadFromFile(String filename) {
        // Read file and parse transactions
        try (BufferedReader reader = new BufferedReader(new FileReader("CustomersLists.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) { // Read each line
                String[] tokens = line.split(","); // Split line by comma
                if (tokens.length == 2) { // Ensure valid format
                    String customerName = tokens[0].trim(); // Get customer name
                    double totalCost = Double.parseDouble(tokens[1].trim()); // Get total cost
                    transactions.push(new Transaction(customerName, totalCost)); // Add to stack
                }
            }
        } catch (IOException e) {
            // Handle file reading errors
            e.printStackTrace();
        }
    }
}
