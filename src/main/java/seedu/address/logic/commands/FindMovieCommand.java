package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.movie.MovieName;
import seedu.address.model.movie.NameContainsKeywordsPredicate;
import seedu.address.model.movie.StartDate;
import seedu.address.model.tag.Tag;

/**
 * Finds and lists all movies in movie planner whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindMovieCommand extends Command {

    public static final String COMMAND_WORD = "findmovie";
    public static final String COMMAND_ALIAS = "fm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all movies whose names contain any of "
            + "the specified keywords (case-sensitive).\n"
            + "Parameters: "
            + PREFIX_NAME + "KEYWORDS "
            + "Example: " + COMMAND_WORD + " "+ PREFIX_NAME + " avenger horror nemo";

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

    /**
     * Stores the details for movies to find. Each non-empty field value will
     * be the search conditions.
     */
    public static class FindMovieDescriptor {
        private MovieName movieName;
        private StartDate startDate;
        private Set<Tag> tags;

        public FindMovieDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public FindMovieDescriptor(FindMovieDescriptor toCopy) {
            setName(toCopy.movieName);
            setStartDate(toCopy.startDate);
            setTags(toCopy.tags);
        }


        public void setName(MovieName movieName) {
            this.movieName = movieName;
        }

        public Optional<MovieName> getName() {
            return Optional.ofNullable(movieName);
        }

        public void setStartDate(StartDate startDate) {
            this.startDate = startDate;
        }

        public Optional<StartDate> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FindMovieDescriptor)) {
                return false;
            }

            // state check
            FindMovieDescriptor e = (FindMovieDescriptor) other;

            return getName().equals(e.getName())
                    && getStartDate().equals(e.getStartDate())
                    && getTags().equals(e.getTags());
        }
    }
}
