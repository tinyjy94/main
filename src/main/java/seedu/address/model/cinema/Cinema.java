package seedu.address.model.cinema;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Cinema in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Cinema {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;

    private final UniqueTheaterList theater;
    private final UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Cinema(Name name, Phone phone, Email email, Address address, Set<Theater> theater, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, theater, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.theater = new UniqueTheaterList(theater);
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Set<Theater> getTheaters() {
        return Collections.unmodifiableSet(theater.toSet());
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Cinema)) {
            return false;
        }

        Cinema otherCinema = (Cinema) other;
        return otherCinema.getName().equals(this.getName())
                && otherCinema.getPhone().equals(this.getPhone())
                && otherCinema.getEmail().equals(this.getEmail())
                && otherCinema.getAddress().equals(this.getAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, theater, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Theater: ");
        getTheaters().forEach(builder::append);
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
