package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.commons.util.SecurityUtil;

public class EncryptCommand extends Command {
    public static final String COMMAND_WORD = "encrypt";
    public static final String COMMAND_ALIAS = "enc";
    public static final String MESSAGE_SUCCESS = "MoviePlanner Encrypted!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Encrypts MoviePlanner file " +
            " to prevent data leak.\n "
            + "Parameters: " + PREFIX_PASSWORD + " PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PASSWORD + "dummypass ";

    @Override
    public CommandResult execute() {
        SecurityUtil.encrypt();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
