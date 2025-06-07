package src.ui;

import javax.swing.*;
import src.dao.UserDAO;
import java.awt.*;
import java.awt.event.*;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInBtn;

    public LoginScreen() {
        setTitle("Ticket Booking - Login");
        setSize(400, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Colors & fonts for a cleaner look
        Color bgColor = new Color(240, 243, 247);
        Color panelColor = Color.WHITE;
        Color titleColor = new Color(25, 118, 210);
        Color buttonColor = new Color(30, 136, 229);
        Color buttonHoverColor = new Color(21, 101, 192);

        getContentPane().setBackground(bgColor);

        // Card-like panel for form
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(panelColor);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        // Title
        JLabel titleLabel = new JLabel("Ticket Booking", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(titleColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(titleLabel);
        cardPanel.add(Box.createVerticalStrut(25));

        // Email label & field
        JLabel userLabel = new JLabel("Email:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        usernameField.setToolTipText("Enter your email address");
        cardPanel.add(userLabel);
        cardPanel.add(usernameField);
        cardPanel.add(Box.createVerticalStrut(15));

        // Password label & field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        passwordField.setToolTipText("Enter your password");
        cardPanel.add(passLabel);
        cardPanel.add(passwordField);
        cardPanel.add(Box.createVerticalStrut(25));

        // Sign In button
        signInBtn = new JButton("Sign In");
        signInBtn.setBackground(buttonColor);
        signInBtn.setForeground(Color.WHITE);
        signInBtn.setFocusPainted(false);
        signInBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        signInBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        signInBtn.setBorderPainted(false);
        signInBtn.addActionListener(this::handleSignIn);
        cardPanel.add(signInBtn);
        cardPanel.add(Box.createVerticalStrut(10));

        // Sign Up button
        JButton signUpBtn = new JButton("Sign Up");
        signUpBtn.setBackground(new Color(200, 200, 200));
        signUpBtn.setForeground(Color.BLACK);
        signUpBtn.setFocusPainted(false);
        signUpBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signUpBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpBtn.setBorderPainted(false);
        signUpBtn.addActionListener(this::handleSignUp);
        cardPanel.add(signUpBtn);

        // Hover effect for buttons
        signInBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signInBtn.setBackground(buttonHoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signInBtn.setBackground(buttonColor);
            }
        });
        signUpBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                signUpBtn.setBackground(new Color(170, 170, 170));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                signUpBtn.setBackground(new Color(200, 200, 200));
            }
        });

        add(cardPanel);
        setVisible(true);

        usernameField.requestFocusInWindow();
    }

    private void handleSignIn(ActionEvent e) {
        String email = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Invalid Email", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO dao = new UserDAO();
        if (dao.signIn(email, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            dispose();
            new BookingScreen();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSignUp(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Sign Up screen not implemented yet.");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w.-]+@[\\w.-]+\\.\\w+$";
        return email.matches(emailRegex);
    }
}
