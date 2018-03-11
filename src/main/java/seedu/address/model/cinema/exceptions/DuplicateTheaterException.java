package seedu.address.model.cinema.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Theater objects.
 */
public class DuplicateTheaterException extends DuplicateDataException {
    public DuplicateTheaterException() {
        super("Operation would result in duplicate theaters");
    }
}
