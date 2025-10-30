package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes one or more persons from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes person(s) identified by index number(s) from the displayed person list.\n"
            + "Parameters: INDEX or INDEX_RANGE (must be positive integers)\n"
            + "  - Single index: " + COMMAND_WORD + " 1\n"
            + "  - Multiple indices: " + COMMAND_WORD + " 1,3,5\n"
            + "  - Index ranges: " + COMMAND_WORD + " 1,3,5-7,10\n"
            + "Example: " + COMMAND_WORD + " 1,3,5-7";

    /** Success message for single person deletion */
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    /** Success message for multiple person deletion */
    public static final String MESSAGE_DELETE_PERSONS_SUCCESS = "Deleted %1$d person(s):\n%2$s";

    /** Confirmation dialog message for bulk deletion */
    public static final String MESSAGE_DELETE_CONFIRMATION = "You are about to delete %d person(s). Continue?";

    /** Message shown when user cancels bulk deletion */
    public static final String MESSAGE_DELETE_CANCELLED = "Deletion cancelled.";

    /** Error message when indices are out of range */
    public static final String MESSAGE_INVALID_INDICES = "Invalid indices: %s (person list has %d entries)";

    /** List of indices to delete (1-based, matching display order) */
    private final List<Index> targetIndices;

    /** Flag to track if this is a bulk delete operation */
    private final boolean isBulkDelete;

    /**
     * Creates a DeleteCommand for deleting a single person.
     *
     * @param targetIndex the 1-based index of the person to delete
     * @throws NullPointerException if targetIndex is null
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndices = new ArrayList<>();
        this.targetIndices.add(targetIndex);
        this.isBulkDelete = false;
    }

    /**
     * Creates a DeleteCommand for deleting multiple persons.
     *
     * @param targetIndices the list of 1-based indices of persons to delete
     * @throws NullPointerException if targetIndices is null
     */
    public DeleteCommand(List<Index> targetIndices) {
        this.targetIndices = new ArrayList<>(targetIndices);
        this.isBulkDelete = targetIndices.size() > 1;
    }

    /**
     * Executes the delete command.
     *
     * @param model the model to operate on, containing the address book
     * @return the result of the command execution with feedback message
     * @throws CommandException if indices are invalid or out of range
     * @throws NullPointerException if model is null
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        // Validate all indices
        List<Integer> invalidIndices = new ArrayList<>();
        for (Index idx : targetIndices) {
            if (idx.getZeroBased() >= lastShownList.size()) {
                invalidIndices.add(idx.getOneBased());
            }
        }

        // Handle invalid indices
        if (!invalidIndices.isEmpty()) {
            if (isBulkDelete) {
                // Bulk delete: report all invalid indices
                String invalidMsg = String.join(", ", invalidIndices.stream()
                        .map(String::valueOf).toArray(String[]::new));
                throw new CommandException(String.format(MESSAGE_INVALID_INDICES,
                        invalidMsg, lastShownList.size()));
            } else {
                // Single delete
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        // Request confirmation for bulk operations
        if (isBulkDelete) {
            if (!getConfirmation(targetIndices.size())) {
                return new CommandResult(MESSAGE_DELETE_CANCELLED);
            }
        }

        // Sort indices in descending order to avoid index shifting
        List<Index> sortedIndices = new ArrayList<>(targetIndices);
        sortedIndices.sort((a, b) -> Integer.compare(b.getZeroBased(), a.getZeroBased()));

        List<String> deletedPersons = new ArrayList<>();

        // Delete each person
        for (Index idx : sortedIndices) {
            if (idx.getZeroBased() < lastShownList.size()) {
                Person personToDelete = lastShownList.get(idx.getZeroBased());
                model.deletePerson(personToDelete);
                deletedPersons.add(Messages.format(personToDelete));
            }
        }

        // Commit changes to model
        model.commitAddressBook();

        // Generate result message
        String resultMessage;
        if (isBulkDelete) {
            String deletedList = String.join("\n  • ", deletedPersons);
            resultMessage = String.format(MESSAGE_DELETE_PERSONS_SUCCESS,
                    deletedPersons.size(), "  • " + deletedList);
        } else {
            resultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPersons.get(0));
        }

        return new CommandResult(resultMessage);
    }

    /**
     * Shows a confirmation dialog for bulk deletion.
     *
     * @param count the number of persons to delete
     * @return true if user clicked YES, false if user clicked NO or closed dialog
     */
    private boolean getConfirmation(int count) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Delete " + count + " Contact(s)?");
        alert.setContentText(String.format(MESSAGE_DELETE_CONFIRMATION, count));
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }

    /**
     * Checks if two DeleteCommand objects are equal.
     *
     * @param other the object to compare with
     * @return true if both commands delete the same indices, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteCommand)) {
            return false;
        }
        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    /**
     * Returns a string representation of the DeleteCommand.
     *
     * @return a string showing the command type, target indices, and bulk flag
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .add("isBulkDelete", isBulkDelete)
                .toString();
    }
}
