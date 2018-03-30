package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

public class EncryptionRequestEvent extends BaseEvent {
    /**
     * Indicates a request for encryption
     */
    private final String password;

    public EncryptionRequestEvent(String password) {
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
