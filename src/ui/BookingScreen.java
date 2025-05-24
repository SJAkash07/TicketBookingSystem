package src.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.dao.BookingDAO;
import src.model.Booking;

import java.util.List;

public class BookingScreen extends JFrame {
    private JTextField nameField;
    private JComboBox<String> seatBox;
    private JComboBox<String> modeBox, fromBox, toBox;

    public BookingScreen() {
        setTitle("Ticket Booking");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Simple colors
        Color backgroundColor = new Color(230, 240, 250);
        Color headerColor = new Color(0, 102, 204);
        Color buttonColor = new Color(0, 153, 255);

        getContentPane().setBackground(backgroundColor);

        JLabel titleLabel = new JLabel("Book Your Ticket", JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(headerColor);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setPreferredSize(new Dimension(400, 40));

        nameField = new JTextField();
        seatBox = new JComboBox<>();

        modeBox = new JComboBox<>(new String[]{"Train", "Bus", "Flight"});
        fromBox = new JComboBox<>(new String[]{"Greater Noida", "Delhi", "Mumbai","Kanpur","Agra","Kolkata","Patna"});
        toBox = new JComboBox<>(new String[]{"Agra", "Kanpur", "Lucknow", "Bangalore", "Chennai", "Kolkata"});

        JButton bookBtn = new JButton("Book Ticket");
        bookBtn.setBackground(buttonColor);
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFocusPainted(false);

        bookBtn.addActionListener(this::handleBooking);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBackground(backgroundColor);

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Mode of Transport:"));
        panel.add(modeBox);
        panel.add(new JLabel("From:"));
        panel.add(fromBox);
        panel.add(new JLabel("To:"));
        panel.add(toBox);
        panel.add(new JLabel("Available Seats:"));
        panel.add(seatBox);
        panel.add(new JLabel());  // empty label for spacing
        panel.add(bookBtn);

        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        // Update seats when selection changes
        ActionListener updateSeats = e -> loadAvailableSeats();
        modeBox.addActionListener(updateSeats);
        fromBox.addActionListener(updateSeats);
        toBox.addActionListener(updateSeats);

        setVisible(true);

        loadAvailableSeats();
    }

    private void loadAvailableSeats() {
        String mode = (String) modeBox.getSelectedItem();
        String from = (String) fromBox.getSelectedItem();
        String to = (String) toBox.getSelectedItem();

        seatBox.removeAllItems();
        if (mode != null && from != null && to != null) {
            Booking tempBooking = new Booking();
            tempBooking.setMode(mode);
            tempBooking.setFrom(from);
            tempBooking.setTo(to);

            List<String> availableSeats = new BookingDAO().getAvailableSeats(tempBooking);
            for (String seat : availableSeats) {
                seatBox.addItem(seat);
            }
        }
    }


    private void handleBooking(ActionEvent e) {
        String name = nameField.getText().trim();
        String mode = (String) modeBox.getSelectedItem();
        String from = (String) fromBox.getSelectedItem();
        String to = (String) toBox.getSelectedItem();
        String seatStr = (String) seatBox.getSelectedItem();

        if (name.isEmpty() || seatStr == null || from == null || to == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int seat;
        try {
            seat = Integer.parseInt(seatStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Seat number must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int distance = getDistance(from, to);
        if (distance == 0) {
            JOptionPane.showMessageDialog(this, "Invalid route", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BookingDAO dao = new BookingDAO();

        double fare = calculateFare(mode, distance);
        Booking booking = new Booking(name, mode, from, to, seat, distance, fare);
        dao.saveBooking(booking);

        String ticket = "🎫 Ticket Details\n\n"
                + "Passenger: " + booking.getName()
                + "\nMode: " + booking.getMode()
                + "\nFrom: " + booking.getFrom()
                + "\nTo: " + booking.getTo()
                + "\nSeat No: " + booking.getSeatNo()
                + "\nDistance: " + booking.getDistance() + " km"
                + "\nFare: ₹" + booking.getFare();

        JOptionPane.showMessageDialog(this, ticket, "Ticket Booked", JOptionPane.INFORMATION_MESSAGE);
    }


    private int getDistance(String from, String to) {
        String route = from + "→" + to;
        return switch (route) {
            case "Greater Noida→Agra" -> 200;
            case "Greater Noida→Kanpur" -> 400;
            case "Greater Noida→Lucknow" -> 500;
            case "Greater Noida→Bangalore" -> 2100;
            case "Greater Noida→Chennai" -> 2200;
            case "Greater Noida→Kolkata" -> 1500;
            case "Delhi→Agra" -> 210;
            case "Mumbai→Bangalore" -> 980;
            default -> 0;
        };
    }

    private double calculateFare(String mode, int distance) {
        return switch (mode) {
            case "Train" -> 50 + (2 * distance);
            case "Bus" -> 30 + (1.5 * distance);
            case "Flight" -> 200 + (5 * distance);
            default -> 0;
        };
    }
}
