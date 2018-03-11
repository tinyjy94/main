package seedu.address.model.cinema;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.cinema.exceptions.TheaterNotFoundException;

/**
 * A list of theaters that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Theater#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTheaterList implements Iterable<Theater> {
    private final ObservableList<Theater> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty TagList.
     */
    public UniqueTheaterList() {}

    /**
     * Creates a UniqueTagList using given tags.
     * Enforces no nulls.
     */
    public UniqueTheaterList(Set<Theater> theaters) {
        requireAllNonNull(theaters);
        internalList.addAll(theaters);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all theaters in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Theater> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Theaters in this list with those in the argument theater list.
     */
    public void setTheaters(Set<Theater> theaters) {
        requireAllNonNull(theaters);
        internalList.setAll(theaters);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every tag in the argument list exists in this object.
     */
    public void mergeFrom(UniqueTheaterList from) {
        final Set<Theater> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(theater -> !alreadyInside.contains(theater))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent theater as the given argument.
     */
    public boolean contains(Theater toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a theater to the list.
     * @throws DuplicateTheaterException if the cinema to add is a duplicate of an existing theater in the list.
     */
    public void add(Theater toAdd) throws DuplicateTheaterException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTheaterException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes the equivalent theater from the list.
     *
     * @throws TheaterNotFoundException if no such theater could be found in the list.
     */
    public boolean remove(Theater toRemove) throws TheaterNotFoundException {
        requireNonNull(toRemove);
        final boolean theaterFoundAndDeleted = internalList.remove(toRemove);
        if (!theaterFoundAndDeleted) {
            throw new TheaterNotFoundException();
        }
        return theaterFoundAndDeleted;
    }

    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setTheater(Set<Theater> theaters) {
        requireAllNonNull(theaters);
        internalList.setAll(theaters);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Theater> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Theater> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTheaterList // instanceof handles nulls
                && this.internalList.equals(((UniqueTheaterList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTheaterException extends DuplicateDataException {
        protected DuplicateTheaterException() {
            super("Operation would result in duplicate theaters");
        }
    }
}
