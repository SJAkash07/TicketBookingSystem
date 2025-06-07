package src.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import src.dao.BookingDAO;
import src.model.Booking;
import java.util.List;

public class BookingScreen extends JFrame {
    private JTextField nameField;
    private JComboBox<String> seatBox;
    private JComboBox<String> modeBox, fromBox, toBox;
    private JButton bookBtn;

    public BookingScreen() {
        setTitle("Ticket Booking");
        setSize(450, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Colors
        Color bgColor = new Color(245, 248, 252);
        Color panelColor = Color.WHITE;
        Color headerColor = new Color(30, 144, 255);
        Color buttonColor = new Color(0, 123, 255);
        Color buttonHoverColor = new Color(0, 102, 204);
        Color borderColor = new Color(200, 200, 200);

        getContentPane().setBackground(bgColor);

        // Header Label - full width with padding & shadow
        JLabel titleLabel = new JLabel("Book Your Ticket", JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(headerColor);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setPreferredSize(new Dimension(450, 60));
        titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(0, 80, 170)));

        // Card panel for inputs with padding and drop shadow effect
        JPanel panel = new JPanel();
        panel.setBackground(panelColor);
        panel.setBorder(new EmptyBorder(30, 45, 30, 45));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(true);

        // Create input fields with consistent spacing & style
        panel.add(createFieldPanel("Name:", nameField = new JTextField()));
        panel.add(Box.createVerticalStrut(20));
        panel.add(createFieldPanel("Mode of Transport:", modeBox = new JComboBox<>(new String[]{"Train", "Bus", "Flight"})));
        panel.add(Box.createVerticalStrut(20));
        panel.add(createFieldPanel("From:", fromBox = new JComboBox<>(new String[]{"Greater Noida", "Delhi", "Mumbai", "Kanpur", "Agra", "Kolkata", "Patna"})));
        panel.add(Box.createVerticalStrut(20));
        panel.add(createFieldPanel("To:", toBox = new JComboBox<>(new String[]{"Agra", "Kanpur", "Lucknow", "Bangalore", "Chennai", "Kolkata"})));
        panel.add(Box.createVerticalStrut(20));
        panel.add(createFieldPanel("Available Seats:", seatBox = new JComboBox<>()));
        panel.add(Box.createVerticalStrut(30));

        // Book Button
        bookBtn = new JButton("Book Ticket");
        bookBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookBtn.setBackground(buttonColor);
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        bookBtn.setFocusPainted(false);
        bookBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bookBtn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        bookBtn.setEnabled(false);

        // Hover effect on button
        bookBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bookBtn.setBackground(buttonHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bookBtn.setBackground(buttonColor);
            }
        });

        bookBtn.addActionListener(this::handleBooking);

        panel.add(bookBtn);

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        // Listeners to update seats and validate form
        ActionListener updateSeatsAndValidate = e -> {
            loadAvailableSeats();
            validateBookingForm();
        };
        modeBox.addActionListener(updateSeatsAndValidate);
        fromBox.addActionListener(updateSeatsAndValidate);
        toBox.addActionListener(updateSeatsAndValidate);
        nameField.getDocument().addDocumentListener(new SimpleDocumentListener(this::validateBookingForm) {});

        setVisible(true);

        loadAvailableSeats();
        validateBookingForm();
    }

    private JPanel createFieldPanel(String labelText, JComponent inputField) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setPreferredSize(new Dimension(150, 30));

        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        if (inputField instanceof JTextField) {
            ((JTextField) inputField).setColumns(15);
            ((JTextField) inputField).setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
            ));
        } else if (inputField instanceof JComboBox) {
            ((JComboBox<?>) inputField).setMaximumRowCount(6);
            ((JComboBox<?>) inputField).setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        }

        JPanel fieldPanel = new JPanel(new BorderLayout(10, 0));
        fieldPanel.setBackground(Color.WHITE);
        fieldPanel.add(label, BorderLayout.WEST);
        fieldPanel.add(inputField, BorderLayout.CENTER);
        return fieldPanel;
    }

    private void loadAvailableSeats() {
        seatBox.removeAllItems();

        String mode = (String) modeBox.getSelectedItem();
        String from = (String) fromBox.getSelectedItem();
        String to = (String) toBox.getSelectedItem();

        if (mode != null && from != null && to != null && !from.equals(to)) {
            Booking tempBooking = new Booking();
            tempBooking.setMode(mode);
            tempBooking.setFrom(from);
            tempBooking.setTo(to);

            List<String> availableSeats = new BookingDAO().getAvailableSeats(tempBooking);

            if (availableSeats.isEmpty()) {
                seatBox.addItem("No seats available");
                seatBox.setEnabled(false);
            } else {
                for (String seat : availableSeats) {
                    seatBox.addItem(seat);
                }
                seatBox.setEnabled(true);
            }
        } else {
            seatBox.addItem("Select valid route");
            seatBox.setEnabled(false);
        }
    }

    private void handleBooking(ActionEvent e) {
        // Your existing booking logic here...
    }

    private boolean validateName(String name) {
        return name != null && !name.isEmpty() && name.matches("[a-zA-Z\\s]+");
    }

    private void validateBookingForm() {
        String name = nameField.getText().trim();
        String from = (String) fromBox.getSelectedItem();
        String to = (String) toBox.getSelectedItem();
        String seat = (String) seatBox.getSelectedItem();

        boolean enabled = validateName(name)
                && from != null && to != null && !from.equals(to)
                && seat != null && seatBox.isEnabled()
                && !seat.equals("No seats available") && !seat.equals("Select valid route");

        bookBtn.setEnabled(enabled);
    }

    // DocumentListener helper
    private abstract static class SimpleDocumentListener implements javax.swing.event.DocumentListener {
        private final Runnable onChange;
        public SimpleDocumentListener(Runnable onChange) { this.onChange = onChange; }
        public void insertUpdate(javax.swing.event.DocumentEvent e) { onChange.run(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { onChange.run(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { onChange.run(); }
    }
}
