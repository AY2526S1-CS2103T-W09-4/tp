package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "abc123";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_COMPANY = "Company@#$";
    private static final String INVALID_PRIORITY = "INVALID";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_COMPANY = "Tech Corp";
    private static final String VALID_NOTE = "Important client";
    private static final String VALID_PRIORITY = "HIGH";
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS, null, null, null
        );
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS, null, null, null
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS, null, null, null
        );
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS, null, null, null
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS, null, null, null
        );
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_returnsPersonWithNullEmail() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, null, VALID_TAGS, null, null, null
        );
        Person modelPerson = person.toModelType();
        assertEquals(null, modelPerson.getEmail());
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, null, VALID_TAGS, null, null, null
        );
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_returnsPersonWithNullAddress() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, null, null, VALID_TAGS, null, null, null
        );
        Person modelPerson = person.toModelType();
        assertEquals(null, modelPerson.getAddress());
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, invalidTags, null, null, null
        );
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_validPersonWithCompany_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_COMPANY, VALID_TAGS, null, null, null
        );
        Person modelPerson = person.toModelType();
        assertEquals(new Company(VALID_COMPANY), modelPerson.getCompany());
    }

    @Test
    public void toModelType_nullCompany_returnsPersonWithNullCompany() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS, null, null, null
        );
        Person modelPerson = person.toModelType();
        assertEquals(null, modelPerson.getCompany());
    }

    @Test
    public void toModelType_invalidCompany_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, INVALID_COMPANY, VALID_TAGS, null, null, null
        );
        String expectedMessage = Company.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_emptyCompany_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, "", VALID_TAGS, null, null, null
        );
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_validPersonWithNote_returnsPerson() throws Exception {
        Person personWithNote = new PersonBuilder(BENSON)
                .withNote(VALID_NOTE)
                .build();
        JsonAdaptedPerson jsonPerson = new JsonAdaptedPerson(personWithNote);
        assertEquals(personWithNote, jsonPerson.toModelType());
    }

    @Test
    public void toModelType_nullNote_returnsPersonWithNullNote() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS, null, null, null
        );
        Person modelPerson = person.toModelType();
        assertEquals(null, modelPerson.getNote());
    }

    @Test
    public void toModelType_validNote_returnsPersonWithNote() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS, VALID_NOTE, null, null
        );
        Person modelPerson = person.toModelType();
        assertEquals(VALID_NOTE, modelPerson.getNote().value);
    }

    @Test
    public void toModelType_validPersonWithPriority_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS, null, null, VALID_PRIORITY
        );
        Person modelPerson = person.toModelType();
        assertEquals(new Priority(VALID_PRIORITY), modelPerson.getPriority());
    }

    @Test
    public void toModelType_nullPriority_returnsPersonWithNullPriority() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS, null, null, null
        );
        Person modelPerson = person.toModelType();
        assertEquals(null, modelPerson.getPriority());
    }

    @Test
    public void toModelType_invalidPriority_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS, null, null, INVALID_PRIORITY
        );
        String expectedMessage = Priority.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_emptyPriority_returnsPersonWithNullPriority() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, null, VALID_TAGS, null, null, ""
        );
        Person modelPerson = person.toModelType();
        assertEquals(null, modelPerson.getPriority());
    }

    @Test
    public void toModelType_personConstructor_nullFields() throws Exception {
        Person personWithNulls = new PersonBuilder(BENSON)
                .withEmail(null)
                .withAddress(null)
                .withCompany(null)
                .withNote(null)
                .withTags(BENSON.getTags().stream().map(tag -> tag.tagName).toArray(String[]::new))
                .build();

        JsonAdaptedPerson adapted = new JsonAdaptedPerson(personWithNulls);
        Person modelPerson = adapted.toModelType();

        assertEquals(personWithNulls, modelPerson);
    }

    @Test
    public void toModelType_nullFields_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, null, null, null, VALID_TAGS, null, null, null
        );
        Person modelPerson = person.toModelType();
        assertEquals(null, modelPerson.getEmail());
        assertEquals(null, modelPerson.getAddress());
        assertEquals(null, modelPerson.getPriority());
    }
}
