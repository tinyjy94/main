package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.commons.events.storage.DecryptionRequestEvent;

public class DecryptCommand extends Command {
    public static final String COMMAND_WORD = "decrypt";
    public static final String COMMAND_ALIAS = "dec";
    public static final String MESSAGE_SUCCESS = "MoviePlanner Decrypted!";
    public static final String MESSAGE_PASSWORDTOOSHORT = "Password should be of 8 characters!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Decrypts MoviePlanner file to view contents.\n "
            + "Parameters: " + PREFIX_PASSWORD + " PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PASSWORD + "dummypass ";

    private String password;

    public DecryptCommand(String password) {
        this.password = password;
    }

    @Override
    public CommandResult execute() {
        //user request to encrypt
        raise(new DecryptionRequestEvent(password));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
