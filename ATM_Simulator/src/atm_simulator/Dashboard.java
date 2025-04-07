package atm_simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Dashboard implements ActionListener {

    private static JFrame f;
    private static JButton signin, forgotpass, signup, adminBtn;
    private static JLabel titleLabel, cardNo, cardPin;
    private static JTextField card;
    private static JPasswordField pin;

    Dashboard() {
        f = new JFrame("ATM Simulator...");
        f.setSize(1000, 700);
        f.setLayout(null);
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(Color.WHITE);

        titleLabel = new JLabel("Welcome to ATM Simulator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(300, 50, 400, 40);
        f.add(titleLabel);

        cardNo = new JLabel("Card No:");
        cardNo.setFont(new Font("Verdana", Font.BOLD, 18));
        cardNo.setBounds(280, 170, 100, 30);
        f.add(cardNo);

        card = new JTextField();
        card.setBounds(400, 170, 250, 30);
        f.add(card);

        cardPin = new JLabel("PIN:");
        cardPin.setFont(new Font("Verdana", Font.BOLD, 16));
        cardPin.setBounds(280, 220, 100, 30);
        f.add(cardPin);

        pin = new JPasswordField();
        pin.setBounds(400, 220, 250, 30);
        f.add(pin);

        signin = new JButton("Sign In");
        signin.setBounds(250, 400, 200, 50);
        signin.setBackground(Color.BLACK);
        signin.setForeground(Color.WHITE);
        signin.setFont(new Font("Verdana", Font.BOLD, 14));
        signin.addActionListener(this);
        f.add(signin);

        forgotpass = new JButton("Forgot Password");
        forgotpass.setBounds(520, 400, 200, 50);
        forgotpass.setBackground(Color.BLACK);
        forgotpass.setForeground(Color.WHITE);
        forgotpass.setFont(new Font("Verdana", Font.BOLD, 14));
        forgotpass.addActionListener(this);
        f.add(forgotpass);

        signup = new JButton("Create New Account");
        signup.setBounds(300, 500, 400, 50);
        signup.setBackground(Color.BLACK);
        signup.setForeground(Color.WHITE);
        signup.setFont(new Font("Verdana", Font.BOLD, 14));
        signup.addActionListener(this);
        f.add(signup);

        adminBtn = new JButton("Admin");
        adminBtn.setBounds(300, 570, 400, 50);
        adminBtn.setBackground(new Color(139, 0, 0));
        adminBtn.setForeground(Color.WHITE);
        adminBtn.setFont(new Font("Verdana", Font.BOLD, 14));
        adminBtn.addActionListener(this);
        f.add(adminBtn);

        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signin) {
            try {
                Connection conn = DBConnection.getConnection();
                String query = "SELECT * FROM users WHERE card_number = ? AND pin = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, card.getText());
                pstmt.setString(2, String.valueOf(pin.getPassword()));
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    f.setVisible(false);
                    new UserInterface(card.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid card number or PIN.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == forgotpass) {
            f.setVisible(false);
            new Forgot_Password();
        } else if (e.getSource() == signup) {
            f.dispose();
            new Create_User();
        } else if (e.getSource() == adminBtn) {
            String enteredCard = card.getText().trim();
            String enteredPin = String.valueOf(pin.getPassword()).trim();
            if (enteredCard.equals("123456") && enteredPin.equals("123456")) {
                f.dispose();
                new AdminPage();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Admin credentials.");
            }
        }
    }

    public static void main(String[] args) {
        new Dashboard();
    }
}
