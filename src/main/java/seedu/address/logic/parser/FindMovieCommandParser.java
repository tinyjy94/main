package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindMovieCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.movie.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindMovieCommand object
 */
public class FindMovieCommandParser implements Parser<FindMovieCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMovieCommand
     * and returns an FindMovieCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindMovieCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMovieCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindMovieCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
