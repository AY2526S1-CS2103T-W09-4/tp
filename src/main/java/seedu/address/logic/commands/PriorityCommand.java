package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;

/**
 * Sets or updates the priority level for an existing person in the address book.
 */
public class PriorityCommand extends Command {

    public static final String COMMAND_WORD = "priority";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the priority level for the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PRIORITY + "PRIORITY (HIGH/MEDIUM/LOW or 1-5)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PRIORITY + "HIGH";
            + "To clear priority: " + COMMAND_WORD + " INDEX pr/\n"
    public static final String MESSAGE_SET_PRIORITY_SUCCESS = "Set priority for Contact: %1$s";
    public static final String MESSAGE_REMOVE_PRIORITY_SUCCESS = "Removed priority from Contact: %1$s";
    public static final String MESSAGE_CLEAR_SUCCESS = "Cleared priority for: %s";
    private final Index index;
    private final Priority priority;

    /**
     * @param index of the person in the filtered person list to set priority
     * @param priority priority level to set for the person
     */
    public PriorityCommand(Index index, Priority priority) {
        requireNonNull(index);
        this.index = index;
        this.priority = priority;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getCompany(),
                personToEdit.getTags(),
                personToEdit.getNote(),
                priority
        );

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String msg = (priority == null) ? String.format(MESSAGE_CLEAR_SUCCESS, edited.getName()) : String.format(generateSuccessMessage(editedPerson), edited.getPriority(), edited.getName());
        return new CommandResult(msg);
    }

    /**
     * Generates a command execution success message.
     */
    private String generateSuccessMessage(Person person) {
        return String.format(MESSAGE_SET_PRIORITY_SUCCESS, Messages.format(person));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PriorityCommand)) {
            return false;
        }
        PriorityCommand otherCommand = (PriorityCommand) other;
        return index.equals(otherCommand.index)
                && ((priority == null && otherCommand.priority == null)
                || (priority != null && priority.equals(otherCommand.priority)));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("priority", priority)
                .toString();
    }
}
