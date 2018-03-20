package seedu.address.model.cinema.exceptions;

/**
 * Signals that the operation will result in conflict screening schedule.
 */
public class ScreeningConflictException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public ScreeningConflictException(String message) {
        super(message);
    }
}
