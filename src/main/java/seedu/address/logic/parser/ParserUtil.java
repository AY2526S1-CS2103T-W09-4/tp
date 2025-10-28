package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = trimOrThrow(oneBasedIndex, "Index");
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading, trailing and extra whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        String trimmedName = collapseInnerSpaces(trimOrThrow(name, "Name"));
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        String trimmedPhone = trimOrThrow(phone, "Phone");
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading, trailing and extra whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        String trimmedAddress = collapseInnerSpaces(trimOrThrow(address, "Address"));
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        String trimmedEmail = trimOrThrow(email, "Email");
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        String trimmedTag = trimOrThrow(tag, "Tag");
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String company} into a {@code Company}.
     * Leading, trailing and extra whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code company} is invalid.
     */
    public static Company parseCompany(String company) throws ParseException {
        String trimmedCompany = collapseInnerSpaces(trimOrThrow(company, "Company"));
        if (!Company.isValidCompany(trimmedCompany)) {
            throw new ParseException(Company.MESSAGE_CONSTRAINTS);
        }
        return new Company(trimmedCompany);
    }

    /**
     * Parses a {@code String note} into a {@code Note}.
     * Leading and trailing whitespaces will be trimmed.
     * Empty string will be treated as removing the note.
     *
     * @throws ParseException if the given {@code note} is invalid.
     */
    public static Note parseNote(String note) throws ParseException {
        String trimmedNote = collapseInnerSpaces(trimOrThrow(note, "Note"));

        // Allow empty string to clear the note
        if (trimmedNote.isEmpty()) {
            return new Note(Note.EMPTY_NOTE_PLACEHOLDER);
        }

        if (!Note.isValidNote(trimmedNote)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(trimmedNote);
    }

    /**
     * Parses a {@code String priority} into a {@code Priority}.
     * Leading, trailing and extra whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        String trimmedPriority = collapseInnerSpaces(trimOrThrow(priority, "Priority"));
        if (!Priority.isValidPriority(trimmedPriority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }
        return new Priority(trimmedPriority);
    }

    // Helper functions to harden parser util

    /**
     * Trims and returns input string if it is valid, throws a custom ParseException otherwise.
     */
    private static String trimOrThrow(String s, String field) throws ParseException {
        requireNonNull(s);
        String t = s.trim();
        if (t.isEmpty()) {
            throw new ParseException(field + " cannot be empty.");
        }
        return t;
    }

    private static String collapseInnerSpaces(String s) {
        return s.trim().replaceAll("\\s+", " ");
    }
}
