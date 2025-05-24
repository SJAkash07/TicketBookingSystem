package com.ticket.dao;

import java.sql.*;

public class UserDAO {
    private final String url = "jdbc:mysql://localhost:3306/ticketdb";
    private final String username = "root";
    private final String password = "your_password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public boolean signUp(String name, String email, String pass) {
        String checkQuery = "SELECT * FROM users WHERE email = ?";
        String insertQuery = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) return false; // user already exists

            insertStmt.setString(1, name);
            insertStmt.setString(2, email);
            insertStmt.setString(3, pass);
            insertStmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean signIn(String email, String pass) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // returns true if a matching user is found

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
