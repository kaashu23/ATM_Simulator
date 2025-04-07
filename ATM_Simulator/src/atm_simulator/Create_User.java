package atm_simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Create_User implements ActionListener {

    private JFrame frame;
    private JTextField nameField, cardField, mobileField, depositField;
    private JPasswordField pinField, confirmPinField;
    private JButton createButton, cancelButton;

    public Create_User() {
        frame = new JFrame("ATM Simulator - Create New User");
        frame.setSize(1000, 700);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        JLabel title = new JLabel("Create New Account");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.BLUE);
        title.setBounds(350, 40, 400, 30);
        frame.add(title);

        frame.add(makeLabel("User Name (First Name) :", 230, 120));
        nameField = makeTextField(450, 120);
        frame.add(nameField);

        frame.add(makeLabel("Card Number :", 250, 170));
        cardField = makeTextField(450, 170);
        frame.add(cardField);

        frame.add(makeLabel("PIN :", 250, 220));
        pinField = makePasswordField(450, 220);
        frame.add(pinField);

        frame.add(makeLabel("Confirm PIN :", 250, 270));
        confirmPinField = makePasswordField(450, 270);
        frame.add(confirmPinField);

        frame.add(makeLabel("Mobile Number :", 250, 320));
        mobileField = makeTextField(450, 320);
        frame.add(mobileField);

        frame.add(makeLabel("Initial Deposit :", 250, 370));
        depositField = makeTextField(450, 370);
        frame.add(depositField);

        createButton = new JButton("Create");
        createButton.setBounds(300, 450, 150, 40);
        createButton.setBackground(Color.BLACK);
        createButton.setForeground(Color.WHITE);
        createButton.setFont(new Font("Verdana", Font.BOLD, 14));
        createButton.addActionListener(this);
        frame.add(createButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(500, 450, 150, 40);
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Verdana", Font.BOLD, 14));
        cancelButton.addActionListener(this);
        frame.add(cancelButton);

        frame.setVisible(true);
    }

    private JLabel makeLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Verdana", Font.BOLD, 14));
        label.setBounds(x, y, 200, 30);
        return label;
    }

    private JTextField makeTextField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 250, 30);
        return field;
    }

    private JPasswordField makePasswordField(int x, int y) {
        JPasswordField field = new JPasswordField();
        field.setBounds(x, y, 250, 30);
        return field;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            String name = nameField.getText();
            String card = cardField.getText();
            String pin = String.valueOf(pinField.getPassword());
            String confirmPin = String.valueOf(confirmPinField.getPassword());
            String mobile = mobileField.getText();
            String deposit = depositField.getText();

            if (!pin.equals(confirmPin)) {
                JOptionPane.showMessageDialog(frame, "PINs do not match.");
                return;
            }

            if (name.isEmpty() || card.isEmpty() || pin.isEmpty() || mobile.isEmpty() || deposit.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all required fields.");
                return;
            } else {
                Connection conn = DBConnection.getConnection();
                String query = "INSERT INTO users (name, card_number, pin, mobile, balance) VALUES (?, ?, ?, ?, ?)";

                try {
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, name);
                    pstmt.setString(2, card);
                    pstmt.setString(3, pin);
                    pstmt.setString(4, mobile);
                    pstmt.setDouble(5, Double.parseDouble(deposit));
                    int rowsInserted = pstmt.executeUpdate();

                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(frame, "Account created and saved to DB!");
                        new Dashboard();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to create account.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            frame.dispose();
        } else if (e.getSource() == cancelButton) {
            frame.dispose();
            new Dashboard();
        }
    }
}
