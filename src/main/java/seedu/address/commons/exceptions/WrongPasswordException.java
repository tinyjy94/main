package seedu.address.commons.exceptions;

/**
 * Represents an error when getting password during decryption
 */
public class WrongPasswordException extends Exception {
    public WrongPasswordException(String message) {
        super(message);
    }

}
