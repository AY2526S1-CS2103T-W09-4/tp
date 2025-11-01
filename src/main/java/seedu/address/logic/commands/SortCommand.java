package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.logic.parser.SortKeys;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts the contacts in the address book by name.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Sorted all persons by %1$s (ascending).";
    private final SortKeys key;

    public SortCommand(SortKeys key) {
        this.key = key;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Comparator<Person> cmp = comparatorFor(key);
        model.sortPersonList(cmp);
        model.commitAddressBook();
        String message = String.format(MESSAGE_SUCCESS, key.getDisplayName());
        return new CommandResult(message);
    }

    private Comparator<Person> comparatorFor(SortKeys f) {
        switch (f) {
        case NAME:
            return java.util.Comparator.comparing(p -> p.getName().fullName, String.CASE_INSENSITIVE_ORDER);
        case PHONE:
            return java.util.Comparator.comparing(p -> p.getPhone().value);
        case EMAIL:
            return java.util.Comparator.comparing(p -> p.getEmail() == null ? "\uFFFF" : p.getEmail().value,
                    String.CASE_INSENSITIVE_ORDER);
        case ADDRESS:
            return java.util.Comparator.comparing(p -> p.getAddress() == null ? "\uFFFF" : p.getAddress().value,
                    String.CASE_INSENSITIVE_ORDER);
        case TAG:
            return java.util.Comparator.comparing(p -> p.getTags().stream()
                    .map(tag -> tag.tagName)
                    .sorted(String.CASE_INSENSITIVE_ORDER)
                    .findFirst().orElse(""));
        case PRIORITY:
            return java.util.Comparator.comparing(p -> p.getPriority() != null
                    ? p.getPriority().getLevel().getNumericValue()
                    : Integer.MAX_VALUE);
        default:
            throw new IllegalArgumentException("Unsupported sort key: " + f);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof SortCommand;
    }
}
