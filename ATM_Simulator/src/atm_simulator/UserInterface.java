package atm_simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserInterface implements ActionListener {

    private JFrame frame;
    private JLabel titleLabel;
    private JButton withdrawButton, depositButton, balanceButton, historyButton, exitButton;
    private String cardNumber;

    public UserInterface(String cardNumber) {
        this.cardNumber = cardNumber;
        frame = new JFrame("ATM Simulator - Transaction Menu");
        frame.setSize(1000, 700);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        titleLabel = new JLabel("Select Your Transaction");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBounds(320, 40, 400, 40);
        frame.add(titleLabel);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(220, 150, 250, 60);
        withdrawButton.setBackground(Color.BLACK);
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.setFont(new Font("Verdana", Font.BOLD, 16));
        withdrawButton.addActionListener(this);
        frame.add(withdrawButton);

        depositButton = new JButton("Deposit");
        depositButton.setBounds(530, 150, 250, 60);
        depositButton.setBackground(Color.BLACK);
        depositButton.setForeground(Color.WHITE);
        depositButton.setFont(new Font("Verdana", Font.BOLD, 16));
        depositButton.addActionListener(this);
        frame.add(depositButton);

        balanceButton = new JButton("Balance");
        balanceButton.setBounds(220, 240, 250, 60);
        balanceButton.setBackground(Color.BLACK);
        balanceButton.setForeground(Color.WHITE);
        balanceButton.setFont(new Font("Verdana", Font.BOLD, 16));
        balanceButton.addActionListener(this);
        frame.add(balanceButton);

        historyButton = new JButton("Transaction History");
        historyButton.setBounds(530, 240, 250, 60);
        historyButton.setBackground(Color.BLACK);
        historyButton.setForeground(Color.WHITE);
        historyButton.setFont(new Font("Verdana", Font.BOLD, 16));
        historyButton.addActionListener(this);
        frame.add(historyButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(400, 400, 200, 50);
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Verdana", Font.BOLD, 16));
        exitButton.addActionListener(this);
        frame.add(exitButton);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == withdrawButton) {
            frame.dispose();
            new Withdraw(cardNumber); 
        } else if (e.getSource() == depositButton) {
            frame.dispose();
            new Deposite(cardNumber);
        } else if (e.getSource() == balanceButton) {
            frame.dispose();
            new Balance(cardNumber);
        } else if (e.getSource() == historyButton) {
            frame.dispose();
            new Transaction_History(cardNumber);
        } else if (e.getSource() == exitButton) {
            frame.dispose();
            new Dashboard();
        }
    }
}
