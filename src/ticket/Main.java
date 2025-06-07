package src.ticket;

import src.ui.AuthScreen;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Launch the authentication screen on the Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            try {
                new AuthScreen();  // Open login/register screen
            } catch (Exception e) {
                System.err.println("Error launching application: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
