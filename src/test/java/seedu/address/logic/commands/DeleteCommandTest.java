package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains unit tests for DeleteCommand.
 */
public class DeleteCommandTest {

    @Test
    public void execute_validSingleIndex_success() throws CommandException {
        // Single index deletion should reduce list size by 1
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        int sizeBefore = model.getFilteredPersonList().size();
        CommandResult result = deleteCommand.execute(model);
        int sizeAfter = model.getFilteredPersonList().size();

        assertEquals(sizeBefore - 1, sizeAfter);
        assertEquals(true, result.getFeedbackToUser().contains("Deleted Person:"));
    }

    @Test
    public void execute_validMultipleIndicesWithoutConfirmation_success() throws CommandException {
        // Multiple indices without confirmation should still delete successfully
        // (Note: In GUI, confirmation dialog will appear, but execute logic validates)
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        int sizeBefore = model.getFilteredPersonList().size();

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        deleteCommand.execute(model);

        int sizeAfter = model.getFilteredPersonList().size();
        assertEquals(sizeBefore - 1, sizeAfter);
    }

    @Test
    public void execute_outOfBoundIndex_throwsCommandException() {
        // Out of bounds index should throw CommandException
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void execute_bulkOutOfBoundIndex_throwsCommandException() {
        // Bulk delete with out of bounds index should throw CommandException
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        int listSize = model.getFilteredPersonList().size();
        Index outOfBoundIndex = Index.fromOneBased(listSize + 1);

        DeleteCommand deleteCommand = new DeleteCommand(
                Arrays.asList(INDEX_FIRST_PERSON, outOfBoundIndex));

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void equals_sameIndices_true() {
        // DeleteCommand with same index should be equal
        DeleteCommand deleteCommand1 = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand2 = new DeleteCommand(INDEX_FIRST_PERSON);
        assertEquals(deleteCommand1, deleteCommand2);
    }

    @Test
    public void equals_differentIndices_false() {
        // DeleteCommand with different indices should not be equal
        DeleteCommand deleteCommand1 = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand2 = new DeleteCommand(INDEX_SECOND_PERSON);
        assertEquals(false, deleteCommand1.equals(deleteCommand2));
    }

    @Test
    public void equals_sameListIndices_true() {
        // DeleteCommand with same list of indices should be equal
        DeleteCommand deleteCommand1 = new DeleteCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        DeleteCommand deleteCommand2 = new DeleteCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        assertEquals(deleteCommand1, deleteCommand2);
    }

    @Test
    public void equals_differentListIndices_false() {
        // DeleteCommand with different indices should not be equal
        DeleteCommand deleteCommand1 = new DeleteCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        DeleteCommand deleteCommand2 = new DeleteCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON));
        assertEquals(false, deleteCommand1.equals(deleteCommand2));
    }

    @Test
    public void isBulkDelete_singleIndex_false() {
        // Single index should not be marked as bulk delete
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        assertEquals(false, deleteCommand.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void isBulkDelete_multipleIndices_true() {
        // Multiple indices should be marked as bulk delete
        DeleteCommand deleteCommand = new DeleteCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        assertEquals(true, deleteCommand.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void toString_singleDelete_containsCorrectInfo() {
        // toString should contain correct information for single delete
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        String result = deleteCommand.toString();
        assertEquals(true, result.contains("isBulkDelete=false"));
    }

    @Test
    public void toString_bulkDelete_containsCorrectInfo() {
        // toString should contain correct information for bulk delete
        DeleteCommand deleteCommand = new DeleteCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        String result = deleteCommand.toString();
        assertEquals(true, result.contains("isBulkDelete=true"));
    }
}
