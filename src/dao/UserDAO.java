package src.dao;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserDAO {
    private final String url = "jdbc:mysql://localhost:3306/ticketdb";
    private final String username = "root";
    private final String password = "your_password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // Hash password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not found");
        }
    }

    // Register a new user
    public boolean signUp(String name, String email, String pass) {
        String checkQuery = "SELECT * FROM users WHERE email = ?";
        String insertQuery = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                rs.close();
                return false; // user already exists
            }
            rs.close();

            String hashedPass = hashPassword(pass);

            insertStmt.setString(1, name);
            insertStmt.setString(2, email);
            insertStmt.setString(3, hashedPass);
            insertStmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Authenticate user login
    public boolean signIn(String email, String pass) {
        String query = "SELECT password FROM users WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                rs.close();

                String inputHash = hashPassword(pass);
                return storedHash.equals(inputHash);
            }
            rs.close();
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
