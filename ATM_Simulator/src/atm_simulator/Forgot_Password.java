package atm_simulator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Forgot_Password implements ActionListener {

    private JFrame f;
    private JLabel cardLabel, nameLabel, passLabel, confirmLabel;
    private JTextField cardField, nameField;
    private JPasswordField passField, confirmField;
    private JButton checkButton, submitButton, exitButton;

    public Forgot_Password() {
        f = new JFrame("ATM Simulator - Forgot Password");
        f.setSize(1000, 700);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(Color.WHITE);

        // Card number
        cardLabel = new JLabel("Enter Card Number:");
        cardLabel.setBounds(300, 150, 200, 30);
        cardLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        f.add(cardLabel);

        cardField = new JTextField();
        cardField.setBounds(500, 150, 200, 30);
        f.add(cardField);

        // Name
        nameLabel = new JLabel("Enter Your Name:");
        nameLabel.setBounds(300, 200, 200, 30);
        nameLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        f.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(500, 200, 200, 30);
        f.add(nameField);

        // Check button
        checkButton = new JButton("Check");
        checkButton.setBounds(400, 260, 150, 40);
        checkButton.setBackground(Color.BLACK);
        checkButton.setForeground(Color.WHITE);
        checkButton.setFont(new Font("Verdana", Font.BOLD, 14));
        checkButton.addActionListener(this);
        f.add(checkButton);

        // Exit button
        exitButton = new JButton("Exit");
        exitButton.setBounds(570, 260, 100, 40);
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Verdana", Font.BOLD, 14));
        exitButton.addActionListener(this);
        f.add(exitButton);

        // Password fields (hidden initially)
        passLabel = new JLabel("Enter New Password:");
        passLabel.setBounds(300, 330, 200, 30);
        passLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        passLabel.setVisible(false);
        f.add(passLabel);

        passField = new JPasswordField();
        passField.setBounds(500, 330, 200, 30);
        passField.setVisible(false);
        f.add(passField);

        confirmLabel = new JLabel("Confirm Password:");
        confirmLabel.setBounds(300, 380, 200, 30);
        confirmLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        confirmLabel.setVisible(false);
        f.add(confirmLabel);

        confirmField = new JPasswordField();
        confirmField.setBounds(500, 380, 200, 30);
        confirmField.setVisible(false);
        f.add(confirmField);

        // Submit button
        submitButton = new JButton("Submit");
        submitButton.setBounds(400, 440, 150, 40);
        submitButton.setBackground(Color.BLUE);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Verdana", Font.BOLD, 14));
        submitButton.setVisible(false);
        submitButton.addActionListener(this);
        f.add(submitButton);

        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == checkButton) {
            String card = cardField.getText();
            String name = nameField.getText();

            try {
                Connection conn = DBConnection.getConnection();
                String query = "SELECT * FROM users WHERE card_number = ? AND name = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, card);
                pstmt.setString(2, name);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    passLabel.setVisible(true);
                    passField.setVisible(true);
                    confirmLabel.setVisible(true);
                    confirmField.setVisible(true);
                    submitButton.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "No matching user found.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == submitButton) {
            String card = cardField.getText();
            String newPass = String.valueOf(passField.getPassword());
            String confirmPass = String.valueOf(confirmField.getPassword());

            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(f, "Passwords do not match!");
            } else {
                try {
                    Connection conn = DBConnection.getConnection();
                    String query = "UPDATE users SET pin = ? WHERE card_number = ?";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, newPass);
                    pstmt.setString(2, card);

                    int rowsUpdated = pstmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(f, "Password successfully updated!");
                    } else {
                        JOptionPane.showMessageDialog(f, "Card number not found. Password not updated.");
                    }
                    pstmt.close();
                    conn.close();
                    f.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                f.dispose();
                new Dashboard();
            }
        } else if (e.getSource() == exitButton) {
            f.dispose();
            new Dashboard();
        }
    }
}
