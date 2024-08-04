package Service;

import Model.User;
import Model.Transaction;
import Service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.sql.Timestamp;

public class UserService {
    private Map<String, User> users;

    public UserService() {
        users = new HashMap<>();
    }

    // Register a user
    public void register(User user) {
        if (!users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
        }
    }

    // Login a user
    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    // Get a user by username
    public User getUser(String username) {
        return users.get(username);
    }

    // Get the balance of a user
    public double getBalance(User user) {
        return user.getBalance();
    }

    // Deposit money into a user's account
    public void deposit(User user, double amount) {
        if (amount > 0) {
            double newBalance = getBalance(user) + amount;
            user.setBalance(newBalance);
            user.addTransaction(new Transaction(0, 0, amount, "Deposit", new Timestamp(System.currentTimeMillis())));
        } else {
            // Handle invalid deposit amount
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Withdraw money from a user's account
    public boolean withdraw(User user, double amount) {
        if (amount > 0 && getBalance(user) >= amount) {
            double newBalance = getBalance(user) - amount;
            user.setBalance(newBalance);
            user.addTransaction(new Transaction(0, 0, amount, "Withdraw", new Timestamp(System.currentTimeMillis())));
            return true;
        } else {
            // Handle insufficient funds or invalid amount
            System.out.println("Insufficient funds or invalid withdrawal amount.");
            return false;
        }
    }

    // Transfer money between users
    public boolean transfer(User sender, User receiver, double amount) {
        if (withdraw(sender, amount)) {
            deposit(receiver, amount);
            sender.addTransaction(new Transaction(0, 0, amount, "Transfer Out", new Timestamp(System.currentTimeMillis())));
            receiver.addTransaction(new Transaction(0, 0, amount, "Transfer In", new Timestamp(System.currentTimeMillis())));
            return true;
        }
        return false;
    }
}