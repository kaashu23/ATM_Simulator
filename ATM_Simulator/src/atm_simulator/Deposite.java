package atm_simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Deposite implements ActionListener {

    JFrame f;
    JLabel balanceLabel;
    JTextField amountField;
    JButton submitButton, exitButton;

    String cardNumber;
    double currentBalance = 0.0;

    public Deposite(String cardNumber) {
        this.cardNumber = cardNumber;

        f = new JFrame("ATM Simulator - Deposit");
        f.setSize(1000, 700);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("Deposit Money");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setBounds(380, 50, 300, 40);
        f.add(title);

        balanceLabel = new JLabel("Current Balance: ₹0.00");
        balanceLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        balanceLabel.setBounds(350, 120, 400, 30);
        f.add(balanceLabel);

        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setFont(new Font("Arial", Font.BOLD, 20));
        amountLabel.setBounds(300, 200, 200, 30);
        f.add(amountLabel);

        amountField = new JTextField();
        amountField.setFont(new Font("Arial", Font.PLAIN, 18));
        amountField.setBounds(500, 200, 200, 35);
        f.add(amountField);

        submitButton = new JButton("Submit");
        submitButton.setBounds(350, 300, 130, 50);
        submitButton.setBackground(new Color(0, 128, 0));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.addActionListener(this);
        f.add(submitButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(520, 300, 130, 50);
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.addActionListener(this);
        f.add(exitButton);

        fetchBalance();
        f.setVisible(true);
    }

    void fetchBalance() {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT balance FROM users WHERE card_number = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                currentBalance = rs.getDouble("balance");
                balanceLabel.setText("Current Balance: ₹" + currentBalance);
            } else {
                JOptionPane.showMessageDialog(f, "Account not found!");
                f.dispose();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String amountText = amountField.getText();

            if (amountText.isEmpty()) {
                JOptionPane.showMessageDialog(f, "Enter amount to deposit");
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(f, "Enter a valid number");
                return;
            }
            if (amount <= 0) {
                JOptionPane.showMessageDialog(f, "Amount must be greater than zero.");
                return;
            }
            try {
                Connection conn = DBConnection.getConnection();

                String updateQuery = "UPDATE users SET balance = balance + ? WHERE card_number = ?";
                PreparedStatement pstmt = conn.prepareStatement(updateQuery);
                pstmt.setDouble(1, amount);
                pstmt.setString(2, cardNumber);
                pstmt.executeUpdate();

                String insertQuery = "INSERT INTO transactions (card_number, type, amount) VALUES (?, 'Deposit', ?)";
                PreparedStatement pstmt2 = conn.prepareStatement(insertQuery);
                pstmt2.setString(1, cardNumber);
                pstmt2.setDouble(2, amount);
                pstmt2.executeUpdate();

                JOptionPane.showMessageDialog(f, "₹" + amount + " deposited successfully!");
                fetchBalance();
                amountField.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == exitButton) {
            f.dispose();
            new Dashboard();
        }
    }
}
