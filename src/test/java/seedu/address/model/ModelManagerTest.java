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
import seedu.address.testutil.CinemaBuilder;
import seedu.address.testutil.MoviePlannerBuilder;

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
        MoviePlanner moviePlanner = new MoviePlannerBuilder().withCinema(ALICE).withCinema(BENSON).build();
        MoviePlanner differentMoviePlanner = new MoviePlanner();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(moviePlanner, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(moviePlanner, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different moviePlanner -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentMoviePlanner, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredCinemaList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(moviePlanner, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCinemaList(PREDICATE_SHOW_ALL_CINEMAS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setMoviePlannerName("differentName");
        assertTrue(modelManager.equals(new ModelManager(moviePlanner, differentUserPrefs)));
    }

    @Test
    public void deleteTag_tagNotInUse_modelNotChanged() throws Exception {
        MoviePlanner moviePlannerWithAmy = new MoviePlannerBuilder().withCinema(AMY).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(moviePlannerWithAmy, userPrefs);
        thrown.expect(TagNotFoundException.class);
        modelManager.deleteTag(new Tag(VALID_TAG_UNUSED));

        assertEquals(new ModelManager(moviePlannerWithAmy, userPrefs), modelManager);
    }

    @Test
    public void deleteTag_tagInUseByOneCinema_tagRemoved() throws Exception {
        MoviePlanner moviePlannerWithAmyAndBob = new MoviePlannerBuilder().withCinema(AMY).withCinema(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(moviePlannerWithAmyAndBob, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_HUSBAND));

        Cinema bobHusbandTagRemoved = new CinemaBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        MoviePlanner expectedMoviePlanner = new MoviePlannerBuilder().withCinema(AMY)
                                                                  .withCinema(bobHusbandTagRemoved).build();

        assertEquals(new ModelManager(expectedMoviePlanner, userPrefs), modelManager);
    }

    @Test
    public void deleteTag_tagInUseByMultipleCinema_tagRemoved() throws Exception {
        MoviePlanner moviePlannerWithAmyAndBob = new MoviePlannerBuilder().withCinema(AMY).withCinema(BOB).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(moviePlannerWithAmyAndBob, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_FRIEND));

        Cinema amyFriendTagRemoved = new CinemaBuilder(AMY).withTags().build();
        Cinema bobFriendTagRemoved = new CinemaBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        MoviePlanner expectedMoviePlanner = new MoviePlannerBuilder().withCinema(amyFriendTagRemoved)
                                                                  .withCinema(bobFriendTagRemoved).build();

        assertEquals(new ModelManager(expectedMoviePlanner, userPrefs), modelManager);
    }
}
