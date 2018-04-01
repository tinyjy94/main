package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.commons.events.storage.EncryptionRequestEvent;

public class EncryptCommand extends Command {

    public static final String COMMAND_WORD = "encrypt";
    public static final String COMMAND_ALIAS = "enc";
    public static final String MESSAGE_SUCCESS = "MoviePlanner Encrypted!";
    public static final String MESSAGE_PASSWORDTOOSHORT = "Password should be of 8 characters!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Encrypts MoviePlanner file to prevent data leak.\n "
            + "Parameters: " + PREFIX_PASSWORD + " PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PASSWORD + "dummypass ";

    private String password;

    public EncryptCommand(String password) {
        this.password = password;
    }

    @Override
    public CommandResult execute() {
        //user request to encrypt
        raise(new EncryptionRequestEvent(password));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
