package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMOFTHEATERS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CINEMAS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Theater;
import seedu.address.model.cinema.exceptions.CinemaNotFoundException;
import seedu.address.model.cinema.exceptions.DuplicateCinemaException;

/**
 * Adds theaters to existing cinema
 */
public class AddTheaterCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtheater";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": add theaters to cinema "
            + "by the index number used in the last cinema listing. "
            + "Existing number of theaters will be added with input value.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NUMOFTHEATERS + "THEATERS\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NUMOFTHEATERS + "3 ";

    public static final String MESSAGE_RESIZE_CINEMA_SUCCESS = "Resized Cinema: %1$s";
    public static final String MESSAGE_NOT_RESIZED = "Number of theater to resize must be provided.";
    public static final String MESSAGE_DUPLICATE_CINEMA = "This cinema already exists in the movie planner.";

    private final Index index;
    private int newTheaters = 0;

    private Cinema cinemaToResize;
    private Cinema resizedCinema;

    /**
     * @param index of the cinema in the filtered cinema list to edit
     * @param NewTheaters to resize the cinema with
     */
    public AddTheaterCommand(Index index, int NewTheaters) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updateCinema(cinemaToResize, resizedCinema);
        } catch (DuplicateCinemaException dce) {
            throw new CommandException(MESSAGE_DUPLICATE_CINEMA);
        } catch (CinemaNotFoundException cnfe) {
            throw new AssertionError("The target cinema cannot be missing");
        }
        model.updateFilteredCinemaList(PREDICATE_SHOW_ALL_CINEMAS);
        return new CommandResult(String.format(MESSAGE_RESIZE_CINEMA_SUCCESS, resizedCinema));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Cinema> lastShownList = model.getFilteredCinemaList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CINEMA_DISPLAYED_INDEX);
        }

        cinemaToResize = lastShownList.get(index.getZeroBased());
        resizedCinema = createResizedCinema(cinemaToResize, newTheaters);
    }

    /**
     * Creates and returns a {@code Cinema} with the details of {@code cinemaToEdit}
     * edited with {@code editCinemaDescriptor}.
     */
    private Cinema createResizedCinema(Cinema cinemaToResize, int newTheaters) {
        assert cinemaToResize != null;
        int oldTheaterSize = cinemaToResize.getTheaters().size();
        ArrayList<Theater> updatedTheaterList = new ArrayList<>();
        for (Theater theaters : cinemaToResize.getTheaters()) {
            updatedTheaterList.add(theaters);
        }

        for (int i = oldTheaterSize; i < newTheaters + oldTheaterSize; i++) {
            updatedTheaterList.add(new Theater(i));
        }

        return new Cinema(cinemaToResize.getName(), cinemaToResize.getPhone(), cinemaToResize.getEmail(),
                cinemaToResize.getAddress(), cinemaToResize.getTags(), updatedTheaterList);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTheaterCommand)) {
            return false;
        }

        // state check
        AddTheaterCommand e = (AddTheaterCommand) other;
        return index.equals(e.index)
                && Objects.equals(cinemaToResize, e.cinemaToResize);
    }

    /**
     * Stores the details to edit the cinema with. Each non-empty field value will replace the
     * corresponding field value of the cinema.
     */
    public static class ResizeCinemaDescriptor {
        private ArrayList<Theater> theaters;

        public ResizeCinemaDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public ResizeCinemaDescriptor(ResizeCinemaDescriptor toCopy) {
            setTheaters(toCopy.theaters);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.theaters);
        }

        /**
         * Sets {@code theaters} to this object's {@code theaters}.
         * A defensive copy of {@code theaters} is used internally.
         */
        public void setTheaters(ArrayList<Theater> theaters) {
            this.theaters = (theaters != null) ? new ArrayList<>(theaters) : null;
        }

        public Optional<List<Theater>> getTheaters() {
            return (theaters != null) ? Optional.of(Collections.unmodifiableList(theaters)) : Optional.empty();
        }

        public int getTheaterSize() {
            return theaters.size();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ResizeCinemaDescriptor)) {
                return false;
            }

            // state check
            ResizeCinemaDescriptor e = (ResizeCinemaDescriptor) other;

            return getTheaters().equals(e.getTheaters());
        }
    }
}
