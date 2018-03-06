package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.UniqueCinemaList;
import seedu.address.model.cinema.exceptions.DuplicateCinemaException;
import seedu.address.model.cinema.exceptions.CinemaNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueCinemaList Cinemas;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        Cinemas = new UniqueCinemaList();
        tags = new UniqueTagList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Cinemas and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setCinemas(List<Cinema> Cinemas) throws DuplicateCinemaException {
        this.Cinemas.setCinemas(Cinemas);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Cinema> syncedCinemaList = newData.getCinemaList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());

        try {
            setCinemas(syncedCinemaList);
        } catch (DuplicateCinemaException e) {
            throw new AssertionError("AddressBooks should not have duplicate Cinemas");
        }
    }

    //// Cinema-level operations

    /**
     * Adds a Cinema to the address book.
     * Also checks the new Cinema's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the Cinema to point to those in {@link #tags}.
     *
     * @throws DuplicateCinemaException if an equivalent Cinema already exists.
     */
    public void addCinema(Cinema c) throws DuplicateCinemaException {
        Cinema Cinema = syncWithMasterTagList(c);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any Cinema
        // in the Cinema list.
        Cinemas.add(Cinema);
    }

    /**
     * Replaces the given Cinema {@code target} in the list with {@code editedCinema}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedCinema}.
     *
     * @throws DuplicateCinemaException if updating the Cinema's details causes the Cinema to be equivalent to
     *      another existing Cinema in the list.
     * @throws CinemaNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Cinema)
     */
    public void updateCinema(Cinema target, Cinema editedCinema)
            throws DuplicateCinemaException, CinemaNotFoundException {
        requireNonNull(editedCinema);

        Cinema syncedEditedCinema = syncWithMasterTagList(editedCinema);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any Cinema
        // in the Cinema list.
        Cinemas.setCinema(target, syncedEditedCinema);
    }

    /**
     *  Updates the master tag list to include tags in {@code Cinema} that are not in the list.
     *  @return a copy of this {@code Cinema} such that every tag in this Cinema points to a Tag object in the master
     *  list.
     */
    private Cinema syncWithMasterTagList(Cinema Cinema) {
        final UniqueTagList CinemaTags = new UniqueTagList(Cinema.getTags());
        tags.mergeFrom(CinemaTags);

        // Create map with values = tag object references in the master list
        // used for checking Cinema tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of Cinema tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        CinemaTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Cinema(
                Cinema.getName(), Cinema.getPhone(), Cinema.getEmail(), Cinema.getAddress(), correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws CinemaNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeCinema(Cinema key) throws CinemaNotFoundException {
        if (Cinemas.remove(key)) {
            return true;
        } else {
            throw new CinemaNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return Cinemas.asObservableList().size() + " Cinemas, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<Cinema> getCinemaList() {
        return Cinemas.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.Cinemas.equals(((AddressBook) other).Cinemas)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(Cinemas, tags);
    }
}
