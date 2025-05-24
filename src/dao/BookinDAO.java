package src.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ticket.model.Booking;

import java.sql.*;



public class BookingDAO {

	public void saveBooking(Booking booking) {
	    String sql = "INSERT INTO bookings (name, mode, from_place, to_place, seat_no, distance, fare) VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, booking.getName());
	        stmt.setString(2, booking.getMode());
	        stmt.setString(3, booking.getFrom());
	        stmt.setString(4, booking.getTo());
	        stmt.setInt(5, booking.getSeatNo());
	        stmt.setInt(6, booking.getDistance());
	        stmt.setDouble(7, booking.getFare());

	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public List<String> getAvailableSeats(Booking booking) {
	    List<String> allSeats = new ArrayList<>();
	    for (int i = 1; i <= 50; i++) {
	        allSeats.add(String.valueOf(i));
	    }

	    String sql = "SELECT seat_no FROM bookings WHERE mode = ? AND from_place = ? AND to_place = ?";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, booking.getMode());
	        stmt.setString(2, booking.getFrom());
	        stmt.setString(3, booking.getTo());

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String bookedSeat = String.valueOf(rs.getInt("seat_no"));
	            allSeats.remove(bookedSeat);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return allSeats;
	}

}

