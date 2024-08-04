package Model;

import java.sql.Timestamp;
import Model.Transaction;


public class Transaction {
    private String type; // Ensure this field is correctly defined
    private String date; 
    private int transactionID;
    private int accountID;
    private double amount;
    private String transactionType;
    private Timestamp transactionDate;
    

    // Constructor
    public Transaction(int transactionID, int accountID, double amount, String transactionType, Timestamp transactionDate) {
        this.transactionID = transactionID;
        this.accountID = accountID;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
    }

    // Getter for type
    public String getType() {
        return type;
    }
    // Getter for date
    public String getDate() {
        return date;
    }

    // Getters and setters
    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    
}