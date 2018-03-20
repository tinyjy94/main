package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SCREENING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CINEMA_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOVIE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMOFTHEATERS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCREENING_DATE_TIME;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Theater;
import seedu.address.model.cinema.exceptions.ScreeningConflictException;
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
        try {
            theater.addScreeningToTheater(toAdd);
            model.addScreening(toAdd);
        } catch (ScreeningConflictException sce) {
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
            System.out.println(cinemaIndex.getZeroBased() + " " + lastShownCinemaList.size());
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddScreeningCommand // instanceof handles nulls
                && toAdd.equals(((AddScreeningCommand) other).toAdd));
    }
}
