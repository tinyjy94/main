package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.movie.Movie;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the cinemas list.
     * This list will not contain any duplicate cinemas.
     */
    ObservableList<Cinema> getCinemaList();

    /**
     * Returns an unmodifiable view of the movies list.
     * This list will not contain any duplicate cinemas.
     */
    ObservableList<Movie> getMovieList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
