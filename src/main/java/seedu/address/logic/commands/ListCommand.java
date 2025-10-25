package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
/**
 * Lists all persons in the address book or persons with a specific tag.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = "Listing all contacts: list\n\n"
            + "Shows all contacts in your database. You can also filter by tags.\n\n"
            + "Format:\n\n"
            + "list - Shows all contacts\n\n"
            + "list t/TAG - Shows contacts with specific tag (learn about tags in the add command section)\n\n"
            + "Examples:\n\n"
            + "list\n"
            + "list t/priority\n"
            + "list t/designer";

    public static final String MESSAGE_NO_CONTACTS_STORED = "No contacts stored";

    private final String tagName; // null => show all

    public ListCommand() {
        this.tagName = null;
    }

    /**
     * List only persons having this tag name (case-insensitive).
     */
    public ListCommand(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (tagName == null) {
            // show all persons
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        } else {
            final String tagLower = tagName.toLowerCase();
            model.updateFilteredPersonList(person -> person.getTags().stream()
                    .map(t -> t.tagName.toLowerCase())
                    .anyMatch(tn -> tn.equals(tagLower)));
        }

        List<Person> filtered = model.getFilteredPersonList();

        if (filtered.isEmpty()) {
            return new CommandResult(MESSAGE_NO_CONTACTS_STORED);
        }

        String result = filtered.stream()
                .map(p -> formatPersonForList(p, model))
                .collect(Collectors.joining("\n"));

        // keep same behavior as previous ListCommand: return the formatted string
        return new CommandResult(result);
    }

    /**
     * Matches the formatting expected by the tests:
     * "%d. %s | %s | [%s] | [%s] | %s"
     */
    private static String formatPersonForList(Person person, Model model) {
        int index = model.getFilteredPersonList().indexOf(person) + 1;
        return String.format("%d. %s | %s | [%s] | [%s] | %s",
                index,
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getCompany(),
                person.getTags());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof ListCommand)) {
            return false;
        } else {
            ListCommand otherCmd = (ListCommand) other;
            if (this.tagName == null) {
                return otherCmd.tagName == null;
            } else {
                return this.tagName.equalsIgnoreCase(otherCmd.tagName);
            }
        }
    }

    @Override
    public String toString() {
        return ListCommand.class.getCanonicalName() + "{tag=" + tagName + "}";
    }
}
