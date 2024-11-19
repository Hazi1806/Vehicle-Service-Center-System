import java.io.*;
import java.util.List;
import java.util.Stack;

public class CompleteStack {
    private Stack<Transaction> transactions = new Stack<>();

    public void addTransaction(String customerName, double totalCost) {
        transactions.push(new Transaction(customerName, totalCost));
    }

    public Stack<Transaction> getTransaction() {
        return transactions;
    }
    
    public List<Transaction> getTransactions()
    {
        return transactions;
    }
    
    public static class Transaction {
        private String customerName;
        private double totalCost;

        public Transaction(String customerName, double totalCost) {
            this.customerName = customerName;
            this.totalCost = totalCost;
        }

        public String getCustomerName() {
            return customerName;
        }

        public double getTotalCost() {
            return totalCost;
        }

        @Override
        public String toString() {
            return "Customer: " + customerName + ", Total Cost: RM " + totalCost;
        }
    }

    public void displayCompletedTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No completed transactions available.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader("CustomersLists.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 2) {
                    String customerName = tokens[0].trim();
                    double totalCost = Double.parseDouble(tokens[1].trim());
                    transactions.push(new Transaction(customerName, totalCost));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}