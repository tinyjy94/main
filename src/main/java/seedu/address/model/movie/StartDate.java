package seedu.address.model.movie;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Movie's startdate in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {


    public static final String MESSAGE_STARTDATE_CONSTRAINTS =
            "StartDate must be in this format: DD/MM/YYYY";
    //This regex does not validate dates such as leap years and such.
    public static final String STARTDATE_VALIDATION_REGEX =
            "^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$";
    public final String startdate;

    /**
     * Constructs a {@code StartDate}.
     *
     * @param startdate A valid startdate.
     */
    public StartDate(String startdate) {
        requireNonNull(startdate);
        checkArgument(isValidStartDate(startdate), MESSAGE_STARTDATE_CONSTRAINTS);
        this.startdate = startdate;
    }

    /**
     * Returns true if a given string is a valid movie startdate.
     */
    public static boolean isValidStartDate(String test) {
        return test.matches(STARTDATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return startdate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.startdate.equals(((StartDate) other).startdate)); // state check
    }

    @Override
    public int hashCode() {
        return startdate.hashCode();
    }

}
