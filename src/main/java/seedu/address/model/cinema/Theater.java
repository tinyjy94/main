package seedu.address.model.cinema;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Theater in the Cinema.
 */
public class Theater {

    public static final String MESSAGE_THEATER_CONSTRAINTS =
            "Number of seats can only contain positive numbers, "
                    + "status should only contain A for available or M for maintenance or U for unavailable";
    private static final String NUM_OF_SEATS_VALIDATION_REGEX = "^[1-9]\\d*$";
    private static final String STATUS_VALIDATION_REGEX = "[AMU]";
    private int theaterNumber;
    private String numOfSeats;
    private String status;

    /**
     * Every field must be present and not null.
     */
    public Theater(int theaterNumber, String numOfSeats, String status) {
        checkArgument(isValidTheater(theaterNumber, numOfSeats, status), MESSAGE_THEATER_CONSTRAINTS);
        this.theaterNumber = theaterNumber;
        this.numOfSeats = numOfSeats;
        this.status = status;
    }

    public Theater(int theaterNumber) {
        this.theaterNumber = theaterNumber;
    }

    public int getTheaterNumber() {
        return theaterNumber;
    }

    public String getNumOfSeats() {
        return numOfSeats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNumOfSeats(String numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Theater)) {
            return false;
        }

        Theater otherTheater = (Theater) other;
        return otherTheater.getTheaterNumber() == this.getTheaterNumber()
                && otherTheater.getNumOfSeats().equals(this.getNumOfSeats())
                && otherTheater.getStatus().equals(this.getStatus());
    }

    /**
     * @return true if a given int and string is a valid number of seats and status respectively
     */
    public static boolean isValidTheater(int theaterNumber, String numOfSeats, String status) {
        return status.matches(STATUS_VALIDATION_REGEX)
                && numOfSeats.matches(NUM_OF_SEATS_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(theaterNumber, numOfSeats, status);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTheaterNumber())
                .append(" Number of Seats: ")
                .append(getNumOfSeats())
                .append(" Status: ")
                .append(getStatus());
        return builder.toString();
    }

}
