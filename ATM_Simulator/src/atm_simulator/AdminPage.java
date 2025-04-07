package atm_simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminPage implements ActionListener {

    JFrame f;
    JButton truncateUsersBtn, truncateTransactionsBtn, dropUsersBtn, dropTransactionsBtn, exitBtn;
    JButton editUserBtn;

    AdminPage() {
        f = new JFrame("Admin Dashboard");
        f.setSize(1000, 700);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(Color.WHITE);

        Font sectionFont = new Font("Arial", Font.BOLD, 22);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        // Truncate section
        JLabel truncateLabel = new JLabel("Truncate Table");
        truncateLabel.setFont(sectionFont);
        truncateLabel.setBounds(150, 40, 200, 30);
        f.add(truncateLabel);

        truncateUsersBtn = new JButton("Truncate Users");
        truncateUsersBtn.setBounds(150, 90, 300, 45);
        truncateUsersBtn.setFont(buttonFont);
        truncateUsersBtn.setBackground(new Color(66, 133, 244)); // Blue
        truncateUsersBtn.setForeground(Color.WHITE);
        truncateUsersBtn.addActionListener(this);
        f.add(truncateUsersBtn);

        truncateTransactionsBtn = new JButton("Truncate Transactions");
        truncateTransactionsBtn.setBounds(150, 150, 300, 45);
        truncateTransactionsBtn.setFont(buttonFont);
        truncateTransactionsBtn.setBackground(new Color(66, 133, 244)); // Blue
        truncateTransactionsBtn.setForeground(Color.WHITE);
        truncateTransactionsBtn.addActionListener(this);
        f.add(truncateTransactionsBtn);

        // Drop section
        JLabel dropLabel = new JLabel("Drop Table");
        dropLabel.setFont(sectionFont);
        dropLabel.setBounds(550, 40, 200, 30);
        f.add(dropLabel);

        dropUsersBtn = new JButton("Drop Users Table");
        dropUsersBtn.setBounds(550, 90, 300, 45);
        dropUsersBtn.setFont(buttonFont);
        dropUsersBtn.setBackground(new Color(219, 68, 55)); // Red
        dropUsersBtn.setForeground(Color.WHITE);
        dropUsersBtn.addActionListener(this);
        f.add(dropUsersBtn);

        dropTransactionsBtn = new JButton("Drop Transactions Table");
        dropTransactionsBtn.setBounds(550, 150, 300, 45);
        dropTransactionsBtn.setFont(buttonFont);
        dropTransactionsBtn.setBackground(new Color(219, 68, 55)); // Red
        dropTransactionsBtn.setForeground(Color.WHITE);
        dropTransactionsBtn.addActionListener(this);
        f.add(dropTransactionsBtn);

        // Edit user
        editUserBtn = new JButton("Edit User Balance");
        editUserBtn.setBounds(375, 280, 250, 50);
        editUserBtn.setFont(buttonFont);
        editUserBtn.setBackground(new Color(244, 180, 0)); // Yellow
        editUserBtn.setForeground(Color.BLACK);
        editUserBtn.addActionListener(this);
        f.add(editUserBtn);

        // Exit
        exitBtn = new JButton("Exit");
        exitBtn.setBounds(400, 550, 200, 50);
        exitBtn.setFont(buttonFont);
        exitBtn.setBackground(Color.DARK_GRAY);
        exitBtn.setForeground(Color.WHITE);
        exitBtn.addActionListener(this);
        f.add(exitBtn);

        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            if (e.getSource() == truncateUsersBtn) {
                stmt.executeUpdate("TRUNCATE TABLE users");
                JOptionPane.showMessageDialog(f, "Users table truncated.");
            } else if (e.getSource() == truncateTransactionsBtn) {
                stmt.executeUpdate("TRUNCATE TABLE transactions");
                JOptionPane.showMessageDialog(f, "Transactions table truncated.");
            } else if (e.getSource() == dropUsersBtn) {
                stmt.executeUpdate("DROP TABLE IF EXISTS users");
                JOptionPane.showMessageDialog(f, "Users table dropped.");
            } else if (e.getSource() == dropTransactionsBtn) {
                stmt.executeUpdate("DROP TABLE IF EXISTS transactions");
                JOptionPane.showMessageDialog(f, "Transactions table dropped.");
            } else if (e.getSource() == editUserBtn) {
                editUserBalance(conn);
            } else if (e.getSource() == exitBtn) {
                f.dispose();
                new Dashboard();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(f, "Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void editUserBalance(Connection conn) {
        String card = JOptionPane.showInputDialog(f, "Enter Card Number:");
        String newBalance = JOptionPane.showInputDialog(f, "Enter New Balance:");
        try (PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET balance = ? WHERE card_number = ?")) {
            pstmt.setDouble(1, Double.parseDouble(newBalance));
            pstmt.setString(2, card);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(f, "Balance updated.");
            } else {
                JOptionPane.showMessageDialog(f, "Card number not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(f, "Error: " + ex.getMessage());
        }
    }
}
