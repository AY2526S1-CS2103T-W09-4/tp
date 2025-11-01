package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Adds or updates a note/remark for an existing person in the address book.
 */
public class NoteCommand extends Command {

    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds or updates a note/remark for the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "NOTE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim on weekends";

    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Added note to Contact: %1$s";
    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Removed note from Contact: %1$s";

    private final Index index;
    private final Note note;

    /**
     * @param index of the person in the filtered person list to add note to
     * @param note note to add to the person
     */
    public NoteCommand(Index index, Note note) {
        requireNonNull(index);
        requireNonNull(note);
        this.index = index;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Use the note as is; Note class handles EMPTY_NOTE_PLACEHOLDER
        Person editedPerson = new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getCompany(),
                personToEdit.getTags(),
                note
        );

        model.setPerson(personToEdit, editedPerson);
        model.commitAddressBook();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(note, editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the note is empty or added.
     */
    private String generateSuccessMessage(Note note, Person person) {
        String message;
        if (note.isEmpty()) {
            message = MESSAGE_DELETE_NOTE_SUCCESS;
        } else {
            message = MESSAGE_ADD_NOTE_SUCCESS;
        }

        String noteDisplay = person.getNote().toDisplayString();
        return String.format(message, Messages.format(person) + " [" + noteDisplay + "]");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NoteCommand)) {
            return false;
        }
        NoteCommand otherNoteCommand = (NoteCommand) other;
        return index.equals(otherNoteCommand.index)
                && note.equals(otherNoteCommand.note);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("note", note)
                .toString();
    }
}
