package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private static final DateTimeFormatter ISO_FORMAT = DateTimeFormatter.ISO_DATE_TIME;

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String company;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String note;
    private final String noteLastEdited;
    private final String priority;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("company") String company, @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("note") String note,
                             @JsonProperty("noteLastEdited") String noteLastEdited,
                             @JsonProperty("priority") String priority) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.company = company;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.note = note;
        this.noteLastEdited = noteLastEdited;
        this.priority = priority;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail() != null ? source.getEmail().value : null;
        address = source.getAddress() != null ? source.getAddress().value : null;
        company = source.getCompany() != null ? source.getCompany().value : null;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        note = source.getNote() != null ? source.getNote().value : null;
        noteLastEdited = (source.getNote() != null && source.getNote().getLastEdited() != null)
                ? source.getNote().getLastEdited().format(ISO_FORMAT)
                : null;
        priority = source.getPriority() != null ? source.getPriority().toString() : null;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        // Email is optional
        final Email modelEmail;
        if (email != null) {
            if (!Email.isValidEmail(email)) {
                throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
            }
            modelEmail = new Email(email);
        } else {
            modelEmail = null;
        }

        // Address is optional
        final Address modelAddress;
        if (address != null) {
            if (!Address.isValidAddress(address)) {
                throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
            }
            modelAddress = new Address(address);
        } else {
            modelAddress = null;
        }

        // Company is optional
        final Company modelCompany;
        if (company != null) {
            if (!Company.isValidCompany(company)) {
                throw new IllegalValueException(Company.MESSAGE_CONSTRAINTS);
            }
            modelCompany = new Company(company);
        } else {
            modelCompany = null;
        }

        final Set<Tag> modelTags = new HashSet<>(personTags);

        // Note is optional
        final Note modelNote;
        if (note != null && !note.trim().isEmpty()) {
            if (!Note.isValidNote(note)) {
                throw new IllegalValueException(Note.MESSAGE_CONSTRAINTS);
            }
            LocalDateTime parsedLastEdited = null;
            if (noteLastEdited != null && !noteLastEdited.isEmpty()) {
                try {
                    parsedLastEdited = LocalDateTime.parse(noteLastEdited, ISO_FORMAT);
                } catch (DateTimeParseException ignored) {
                    // ignore malformed timestamp
                }
            }
            modelNote = new Note(note, parsedLastEdited);
        } else {
            modelNote = null;
        }

        // Priority is optional
        final Priority modelPriority;
        if (priority != null && !priority.trim().isEmpty()) {
            if (!Priority.isValidPriority(priority)) {
                throw new IllegalValueException(Priority.MESSAGE_CONSTRAINTS);
            }
            modelPriority = new Priority(priority);
        } else {
            modelPriority = null;
        }

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelCompany,
                modelTags, modelNote, modelPriority);
    }

}
