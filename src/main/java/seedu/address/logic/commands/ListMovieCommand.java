package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MOVIES;

/**
 * Lists all movies in the address book to the user.
 */
public class ListMovieCommand extends Command {

    public static final String COMMAND_WORD = "listmovies";
    public static final String COMMAND_ALIAS = "lm";
    public static final String MESSAGE_SUCCESS = "Listed all movies";


    @Override
    public CommandResult execute() {
        model.updateFilteredMovieList(PREDICATE_SHOW_ALL_MOVIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
