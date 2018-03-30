package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

public class DecryptionRequestEvent extends BaseEvent {
    /**
     * Indicates a request for decryption
     */
    private final String password;

    public DecryptionRequestEvent(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return password;
    }

    public String getPassword() {
        return password;
    }
}
