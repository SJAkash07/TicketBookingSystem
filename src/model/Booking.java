package src.model;

/**
 * Represents a booking record for a ticket.
 */
public class Booking {

    private String name;
    private String mode;
    private String from;
    private String to;
    private int seatNo;
    private int distance;
    private double fare;

    /**
     * No-argument constructor.
     */
    public Booking() {
    }

    /**
     * Full constructor to initialize all fields.
     *
     * @param name     Customer's name
     * @param mode     Mode of transport (e.g., Train, Bus, Flight)
     * @param from     Starting location
     * @param to       Destination location
     * @param seatNo   Seat number
     * @param distance Distance travelled
     * @param fare     Fare amount
     */
    public Booking(String name, String mode, String from, String to, int seatNo, int distance, double fare) {
        this.setName(name);
        this.setMode(mode);
        this.setFrom(from);
        this.setTo(to);
        this.setSeatNo(seatNo);
        this.setDistance(distance);
        this.setFare(fare);
    }

    public String getName() {
        return name;
    }

    /**
     * Set the customer name. Name must not be null or empty.
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name.trim();
    }

    public String getMode() {
        return mode;
    }

    /**
     * Set the mode of transport. Must not be null or empty.
     */
    public void setMode(String mode) {
        if (mode == null || mode.trim().isEmpty()) {
            throw new IllegalArgumentException("Mode cannot be empty");
        }
        this.mode = mode.trim();
    }

    public String getFrom() {
        return from;
    }

    /**
     * Set starting location. Must not be null or empty.
     */
    public void setFrom(String from) {
        if (from == null || from.trim().isEmpty()) {
            throw new IllegalArgumentException("From location cannot be empty");
        }
        this.from = from.trim();
    }

    public String getTo() {
        return to;
    }

    /**
     * Set destination location. Must not be null or empty.
     */
    public void setTo(String to) {
        if (to == null || to.trim().isEmpty()) {
            throw new IllegalArgumentException("To location cannot be empty");
        }
        this.to = to.trim();
    }

    public int getSeatNo() {
        return seatNo;
    }

    /**
     * Set seat number. Must be positive.
     */
    public void setSeatNo(int seatNo) {
        if (seatNo <= 0) {
            throw new IllegalArgumentException("Seat number must be positive");
        }
        this.seatNo = seatNo;
    }

    public int getDistance() {
        return distance;
    }

    /**
     * Set distance travelled. Must be positive.
     */
    public void setDistance(int distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance must be positive");
        }
        this.distance = distance;
    }

    public double getFare() {
        return fare;
    }

    /**
     * Set fare amount. Must be non-negative.
     */
    public void setFare(double fare) {
        if (fare < 0) {
            throw new IllegalArgumentException("Fare cannot be negative");
        }
        this.fare = fare;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "name='" + name + '\'' +
                ", mode='" + mode + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", seatNo=" + seatNo +
                ", distance=" + distance +
                ", fare=" + fare +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Booking)) return false;
        Booking other = (Booking) obj;
        return seatNo == other.seatNo &&
                distance == other.distance &&
                Double.compare(other.fare, fare) == 0 &&
                name.equals(other.name) &&
                mode.equals(other.mode) &&
                from.equals(other.from) &&
                to.equals(other.to);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + mode.hashCode();
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + seatNo;
        result = 31 * result + distance;
        long temp = Double.doubleToLongBits(fare);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
