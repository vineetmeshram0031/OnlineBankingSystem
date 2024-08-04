package Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Account;
import Model.Transaction;

public class AccountService {
    public void createAccount(Account account) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Accounts (userID, balance) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, account.getUserID());
            stmt.setDouble(2, account.getBalance());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transferFunds(int fromAccountID, int toAccountID, double amount) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String withdrawQuery = "UPDATE Accounts SET balance = balance - ? WHERE accountID = ?";
            PreparedStatement withdrawStmt = conn.prepareStatement(withdrawQuery);
            withdrawStmt.setDouble(1, amount);
            withdrawStmt.setInt(2, fromAccountID);
            withdrawStmt.executeUpdate();

            String depositQuery = "UPDATE Accounts SET balance = balance + ? WHERE accountID = ?";
            PreparedStatement depositStmt = conn.prepareStatement(depositQuery);
            depositStmt.setDouble(1, amount);
            depositStmt.setInt(2, toAccountID);
            depositStmt.executeUpdate();

            String transactionQuery = "INSERT INTO Transactions (accountID, amount, transactionType) VALUES (?, ?, ?)";
            PreparedStatement transactionStmt = conn.prepareStatement(transactionQuery);
            transactionStmt.setInt(1, fromAccountID);
            transactionStmt.setDouble(2, -amount);
            transactionStmt.setString(3, "Transfer Out");
            transactionStmt.executeUpdate();

            transactionStmt.setInt(1, toAccountID);
            transactionStmt.setDouble(2, amount);
            transactionStmt.setString(3, "Transfer In");
            transactionStmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    public List<Transaction> getTransactionHistory(int accountID) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Transactions WHERE accountID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getInt("transactionID"),
                    rs.getInt("accountID"),
                    rs.getDouble("amount"),
                    rs.getString("transactionType"),
                    rs.getTimestamp("transactionDate")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}    