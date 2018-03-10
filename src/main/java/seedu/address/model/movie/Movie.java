package seedu.address.model.movie;

import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Name;
import seedu.address.model.tag.UniqueTagList;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Movie in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Movie {

    private final Name name;

    public Movie(Name name) {
        requireAllNonNull(name);
        this.name = name;
    }

    public Name getName() { return name; }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Movie)) {
            return false;
        }

        Movie otherMovie = (Movie) other;
        return otherMovie.getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }
}
