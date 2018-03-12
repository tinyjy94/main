package seedu.address.model.cinema;

/**
 * Represents a theater in cinema
 */
public class Theater {

    public static final String MESSAGE_THEATER_CONSTRAINTS =
            "Theater number should be positive.";

    private int theaterNumber;

    public Theater(int theaterNumber) {
        this.theaterNumber = theaterNumber;
    }

    public int getTheaterNumber() {
        return theaterNumber;
    }

    public void setTheaterNumber(int theaterNumber) {
        this.theaterNumber = theaterNumber;
    }

    @Override
    public String toString() {
        return "Theater " + theaterNumber;
    }
}
