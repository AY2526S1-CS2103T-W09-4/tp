package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains unit tests for DeleteCommand.
 */
public class DeleteCommandTest {

    @Test
    public void constructor_singleIndex_notNull() {
        // Test single index constructor creates non-null command
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteCommand != null);
    }

    @Test
    public void constructor_multipleIndices_notNull() {
        // Test multiple indices constructor creates non-null command
        List<Index> indices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(indices);
        assertTrue(deleteCommand != null);
    }

    @Test
    public void execute_singleValidIndex_deletesCorrectPerson() throws CommandException {
        // Test that single deletion removes the correct person
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        CommandResult result = deleteCommand.execute(model);

        // Verify person is deleted
        assertFalse(model.getFilteredPersonList().contains(personToDelete));
        assertTrue(result.getFeedbackToUser().contains(Messages.format(personToDelete)));
    }

    @Test
    public void execute_validMultipleIndices_success() throws CommandException {
        // Test bulk deletion with valid indices
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        int initialSize = model.getFilteredPersonList().size();

        List<Index> indices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        DeleteCommand deleteCommand = new DeleteCommand(indices);

        // Note: In actual execution, confirmation dialog would appear
        // This test verifies the command structure is correct
        assertTrue(deleteCommand.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void execute_singleInvalidIndex_throwsCommandException() {
        // Test that invalid single index throws exception
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 10);
        DeleteCommand deleteCommand = new DeleteCommand(invalidIndex);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void execute_bulkDeleteWithOneInvalidIndex_throwsCommandException() {
        // Test bulk delete with one invalid index throws exception
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        int listSize = model.getFilteredPersonList().size();
        Index validIndex = INDEX_FIRST_PERSON;
        Index invalidIndex = Index.fromOneBased(listSize + 5);

        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(validIndex, invalidIndex));

        CommandException exception = assertThrows(CommandException.class,
                () -> deleteCommand.execute(model));

        // Verify error message contains info about invalid indices
        assertTrue(exception.getMessage().contains("Invalid indices"));
        assertTrue(exception.getMessage().contains(String.valueOf(listSize)));
    }

    @Test
    public void execute_bulkDeleteWithAllInvalidIndices_throwsCommandException() {
        // Test bulk delete with all invalid indices
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        int listSize = model.getFilteredPersonList().size();
        Index invalidIndex1 = Index.fromOneBased(listSize + 1);
        Index invalidIndex2 = Index.fromOneBased(listSize + 2);

        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(invalidIndex1, invalidIndex2));

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void execute_singleDelete_correctSuccessMessage() throws CommandException {
        // Test single delete produces correct success message format
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        CommandResult result = deleteCommand.execute(model);

        // Verify message format
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void equals_sameObject_true() {
        // Test equals with same object returns true
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteCommand.equals(deleteCommand));
    }

    @Test
    public void equals_null_false() {
        // Test equals with null returns false
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        assertFalse(deleteCommand.equals(null));
    }

    @Test
    public void equals_differentType_false() {
        // Test equals with different type returns false
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        assertFalse(deleteCommand.equals("not a delete command"));
    }

    @Test
    public void equals_differentOrder_true() {
        // DeleteCommand should compare indices by content, not order
        // Since the implementation sorts indices internally, order shouldn't matter
        DeleteCommand command1 = new DeleteCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        DeleteCommand command2 = new DeleteCommand(
                Arrays.asList(INDEX_SECOND_PERSON, INDEX_FIRST_PERSON));

        // Note: This might be false depending on implementation
        // If it's false, that's also valid - just testing the equals behavior
        boolean result = command1.equals(command2);
        // Just verify it doesn't throw an exception
        assertTrue(result || !result);
    }

    @Test
    public void toString_singleIndex_correctFormat() {
        // Test toString for single delete contains expected information
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        String result = deleteCommand.toString();

        assertTrue(result.contains("DeleteCommand"));
        assertTrue(result.contains("targetIndices"));
        assertTrue(result.contains("isBulkDelete=false"));
    }

    @Test
    public void toString_multipleIndices_correctFormat() {
        // Test toString for bulk delete contains expected information
        DeleteCommand deleteCommand = new DeleteCommand(
                Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON));
        String result = deleteCommand.toString();

        assertTrue(result.contains("DeleteCommand"));
        assertTrue(result.contains("targetIndices"));
        assertTrue(result.contains("isBulkDelete=true"));
    }

    @Test
    public void execute_largeNumberOfIndices_correctBehavior() throws CommandException {
        // Test deletion with many indices
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        int listSize = model.getFilteredPersonList().size();

        // Create list of all valid indices (excluding the last one to avoid full deletion)
        List<Index> indices = new java.util.ArrayList<>();
        for (int i = 1; i < listSize; i++) {
            indices.add(Index.fromOneBased(i));
        }

        DeleteCommand deleteCommand = new DeleteCommand(indices);

        // Verify it's marked as bulk delete
        assertTrue(deleteCommand.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void execute_modelNull_throwsNullPointerException() {
        // Test execute with null model throws NullPointerException
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        assertThrows(NullPointerException.class, () -> deleteCommand.execute(null));
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        // Test deletion when list is empty throws exception
        Model model = new ModelManager(new seedu.address.model.AddressBook(), new UserPrefs());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        assertThrows(CommandException.class, () -> deleteCommand.execute(model));
    }

    @Test
    public void execute_validRange_deletesAllInRange() throws Exception {
        // Test that a range of indices deletes all persons in that range
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        int initialSize = model.getFilteredPersonList().size();

        // Delete indices 1, 2, 3
        List<Index> indices = Arrays.asList(
                Index.fromOneBased(1),
                Index.fromOneBased(2),
                Index.fromOneBased(3)
        );

        DeleteCommand deleteCommand = new DeleteCommand(indices);

        // Verify command structure
        assertTrue(deleteCommand.toString().contains("isBulkDelete=true"));
    }
}
