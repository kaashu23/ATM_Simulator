package atm_simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Transaction_History implements ActionListener {

    JFrame f;
    JTextArea historyArea;
    JButton exitButton;
    String cardNumber;

    public Transaction_History(String cardNumber) {
        this.cardNumber = cardNumber;

        f = new JFrame("ATM Simulator - Transaction History");
        f.setSize(1000, 700);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("Full Transaction History");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBounds(330, 30, 400, 30);
        f.add(title);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        historyArea.setMargin(new Insets(10, 20, 10, 20));

        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBounds(100, 100, 800, 450);
        f.add(scrollPane);

        exitButton = new JButton("Exit");
        exitButton.setBounds(440, 580, 120, 40);
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.addActionListener(this); // Register listener
        f.add(exitButton);

        fetchTransactionHistory();
        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            new Dashboard();
            f.dispose();
        }
    }

    void fetchTransactionHistory() {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT type, amount, timestamp FROM transactions WHERE card_number = ? ORDER BY timestamp DESC";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, cardNumber);
            ResultSet rs = pstmt.executeQuery();

            historyArea.setText("\tType\t\tAmount\t\t\tDate & Time\n");
            historyArea.append("\t-------------------------------------------------------------\n");

            while (rs.next()) {
                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                Timestamp time = rs.getTimestamp("timestamp");

                if (type.length() < 8) {
                    historyArea.append("\t" + type + "\t\t₹" + amount + "\t\t" + time + "\n");
                } else {
                    historyArea.append("\t" + type + "\t₹" + amount + "\t\t" + time + "\n");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            historyArea.setText("\tFailed to load transaction history.");
        }
    }
}
