package src.ui;

import javax.swing.*;
import src.dao.UserDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

public class AuthScreen extends JFrame {

    private JPanel contentPanel;
    private JPanel signInPanel;
    private JPanel signUpPanel;

    private JTextField signInEmailField;
    private JPasswordField signInPasswordField;

    private JTextField signUpNameField;
    private JTextField signUpEmailField;
    private JPasswordField signUpPasswordField;

    // Regex for simple email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    public AuthScreen() {
        setTitle("Ticket Booking");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Ticket Booking", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        contentPanel = new JPanel(new CardLayout());

        initSignInPanel();
        initSignUpPanel();

        contentPanel.add(signInPanel, "signIn");
        contentPanel.add(signUpPanel, "signUp");

        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        showSignIn();

        setVisible(true);
    }

    private void initSignInPanel() {
        signInPanel = new JPanel(new GridLayout(6, 1, 10, 10)); // 6 rows to accommodate all elements properly
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
        signUpPanel = new JPanel(new GridLayout(8, 1, 10, 10)); // 8 rows for inputs + buttons
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
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "signIn");
        clearSignInFields();
    }

    private void showSignUp() {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, "signUp");
        clearSignUpFields();
    }

    private void clearSignInFields() {
        signInEmailField.setText("");
        signInPasswordField.setText("");
    }

    private void clearSignUpFields() {
        signUpNameField.setText("");
        signUpEmailField.setText("");
        signUpPasswordField.setText("");
    }

    private void handleSignIn(ActionEvent e) {
        String email = signInEmailField.getText().trim();
        String password = new String(signInPasswordField.getPassword()).trim();

        if (!validateEmail(email)) {
            showError("Please enter a valid email address.");
            return;
        }
        if (password.isEmpty()) {
            showError("Password cannot be empty.");
            return;
        }

        try {
            UserDAO dao = new UserDAO();
            if (dao.signIn(email, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new BookingScreen(); // Assuming BookingScreen is your next screen
            } else {
                showError("Invalid email or password.");
            }
        } catch (Exception ex) {
            showError("An error occurred during login. Please try again.");
            ex.printStackTrace();
        }
    }

    private void handleSignUp(ActionEvent e) {
        String name = signUpNameField.getText().trim();
        String email = signUpEmailField.getText().trim();
        String password = new String(signUpPasswordField.getPassword()).trim();

        if (name.isEmpty()) {
            showError("Name cannot be empty.");
            return;
        }
        if (!validateEmail(email)) {
            showError("Please enter a valid email address.");
            return;
        }
        if (password.isEmpty()) {
            showError("Password cannot be empty.");
            return;
        }

        try {
            UserDAO dao = new UserDAO();
            boolean success = dao.signUp(name, email, password);
            if (success) {
                JOptionPane.showMessageDialog(this, "Sign Up Successful!\nPlease Sign In.");
                showSignIn();
            } else {
                showError("User already exists or registration failed.");
            }
        } catch (Exception ex) {
            showError("An error occurred during sign up. Please try again.");
            ex.printStackTrace();
        }
    }

    private boolean validateEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
