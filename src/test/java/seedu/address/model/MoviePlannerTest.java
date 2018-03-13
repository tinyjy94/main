package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.address.testutil.TypicalCinemas.ALICE;
import static seedu.address.testutil.TypicalCinemas.AMY;
import static seedu.address.testutil.TypicalCinemas.BOB;
import static seedu.address.testutil.TypicalCinemas.getTypicalMoviePlanner;

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
import seedu.address.model.cinema.Theater;
import seedu.address.model.movie.Movie;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;
import seedu.address.testutil.CinemaBuilder;
import seedu.address.testutil.MoviePlannerBuilder;

public class MoviePlannerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final MoviePlanner moviePlanner = new MoviePlanner();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), moviePlanner.getCinemaList());
        assertEquals(Collections.emptyList(), moviePlanner.getTagList());
        assertEquals(Collections.emptyList(), moviePlanner.getTheaterList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        moviePlanner.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyMoviePlanner_replacesData() {
        MoviePlanner newData = getTypicalMoviePlanner();
        moviePlanner.resetData(newData);
        assertEquals(newData, moviePlanner);
    }

    @Test
    public void resetData_withDuplicateCinemas_throwsAssertionError() {
        // Repeat ALICE twice
        List<Cinema> newCinemas = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<Movie> newMovies = new ArrayList<>();
        List<Theater> newTheaters = new ArrayList<>(ALICE.getTheaters());
        MoviePlannerStub newData = new MoviePlannerStub(newCinemas, newTags, newTheaters, newMovies);

        thrown.expect(AssertionError.class);
        moviePlanner.resetData(newData);
    }

    @Test
    public void getCinemaList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        moviePlanner.getCinemaList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        moviePlanner.getTagList().remove(0);
    }

    @Test
    public void updateCinema_modifyDetails_cinemaAndTagListUpdated() throws Exception {
        MoviePlanner moviePlannerBobChangeToAmy = new MoviePlannerBuilder().withCinema(BOB).build();
        MoviePlanner moviePlannerWithAmy = new MoviePlannerBuilder().withCinema(AMY).build();

        moviePlannerBobChangeToAmy.updateCinema(BOB, AMY);

        assertEquals(moviePlannerWithAmy, moviePlannerBobChangeToAmy);
    }

    @Test
    public void removeTag_tagNotInUse_moviePlannerNotChanged() throws Exception {
        MoviePlanner moviePlannerWithAmy = new MoviePlannerBuilder().withCinema(AMY).build();
        thrown.expect(TagNotFoundException.class);
        moviePlannerWithAmy.removeTag(new Tag(VALID_TAG_UNUSED));
        MoviePlanner expectedMoviePlanner = new MoviePlannerBuilder().withCinema(AMY).build();

        assertEquals(expectedMoviePlanner, moviePlannerWithAmy);
    }

    @Test
    public void removeTag_tagInUseByOneCinema_tagRemoved() throws Exception {
        MoviePlanner moviePlannerWithAmyAndBob = new MoviePlannerBuilder().withCinema(AMY).withCinema(BOB).build();
        moviePlannerWithAmyAndBob.removeTag(new Tag(VALID_TAG_HUSBAND));

        Cinema bobHusbandTagRemoved = new CinemaBuilder(BOB).withTags(VALID_TAG_FRIEND).build();
        MoviePlanner expectedMoviePlanner = new MoviePlannerBuilder().withCinema(AMY)
                                                                  .withCinema(bobHusbandTagRemoved).build();

        assertEquals(expectedMoviePlanner, moviePlannerWithAmyAndBob);
    }

    @Test
    public void removeTag_tagInUseByMultipleCinema_tagRemoved() throws Exception {
        MoviePlanner moviePlannerWithAmyAndBob = new MoviePlannerBuilder().withCinema(AMY).withCinema(BOB).build();
        moviePlannerWithAmyAndBob.removeTag(new Tag(VALID_TAG_FRIEND));

        Cinema amyFriendTagRemoved = new CinemaBuilder(AMY).withTags().build();
        Cinema bobFriendTagRemoved = new CinemaBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();

        MoviePlanner expectedMoviePlanner = new MoviePlannerBuilder().withCinema(amyFriendTagRemoved)
                                                                  .withCinema(bobFriendTagRemoved).build();

        assertEquals(expectedMoviePlanner, moviePlannerWithAmyAndBob);
    }

    /**
     * A stub ReadOnlyMoviePlanner whose cinemas and tags lists can violate interface constraints.
     */
    private static class MoviePlannerStub implements ReadOnlyMoviePlanner {
        private final ObservableList<Cinema> cinemas = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();
        private final ObservableList<Movie> movies = FXCollections.observableArrayList();
        private final ObservableList<Theater> theaters = FXCollections.observableArrayList();

        MoviePlannerStub(Collection<Cinema> cinemas, Collection<? extends Tag> tags,
                        Collection<Theater> theaters, Collection<Movie> movies) {
            this.cinemas.setAll(cinemas);
            this.tags.setAll(tags);
            this.movies.setAll(movies);
            this.theaters.setAll(theaters);
        }

        @Override
        public ObservableList<Cinema> getCinemaList() {
            return cinemas;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }

        @Override
        public ObservableList<Movie> getMovieList() {
            return movies;
        }

        @Override
        public ObservableList<Theater> getTheaterList() {
            return theaters;
        }
    }

}
