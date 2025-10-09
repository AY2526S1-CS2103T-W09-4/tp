package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MessagesTest {

    @Test
    public void format_includesEmailAndAddress_whenPresent() {
        Person p = new PersonBuilder()
                .withName("Alice")
                .withPhone("91234567")
                .withEmail("alice@example.com")
                .withAddress("1, Some Road")
                .withCompany(null)
                .withTags()
                .build();

        String formatted = Messages.format(p);
        // should include email and address text when they are non-null
        assertTrue(formatted.contains("Email"));
        assertTrue(formatted.contains("Address"));
        assertTrue(formatted.contains("alice@example.com"));
        assertTrue(formatted.contains("Some Road"));
    }

    @Test
    public void format_omitsEmailAndAddress_whenNull() {
        Person p = new PersonBuilder()
                .withName("Bob")
                .withPhone("91234567")
                .withEmail(null)
                .withAddress(null)
                .withCompany(null)
                .withTags()
                .build();

        String formatted = Messages.format(p);
        assertFalse(formatted.contains("null"));
        assertFalse(formatted.contains("Email:"));
        assertFalse(formatted.contains("Address:"));
    }
}
