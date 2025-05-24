package src.model;

public class Booking {
    private String name;
    private String mode;
    private String from;
    private String to;
    private int seatNo;
    private int distance;
    private double fare;

    //  No-argument constructor
    public Booking() {
    }

    //  Full constructor
    public Booking(String name, String mode, String from, String to, int seatNo, int distance, double fare) {
        this.name = name;
        this.mode = mode;
        this.from = from;
        this.to = to;
        this.seatNo = seatNo;
        this.distance = distance;
        this.fare = fare;
    }

    // Getters and setters...
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public int getSeatNo() { return seatNo; }
    public void setSeatNo(int seatNo) { this.seatNo = seatNo; }

    public int getDistance() { return distance; }
    public void setDistance(int distance) { this.distance = distance; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }
}
