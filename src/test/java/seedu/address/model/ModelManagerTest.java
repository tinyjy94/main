package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CINEMAS;
import static seedu.address.testutil.TypicalCinemas.ALICE;
import static seedu.address.testutil.TypicalCinemas.AMY;
import static seedu.address.testutil.TypicalCinemas.BENSON;
import static seedu.address.testutil.TypicalCinemas.BOB;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.CinemaBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getFilteredCinemaList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredCinemaList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withCinema(ALICE).withCinema(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredCinemaList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCinemaList(PREDICATE_SHOW_ALL_CINEMAS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }

    @Test
    public void deleteTag_tagNotInUse_modelNotChanged() throws Exception {
        AddressBook addressBookWithAmy = new AddressBookBuilder().withCinema(AMY).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(addressBookWithAmy, userPrefs);
        thrown.expect(TagNotFoundException.class);
        modelManager.deleteTag(new Tag(VALID_TAG_UNUSED));

        assertEquals(new ModelManager(addressBookWithAmy, userPrefs), modelManager);
    }

    @Test
    public void deleteTag_tagInUseByOneCinema_tagRemoved() throws Exception {
        AddressBook addressBookWithAmyAndBob = new AddressBookBuilder().withCinema(AMY).withCinema(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(addressBookWithAmyAndBob, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_HUSBAND));

        Cinema bobHusbandTagRemoved = new CinemaBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        AddressBook expectedAddressBook = new AddressBookBuilder().withCinema(AMY)
                                                                  .withCinema(bobHusbandTagRemoved).build();

        assertEquals(new ModelManager(expectedAddressBook, userPrefs), modelManager);
    }

    @Test
    public void deleteTag_tagInUseByMultipleCinema_tagRemoved() throws Exception {
        AddressBook addressBookWithAmyAndBob = new AddressBookBuilder().withCinema(AMY).withCinema(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(addressBookWithAmyAndBob, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_FRIEND));

        Cinema amyFriendTagRemoved = new CinemaBuilder(AMY).withTags().build();
        Cinema bobFriendTagRemoved = new CinemaBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        AddressBook expectedAddressBook = new AddressBookBuilder().withCinema(amyFriendTagRemoved)
                                                                  .withCinema(bobFriendTagRemoved).build();

        assertEquals(new ModelManager(expectedAddressBook, userPrefs), modelManager);
    }
}
