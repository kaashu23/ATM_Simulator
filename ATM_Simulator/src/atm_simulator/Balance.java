package atm_simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Balance implements ActionListener {

    JFrame f;
    JLabel balanceLabel, titleLabel, statementTitle;
    JTextArea miniStatementArea;
    JButton exitButton;

    String cardNumber;
    double currentBalance = 0.0;

    public Balance(String cardNumber) {
        this.cardNumber = cardNumber;

        f = new JFrame("ATM Simulator - Balance & Mini Statement");
        f.setSize(1000, 700);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(Color.WHITE);

        titleLabel = new JLabel("Account Balance & Mini Statement");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(300, 30, 500, 30);
        f.add(titleLabel);

        balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        balanceLabel.setBounds(0, 100, 1000, 30);
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        f.add(balanceLabel);

        // Sub-title for mini statement
        statementTitle = new JLabel("Last 5 Transactions");
        statementTitle.setFont(new Font("Arial", Font.BOLD, 18));
        statementTitle.setBounds(400, 150, 300, 30);
        f.add(statementTitle);

        miniStatementArea = new JTextArea();
        miniStatementArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        miniStatementArea.setEditable(false);
        miniStatementArea.setMargin(new Insets(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(miniStatementArea);
        scrollPane.setBounds(200, 190, 600, 200); // Smaller box
        f.add(scrollPane);

        exitButton = new JButton("Exit");
        exitButton.setBounds(425, 420, 150, 40);
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.addActionListener(this);
        f.add(exitButton);

        fetchBalance();
        fetchMiniStatement();

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
                balanceLabel.setText("Your Current Balance: ₹" + currentBalance);
            } else {
                JOptionPane.showMessageDialog(f, "Account not found!");
                f.dispose();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void fetchMiniStatement() {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT type, amount, timestamp FROM transactions WHERE card_number = ? ORDER BY timestamp DESC LIMIT 5";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();

            miniStatementArea.setText("  Type\t\t\tAmount\t\t\tDate & Time\n");
            miniStatementArea.append("-------------------------------------------------------------\n");

            while (rs.next()) {
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                Timestamp time = rs.getTimestamp("timestamp");

                miniStatementArea.append(String.format("  %-15s ₹%-15.2f %s\n", type, amount, time));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            miniStatementArea.setText("Failed to load mini statement.");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            f.dispose();
            new Dashboard();
        }
    }
}
