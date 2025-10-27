package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validSingleIndex_success() throws CommandException {
        Model tempModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        int sizeBefore = tempModel.getFilteredPersonList().size();
        CommandResult result = deleteCommand.execute(tempModel);
        int sizeAfter = tempModel.getFilteredPersonList().size();

        assertEquals(sizeBefore - 1, sizeAfter);
        assertEquals(true, result.getFeedbackToUser().contains("Deleted Person:"));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Model tempModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(999);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertThrows(CommandException.class, () -> deleteCommand.execute(tempModel));
    }

    @Test
    public void execute_invalidBulkIndices_throwsCommandException() {
        Model tempModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Index outOfBoundIndex = Index.fromOneBased(999);
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(INDEX_FIRST_PERSON, outOfBoundIndex));

        assertThrows(CommandException.class, () -> deleteCommand.execute(tempModel));
    }

    @Test
    public void equals_sameIndices_true() {
        DeleteCommand deleteCommand1 = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand2 = new DeleteCommand(INDEX_FIRST_PERSON);
        assertEquals(deleteCommand1, deleteCommand2);
    }

    @Test
    public void equals_differentIndices_false() {
        DeleteCommand deleteCommand1 = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteCommand2 = new DeleteCommand(INDEX_SECOND_PERSON);
        assertEquals(false, deleteCommand1.equals(deleteCommand2));
    }

    @Test
    public void equals_sameListIndices_true() {
        DeleteCommand deleteCommand1 = new DeleteCommand(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        DeleteCommand deleteCommand2 = new DeleteCommand(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        assertEquals(deleteCommand1, deleteCommand2);
    }

    @Test
    public void isBulkDelete_singleIndex_false() {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        assertEquals(false, deleteCommand.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void isBulkDelete_multipleIndices_true() {
        DeleteCommand deleteCommand = new DeleteCommand(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        assertEquals(true, deleteCommand.toString().contains("isBulkDelete=true"));
    }
}
