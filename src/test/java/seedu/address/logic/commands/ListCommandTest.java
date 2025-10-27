package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Unit tests for ListCommand execution behaviour.
 */
public class ListCommandTest {

    @Test
    public void execute_listAll_showsAllPersons() throws Exception {
        AddressBook ab = getTypicalAddressBook();
        Model model = new ModelManager(ab, new UserPrefs());

        List<Person> expectedBefore = ab.getPersonList();

        ListCommand command = new ListCommand();
        CommandResult result = command.execute(model);

        List<Person> after = model.getFilteredPersonList();
        assertEquals(expectedBefore, after);

        assertTrue(result.getFeedbackToUser() != null && !result.getFeedbackToUser().isEmpty());
    }

    @Test
    public void execute_listByTag_showsOnlyTaggedPersons() throws Exception {
        AddressBook ab = getTypicalAddressBook();
        Model model = new ModelManager(ab, new UserPrefs());

        // build expected list by filtering the typical persons for tag "friends"
        List<Person> expected = ab.getPersonList().stream()
                .filter(p -> p.getTags().stream().anyMatch(t -> t.tagName.equalsIgnoreCase("friends")))
                .collect(Collectors.toList());

        ListCommand command = new ListCommand("friends");
        CommandResult result = command.execute(model);

        List<Person> after = model.getFilteredPersonList();
        assertEquals(expected, after);

        assertTrue(result.getFeedbackToUser() != null && !result.getFeedbackToUser().isEmpty());
    }

    @Test
    public void executeListByTag_noMatches() throws Exception {
        AddressBook ab = getTypicalAddressBook();
        Model model = new ModelManager(ab, new UserPrefs());

        ListCommand command = new ListCommand("thisTagDoesNotExist");
        CommandResult result = command.execute(model);

        List<Person> after = model.getFilteredPersonList();
        assertTrue(after.isEmpty());

        assertEquals(ListCommand.MESSAGE_NO_CONTACTS_STORED, result.getFeedbackToUser());
    }
}
