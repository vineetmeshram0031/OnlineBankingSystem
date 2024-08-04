package Model;


import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;


public class User {
    private String username;
    private String password;
    private String fullName; // Field for full name
    private double balance;
    private List<Transaction> transactions;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    // Default constructor
    public User() {
        this.transactions = new ArrayList<>();
    }

    // Getter and setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and setter for fullName
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Getter and setter for balance
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Add transaction
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // Get all transactions
    public List<Transaction> getTransactions() {
        return transactions;
    }
}