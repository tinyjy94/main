package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalCinemas.ALICE;
import static seedu.address.testutil.TypicalCinemas.AMY;
import static seedu.address.testutil.TypicalCinemas.BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.address.testutil.TypicalCinemas.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.CinemaBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getCinemaList());
        assertEquals(Collections.emptyList(), addressBook.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateCinemas_throwsAssertionError() {
        // Repeat ALICE twice
        List<Cinema> newCinemas = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        AddressBookStub newData = new AddressBookStub(newCinemas, newTags);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }

    @Test
    public void getCinemaList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getCinemaList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getTagList().remove(0);
    }

    @Test
    public void updateCinema_modifyDetails_cinemaAndTagListUpdated() throws Exception {
        AddressBook addressBookBobChangeToAmy = new AddressBookBuilder().withCinema(BOB).build();
        AddressBook AddressBookWithAmy = new AddressBookBuilder().withCinema(AMY).build();

        addressBookBobChangeToAmy.updateCinema(BOB, AMY);

        assertEquals(AddressBookWithAmy, addressBookBobChangeToAmy);
    }

    @Test
    public void removeTag_tagNotInUse_addressBookNotChanged() throws Exception {
        AddressBook addressBookWithAmy = new AddressBookBuilder().withCinema(AMY).build();
        thrown.expect(TagNotFoundException.class);
        addressBookWithAmy.removeTag(new Tag(VALID_TAG_UNUSED));
        AddressBook expectedAddressBook = new AddressBookBuilder().withCinema(AMY).build();

        assertEquals(expectedAddressBook, addressBookWithAmy);
    }

    @Test
    public void removeTag_tagInUseByOneCinema_tagRemoved() throws Exception {
        AddressBook addressBookWithAmyAndBob = new AddressBookBuilder().withCinema(AMY).withCinema(BOB).build();
        addressBookWithAmyAndBob.removeTag(new Tag(VALID_TAG_HUSBAND));

        Cinema bobHusbandTagRemoved = new CinemaBuilder(BOB).withTags(VALID_TAG_FRIEND).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withCinema(AMY)
                                                                  .withCinema(bobHusbandTagRemoved).build();

        assertEquals(expectedAddressBook, addressBookWithAmyAndBob);
    }

    @Test
    public void removeTag_tagInUseByMultipleCinema_tagRemoved() throws Exception {
        AddressBook addressBookWithAmyAndBob = new AddressBookBuilder().withCinema(AMY).withCinema(BOB).build();
        addressBookWithAmyAndBob.removeTag(new Tag(VALID_TAG_FRIEND));

        Cinema amyFriendTagRemoved = new CinemaBuilder(AMY).withTags().build();
        Cinema bobFriendTagRemoved = new CinemaBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();

        AddressBook expectedAddressBook = new AddressBookBuilder().withCinema(amyFriendTagRemoved)
                                                                  .withCinema(bobFriendTagRemoved).build();

        assertEquals(expectedAddressBook, addressBookWithAmyAndBob);
    }

    /**
     * A stub ReadOnlyAddressBook whose cinemas and tags lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Cinema> cinemas = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        AddressBookStub(Collection<Cinema> cinemas, Collection<? extends Tag> tags) {
            this.cinemas.setAll(cinemas);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Cinema> getCinemaList() {
            return cinemas;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
