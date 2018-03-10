package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.movie.Movie;
import seedu.address.model.movie.exceptions.DuplicateMovieException;

/**
 * Adds a movie to the address book.
 */
public class AddMovieCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addmovie";
    public static final String COMMAND_ALIAS = "am";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a movie to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "The Incredibles";

    public static final String MESSAGE_SUCCESS = "New movie added: %1$s";
    public static final String MESSAGE_DUPLICATE_MOVIE = "This movie already exists in the address book";

    private final Movie toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Movie}
     */
    public AddMovieCommand(Movie movie) {
        requireNonNull(movie);
        toAdd = movie;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addMovie(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateMovieException e) {
            throw new CommandException(MESSAGE_DUPLICATE_MOVIE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
