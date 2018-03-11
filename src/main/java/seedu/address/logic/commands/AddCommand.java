package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_THEATER;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.exceptions.DuplicateCinemaException;

/**
 * Adds a cinema to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a cinema to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_THEATER + "5 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New cinema added: %1$s";
    public static final String MESSAGE_DUPLICATE_CINEMA = "This cinema already exists in the address book";

    private final Cinema toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Cinema}
     */
    public AddCommand(Cinema cinema) {
        requireNonNull(cinema);
        toAdd = cinema;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addCinema(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateCinemaException e) {
            throw new CommandException(MESSAGE_DUPLICATE_CINEMA);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
