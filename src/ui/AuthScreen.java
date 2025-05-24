package src.ui;
import javax.swing.*;

import src.dao.UserDAO;

import java.awt.*;
import java.awt.event.ActionEvent;

public class AuthScreen extends JFrame {

    private JPanel signInPanel;
    private JPanel signUpPanel;

    private JTextField signInEmailField;
    private JPasswordField signInPasswordField;

    private JTextField signUpNameField;
    private JTextField signUpEmailField;
    private JPasswordField signUpPasswordField;

    public AuthScreen() {
        setTitle("Ticket Booking");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create the title label
        JLabel titleLabel = new JLabel("Ticket Booking", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Use BorderLayout to add title at top
        setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel(new CardLayout());

        initSignInPanel();
        initSignUpPanel();

        contentPanel.add(signInPanel, "signIn");
        contentPanel.add(signUpPanel, "signUp");

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        showSignIn();
        setVisible(true);
    }

    private void initSignInPanel() {
        signInPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        signInPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        signInEmailField = new JTextField();
        signInPasswordField = new JPasswordField();
        JButton signInButton = new JButton("Sign In");
        JButton toSignUpButton = new JButton("Don't have an account? Sign Up");

        signInPanel.add(new JLabel("Email:"));
        signInPanel.add(signInEmailField);
        signInPanel.add(new JLabel("Password:"));
        signInPanel.add(signInPasswordField);
        signInPanel.add(signInButton);
        signInPanel.add(toSignUpButton);

        signInButton.addActionListener(this::handleSignIn);
        toSignUpButton.addActionListener(e -> showSignUp());
    }

    private void initSignUpPanel() {
        signUpPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        signUpPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        signUpNameField = new JTextField();
        signUpEmailField = new JTextField();
        signUpPasswordField = new JPasswordField();
        JButton signUpButton = new JButton("Sign Up");
        JButton toSignInButton = new JButton("Already have an account? Sign In");

        signUpPanel.add(new JLabel("Name:"));
        signUpPanel.add(signUpNameField);
        signUpPanel.add(new JLabel("Email:"));
        signUpPanel.add(signUpEmailField);
        signUpPanel.add(new JLabel("Password:"));
        signUpPanel.add(signUpPasswordField);
        signUpPanel.add(signUpButton);
        signUpPanel.add(toSignInButton);

        signUpButton.addActionListener(this::handleSignUp);
        toSignInButton.addActionListener(e -> showSignIn());
    }

    private void showSignIn() {
        signInPanel.setVisible(true);
        signUpPanel.setVisible(false);
    }

    private void showSignUp() {
        signInPanel.setVisible(false);
        signUpPanel.setVisible(true);
    }

    private void handleSignIn(ActionEvent e) {
        String email = signInEmailField.getText().trim();
        String password = new String(signInPasswordField.getPassword()).trim();

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
        String name = signUpNameField.getText().trim();
        String email = signUpEmailField.getText().trim();
        String password = new String(signUpPasswordField.getPassword()).trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
        	UserDAO dao = new UserDAO();
        	boolean success = dao.signUp(name, email, password);

        	if (success) {
        	    JOptionPane.showMessageDialog(this, "Sign Up Successful!\nPlease Sign In.");
        	    showSignIn();
        	} else {
        	    JOptionPane.showMessageDialog(this, "User already exists or registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
        	}

        }
    }
}
