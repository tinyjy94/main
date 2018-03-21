package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SCREENING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CINEMA_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOVIE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMOFTHEATERS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCREENING_DATE_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Theater;
import seedu.address.model.movie.Movie;
import seedu.address.model.screening.Screening;

/**
 * Adds a movie screening to a cinema theater.
 */
public class AddScreeningCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addscreening";
    public static final String COMMAND_ALIAS = "as";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a movie screening to a cinema theater. "
            + "Parameters: "
            + PREFIX_MOVIE_INDEX + "MOVIE_INDEX "
            + PREFIX_CINEMA_INDEX + "CINEMA_INDEX "
            + PREFIX_NUMOFTHEATERS + "THEATER_NUMBER "
            + PREFIX_SCREENING_DATE_TIME + "SCREEN_DATE_TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MOVIE_INDEX + "1 "
            + PREFIX_CINEMA_INDEX + "2 "
            + PREFIX_NUMOFTHEATERS + "3 "
            + PREFIX_SCREENING_DATE_TIME + "13/03/2018 13:35";

    public static final String MESSAGE_SUCCESS = "New screening added: %1$s";

    private final Index movieIndex;
    private final Index cinemaIndex;
    private final int theaterNumber;
    private final LocalDateTime screeningDateTime;

    private Screening toAdd;

    /**
     * Creates an AddScreeningCommand to add the specified {@code Screening}
     */
    public AddScreeningCommand(Index movieIndex, Index cinemaIndex,
                               int theaterNumber, LocalDateTime screeningDateTime) {
        this.movieIndex = movieIndex;
        this.cinemaIndex = cinemaIndex;
        this.theaterNumber = theaterNumber;
        this.screeningDateTime = screeningDateTime;
    }

    /**
     * Adds a screening to a cinema theater
     * @return CommandResult on successful add screening
     * @throws CommandException
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        Movie movie = getValidMovie();
        Cinema cinema = getValidCinema();
        Theater theater = getValidTheater(cinema);
        toAdd = new Screening(movie, cinema, theater, screeningDateTime);

        ArrayList<Screening> screeningList = theater.getScreeningList();

        if (isSlotAvailable(screeningList) && canAddMovie(movie)) {
            theater.addScreeningToTheater(toAdd);
            theater.sortScreeningList();
            model.addScreening(toAdd);
        } else {
            throw new CommandException(MESSAGE_INVALID_SCREENING);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /**
     * @return a valid movie based on the movie index
     */
    private Movie getValidMovie() throws CommandException {
        List<Movie> lastShownMovieList = model.getFilteredMovieList();

        if (movieIndex.getZeroBased() >= lastShownMovieList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MOVIE_DISPLAYED_INDEX);
        }

        Movie movie = lastShownMovieList.get(movieIndex.getZeroBased());
        return movie;
    }

    /**
     * @return a valid cinema based on the cinema index
     */
    private Cinema getValidCinema() throws CommandException {
        List<Cinema> lastShownCinemaList = model.getFilteredCinemaList();

        if (cinemaIndex.getZeroBased() >= lastShownCinemaList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CINEMA_DISPLAYED_INDEX);
        }

        Cinema cinema = lastShownCinemaList.get(cinemaIndex.getZeroBased());
        return cinema;
    }

    /**
     * @return a valid theater based on the cinema provided and theater number
     */
    private Theater getValidTheater(Cinema cinema) throws CommandException {
        int theaterIndex = theaterNumber - 1;

        if (theaterIndex >= cinema.getTheaters().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEATER_NUMBER);
        }

        Theater theater = cinema.getTheaters().get(theaterIndex);
        return theater;
    }

    /**
     * Checks that the screening date of the movie is on or after the release date of the movie
     * @return true if screening date is
     */
    private boolean canAddMovie(Movie movie) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate toAddDate = toAdd.getScreeningDateTime().toLocalDate();
        LocalDate releaseDate = LocalDate.parse(movie.getStartDate().toString(), dtf);
        return toAddDate.equals(releaseDate) || toAddDate.isAfter(releaseDate);
    }

    /**
     * Checks that the screening can fit into the schedule of the theater.
     * @param screeningList list of screenings
     * @return true if the screening can fit into the schedule
     */
    private boolean isSlotAvailable(ArrayList<Screening> screeningList) {
        int totalScreeningsWithSameDate = getTotalScreeningsWithSameDate(screeningList);

        if (totalScreeningsWithSameDate == 0) {
            return true;
        } else if (totalScreeningsWithSameDate == 1) {
            return hasNoConflictWithOneOtherScreening(screeningList);
        } else {
            return hasNoConflictWithBeforeAndAfter(totalScreeningsWithSameDate, screeningList);
        }
    }

    /**
     * Checks that the screening does not conflict with one other screening
     * @param screeningList list of screenings
     * @return true if no conflict
     */
    private boolean hasNoConflictWithOneOtherScreening(ArrayList<Screening> screeningList) {
        for (Screening s: screeningList) {
            if (isSameScreeningDate(toAdd, s)) {
                return isScreenTimeOnOrBefore(s) || isScreenTimeOnOrAfter(s);
            }
        }
        return false;
    }

    /**
     * Checks that the screening does not conflict with the screening before or screening after it
     * @param totalScreenings number of screenings with the same date
     * @param screeningList list of screenings
     * @return true if no conflict
     */
    private boolean hasNoConflictWithBeforeAndAfter(int totalScreenings, ArrayList<Screening> screeningList) {
        int count = 0;
        boolean hasNoConflict = false;
        Screening screeningBefore = screeningList.get(0);

        if (isSameScreeningDate(toAdd , screeningBefore)) {
            count++;
        }

        for (int i = 1; i < screeningList.size(); i++) {
            Screening currentScreening = screeningList.get(i);
            if (hasNoConflict == true) {
                break;
            }

            //first screening
            if (count == 1 && isScreenTimeOnOrBefore(currentScreening)) {
                return true;
            } else if (isSameScreeningDate(currentScreening, toAdd)) {
                count++;
                // last screening
                if (count == totalScreenings && isScreenTimeOnOrAfter(currentScreening)) {
                    return true;
                // screening in between
                } else {
                    hasNoConflict = isScreenTimeOnOrAfter(screeningBefore)
                            && isScreenTimeOnOrBefore(currentScreening);
                }
            }
            screeningBefore = currentScreening;
        }
        return hasNoConflict;
    }

    /**
     * Checks that the start time of the screening toAdd is on or after the end time of the screening before it
     * @return true if the start time of the screening is on or after the end time of the screening before it
     */
    private boolean isScreenTimeOnOrAfter(Screening screeningBefore) {
        LocalTime toAddTime = toAdd.getScreeningDateTime().toLocalTime();
        LocalTime screeningBeforeTime = screeningBefore.getScreeningEndDateTime().toLocalTime();
        if (toAddTime.isAfter(screeningBeforeTime) || toAddTime.equals(screeningBeforeTime)) {
            return true;
        }
        return false;
    }

    /**
     * Checks that the end time of the screening toAdd is on or before the start time of the screening after it
     * @return true if the end time of the screening is before the start time of the screening after it
     */
    private boolean isScreenTimeOnOrBefore(Screening screeningAfter) {
        LocalTime toAddTime = toAdd.getScreeningEndDateTime().toLocalTime();
        LocalTime screeningAfterTime = screeningAfter.getScreeningDateTime().toLocalTime();
        if (toAddTime.isBefore(screeningAfterTime) || toAddTime.equals(screeningAfterTime)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if two screenings have the same date
     * @return true if both screenings have the same date
     */
    private boolean isSameScreeningDate(Screening s1, Screening s2) {
        return s1.getScreeningDateTime().toLocalDate().equals(s2.getScreeningDateTime().toLocalDate());
    }

    /**
     * Calculates the total number of screenings with the same screening date
     * @param screeningList list of screenings
     * @return total number of screenings
     */
    private int getTotalScreeningsWithSameDate(ArrayList<Screening> screeningList) {
        int count = 0;
        for (Screening s: screeningList) {
            if (isSameScreeningDate(toAdd, s)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddScreeningCommand // instanceof handles nulls
                && toAdd.equals(((AddScreeningCommand) other).toAdd));
    }
}
