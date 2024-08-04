package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Model.User;
import Model.Transaction;
import Service.UserService;

public class BankingUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private UserService userService;

    public BankingUI() {
        userService = new UserService(); // Initialize UserService
        frame = new JFrame("Online Banking System");
        panel = new JPanel();

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);

        // Set panel background color to blue
        panel.setBackground(Color.ORANGE);

        JLabel userLabel = new JLabel("Username");
        userLabel.setBounds(10, 20, 80, 25);
        userLabel.setForeground(Color.BLACK); // Set label text color to black

        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        passwordLabel.setForeground(Color.BLACK); // Set label text color to black
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        loginButton.setBackground(Color.DARK_GRAY); // Set button background color
        loginButton.setForeground(Color.WHITE); // Set button text color
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(100, 80, 120, 25);

        panel.add(registerButton);

        // Add ActionListener for loginButton
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User user = userService.login(username, password); // Call login method
                if (user != null) {
                    JOptionPane.showMessageDialog(frame, "Login Successful");
                    showDashboard(user); // Show the dashboard
                    frame.dispose(); // Close the login window
                } else {
                    JOptionPane.showMessageDialog(frame, "Login Failed");
                }
            }
        });

        // Add ActionListener for registerButton
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Username and Password must be filled out");
                    return;
                }
                String fullName = JOptionPane.showInputDialog(frame, "Enter Full Name:");
                if (fullName == null || fullName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Full Name must be filled out");
                    return;
                }
                User user = new User(username, password); // Use parameterized constructor
                user.setFullName(fullName); // Set full name
                userService.register(user); // Call register method
                JOptionPane.showMessageDialog(frame, "Registration Successful");
            }
        });

        frame.setVisible(true);
    }

    public void showDashboard(User user) {
        JFrame dashboardFrame = new JFrame("User Dashboard");
        dashboardFrame.setSize(600, 400);
        dashboardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashboardFrame.setLayout(new FlowLayout());

        // Set the background color of the dashboard frame to gray
        dashboardFrame.getContentPane().setBackground(Color.GRAY);

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFullName());
        welcomeLabel.setForeground(Color.BLACK); // Set label text color to black
        dashboardFrame.add(welcomeLabel);

        JButton accountButton = new JButton("Account Management");
        accountButton.setBackground(new Color(70, 130, 180)); // Steel blue color
        accountButton.setForeground(Color.WHITE); // Set button text color to white
        dashboardFrame.add(accountButton);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBackground(new Color(100, 149, 237)); // Cornflower blue color
        depositButton.setForeground(Color.WHITE); // Set button text color to white
        dashboardFrame.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBackground(new Color(30, 144, 255)); // Dodger blue color
        withdrawButton.setForeground(Color.WHITE); // Set button text color to white
        dashboardFrame.add(withdrawButton);

        JButton transferButton = new JButton("Fund Transfer");
        transferButton.setBackground(new Color(0, 191, 255)); // Deep sky blue color
        transferButton.setForeground(Color.WHITE); // Set button text color to white
        dashboardFrame.add(transferButton);

        JButton statementButton = new JButton("Mini Statement");
        statementButton.setBackground(new Color(70, 130, 180)); // Steel blue color
        statementButton.setForeground(Color.WHITE); // Set button text color to white
        dashboardFrame.add(statementButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(0, 0, 255)); // Blue color
        logoutButton.setForeground(Color.WHITE); // Set button text color to white
        dashboardFrame.add(logoutButton);

        // ActionListener for Account Management button
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAccountManagement(user); // Show account management screen
            }
        });

        // ActionListener for Deposit button
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog(dashboardFrame, "Enter amount to deposit:");
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount > 0) {
                        userService.deposit(user, amount);
                        JOptionPane.showMessageDialog(dashboardFrame, "Deposited $" + amount);
                    } else {
                        JOptionPane.showMessageDialog(dashboardFrame, "Amount must be greater than zero.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dashboardFrame, "Invalid amount. Please enter a valid number.");
                }
            }
        });

        // ActionListener for Withdraw button
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String amountStr = JOptionPane.showInputDialog(dashboardFrame, "Enter amount to withdraw:");
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount > 0) {
                        if (userService.withdraw(user, amount)) {
                            JOptionPane.showMessageDialog(dashboardFrame, "Withdrawn $" + amount);
                        } else {
                            JOptionPane.showMessageDialog(dashboardFrame, "Insufficient funds.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(dashboardFrame, "Amount must be greater than zero.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dashboardFrame, "Invalid amount. Please enter a valid number.");
                }
            }
        });

        // ActionListener for Fund Transfer button
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String receiverUsername = JOptionPane.showInputDialog(dashboardFrame, "Enter receiver's username:");
                User receiver = userService.getUser(receiverUsername);
                if (receiver == null) {
                    JOptionPane.showMessageDialog(dashboardFrame, "Receiver not found.");
                    return;
                }
                String amountStr = JOptionPane.showInputDialog(dashboardFrame, "Enter amount to transfer:");
                try {
                    double amount = Double.parseDouble(amountStr);
                    if (amount > 0) {
                        if (userService.transfer(user, receiver, amount)) {
                            JOptionPane.showMessageDialog(dashboardFrame, "Transferred $" + amount);
                        } else {
                            JOptionPane.showMessageDialog(dashboardFrame, "Insufficient funds.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(dashboardFrame, "Amount must be greater than zero.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dashboardFrame, "Invalid amount. Please enter a valid number.");
                }
            }
        });

        // ActionListener for Mini Statement button
        statementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMiniStatement(user);
            }
        });

        // ActionListener for Logout button
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboardFrame.dispose(); // Close the dashboard
                new BankingUI(); // Return to the login screen
            }
        });

        dashboardFrame.setVisible(true);
    }

    public void showAccountManagement(User user) {
        JFrame accountFrame = new JFrame("Account Management");
        accountFrame.setSize(600, 400);
        accountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accountFrame.setLayout(new FlowLayout());

        JLabel accountDetails = new JLabel("Account Details for " + user.getFullName());
        accountFrame.add(accountDetails);

        // Add more components like fields for updating details, buttons for saving
        // changes

        JButton backButton = new JButton("Back to Dashboard");
        accountFrame.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountFrame.dispose(); // Close the account management screen
                showDashboard(user); // Return to the dashboard
            }
        });

        accountFrame.setVisible(true);
    }

    public void showMiniStatement(User user) {
        JFrame statementFrame = new JFrame("Mini Statement");
        statementFrame.setSize(600, 400);
        statementFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        statementFrame.setLayout(new FlowLayout());

        List<Transaction> transactions = user.getTransactions();
        if (transactions.isEmpty()) {
            statementFrame.add(new JLabel("No transactions available."));
        } else {
            for (Transaction transaction : transactions) {
                JLabel transactionLabel = new JLabel(
                        transaction.getDate() + " - " + transaction.getType() + ": $" + transaction.getAmount());
                transactionLabel.setForeground(Color.BLACK); // Set label text color to black
                statementFrame.add(transactionLabel);
            }
        }

        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBackground(Color.BLUE); // Set button background color
        backButton.setForeground(Color.WHITE); // Set button text color
        statementFrame.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statementFrame.dispose(); // Close the mini statement screen
                showDashboard(user); // Return to the dashboard
            }
        });

        statementFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new BankingUI();
    }
}