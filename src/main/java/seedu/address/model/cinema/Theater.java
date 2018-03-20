package seedu.address.model.cinema;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SCREENING;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

import seedu.address.model.cinema.exceptions.ScreeningConflictException;
import seedu.address.model.screening.Screening;

/**
 * Represents a theater in cinema
 */
public class Theater {

    public static final String MESSAGE_THEATER_CONSTRAINTS = "Theater number should be positive.";

    /**
     * Theater number must be positive
     */
    public static final String THEATER_VALIDATION_REGEX = "^[1-9]\\d*$";

    private int theaterNumber;
    private ArrayList<Screening> screeningList;

    public Theater() {
    }

    public Theater(int theaterNumber) {
        this.theaterNumber = theaterNumber;
        this.screeningList = new ArrayList<>();
    }

    /**
     * Returns true if a given string is a valid theater number.
     */
    public static boolean isValidTheater(int test) {
        return String.valueOf(test).matches(THEATER_VALIDATION_REGEX);
    }

    public int getTheaterNumber() {
        return theaterNumber;
    }

    public void setTheaterNumber(int theaterNumber) {
        this.theaterNumber = theaterNumber;
    }

    /**
     * Adds a screening to the sorted screening list of the theater
     */
    public void addScreeningToTheater(Screening screening) throws ScreeningConflictException {
        if (isSlotAvailable(screening)) {
            screeningList.add(screening);
            sortScreeningList();
        } else {
            throw new ScreeningConflictException(MESSAGE_INVALID_SCREENING);
        }
    }

    /**
     * Sorts the screening list by screening date time
     */
    private void sortScreeningList() {
        screeningList.sort(Comparator.comparing(Screening::getScreeningDateTime));
    }

    /**
     * Checks that the screening can fit into the schedule of the theater.
     * @param toAdd
     * @return true if the screening can fit into the schedule
     */
    private boolean isSlotAvailable(Screening toAdd) {
        int totalScreeningsWithSameDate = getTotalScreeningsWithSameDate(toAdd);

        if (totalScreeningsWithSameDate == 0) {
            return true;
        } else if (totalScreeningsWithSameDate == 1) {
            return hasNoConflictWithOneOtherScreening(toAdd);
        } else {
            return hasNoConflictWithBeforeAndAfter(toAdd, totalScreeningsWithSameDate);
        }
    }

    /**
     * Checks that the screening does not conflict with one other screening
     * @param toAdd
     * @return true if no conflict
     */
    private boolean hasNoConflictWithOneOtherScreening(Screening toAdd) {
        for (Screening s: screeningList) {
            if (isSameScreeningDate(toAdd, s)) {
                return isScreenTimeOnOrBefore(toAdd, s) || isScreenTimeOnOrAfter(toAdd, s);
            }
        }
        return false;
    }

    /**
     * Checks that the screening does not conflict with the screening before or screening after it
     * @param toAdd
     * @param totalScreenings number of screenings with the same date
     * @return true if no conflict
     */
    private boolean hasNoConflictWithBeforeAndAfter(Screening toAdd, int totalScreenings) {
        int count = 0;
        boolean hasNoConflict = false;
        Screening screeningBefore = screeningList.get(0);

        if (isSameScreeningDate(toAdd , screeningBefore)) {
            count++;
        }

        for (int i = 1; i < screeningList.size(); i++) {
            Screening currentScreening = screeningList.get(i);
            if (hasNoConflict == true) {
                break;
            }

            //first screening
            if (count == 1 && isScreenTimeOnOrBefore(toAdd, currentScreening)) {
                return true;
            } else if (isSameScreeningDate(currentScreening, toAdd)) {
                count++;
                // last screening
                if (count == totalScreenings && isScreenTimeOnOrAfter(toAdd, currentScreening)) {
                    return true;
                } else {
                    hasNoConflict = isScreenTimeOnOrAfter(toAdd, screeningBefore)
                            && isScreenTimeOnOrBefore(toAdd, currentScreening);
                }
            }
            screeningBefore = currentScreening;
        }
        return hasNoConflict;
    }

    /**
     * Checks that the start time of the screening toAdd is on or after the end time of the screening before it
     * @return true if the start time of the screening is on or after the end time of the screening before it
     */
    private boolean isScreenTimeOnOrAfter(Screening toAdd, Screening screeningBefore) {
        LocalTime toAddTime = toAdd.getScreeningDateTime().toLocalTime();
        LocalTime screeningBeforeTime = screeningBefore.getScreeningEndDateTime().toLocalTime();
        if (toAddTime.isAfter(screeningBeforeTime) || toAddTime.equals(screeningBeforeTime)) {
            return true;
        }
        return false;
    }

    /**
     * Checks that the end time of the screening toAdd is on or before the start time of the screening after it
     * @return true if the end time of the screening is before the start time of the screening after it
     */
    private boolean isScreenTimeOnOrBefore(Screening toAdd, Screening screeningAfter) {
        LocalTime toAddTime = toAdd.getScreeningEndDateTime().toLocalTime();
        LocalTime screeningAfterTime = screeningAfter.getScreeningDateTime().toLocalTime();
        if (toAddTime.isBefore(screeningAfterTime) || toAddTime.equals(screeningAfterTime)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if two screenings have the same date
     * @return true if both screenings have the same date
     */
    private boolean isSameScreeningDate(Screening s1, Screening s2) {
        return s1.getScreeningDateTime().toLocalDate().equals(s2.getScreeningDateTime().toLocalDate());
    }

    /**
     * Calculates the total number of screenings with the same screening date
     * @param toAdd
     * @return total number of screenings
     */
    private int getTotalScreeningsWithSameDate(Screening toAdd) {
        int count = 0;
        for (Screening s: screeningList) {
            if (isSameScreeningDate(toAdd, s)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns a list of screenings in the theater
     */
    public ArrayList<Screening> getScreeningList() {
        return screeningList;
    }

    @Override
    public String toString() {
        return "Theater " + theaterNumber;
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
                && otherTheater.getScreeningList().equals(this.getScreeningList());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(theaterNumber, screeningList);
    }
}
