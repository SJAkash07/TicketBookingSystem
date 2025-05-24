package com.ticket.ui;

import javax.swing.*;
import com.ticket.dao.UserDAO;

import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen() {
        setTitle("Ticket Booking - Login");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Ticket Booking", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel userLabel = new JLabel("Email:");
        usernameField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton signInBtn = new JButton("Sign In");
        JButton signUpBtn = new JButton("Sign Up");

        signInBtn.addActionListener(this::handleSignIn);
        signUpBtn.addActionListener(this::handleSignUp); // Can open a new sign-up screen

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panel.add(titleLabel);
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(signInBtn);
        panel.add(signUpBtn);

        add(panel);
        setVisible(true);
    }

    private void handleSignIn(ActionEvent e) {
        String email = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            UserDAO dao = new UserDAO();
            if (dao.signIn(email, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new BookingScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleSignUp(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Sign Up screen not implemented in this file.");
    }
}
