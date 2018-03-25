package seedu.address.logic.commands;

import seedu.address.model.movie.NameContainsKeywordsPredicate;

/**
 * Finds and lists all movies in movie planner whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindMovieCommand extends Command {

    public static final String COMMAND_WORD = "findmovie";
    public static final String COMMAND_ALIAS = "fm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all movies whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " avenger horror nemo";

    private final NameContainsKeywordsPredicate predicate;

    public FindMovieCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredMovieList(predicate);
        return new CommandResult(getMessageForMovieListShownSummary(model.getFilteredMovieList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMovieCommand // instanceof handles nulls
                && this.predicate.equals(((FindMovieCommand) other).predicate)); // state check
    }
}
