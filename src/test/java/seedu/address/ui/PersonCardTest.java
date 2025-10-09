package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest {

    @Test
    public void helpers_nullOptionalFields() {
        Person p = new PersonBuilder()
                .withName("Z")
                .withPhone("0000")
                .withEmail(null)
                .withAddress(null)
                .withCompany(null)
                .withTags() // ensure tags exist (empty) to avoid null tags
                .build();

        assertEquals("", PersonCard.getAddressText(p));
        assertFalse(PersonCard.isAddressVisible(p));

        assertEquals("", PersonCard.getEmailText(p));
        assertFalse(PersonCard.isEmailVisible(p));

        assertEquals("", PersonCard.getCompanyText(p));
        assertFalse(PersonCard.isCompanyVisible(p));
    }

    @Test
    public void helpers_presentOptionalFields() {
        Person p = new PersonBuilder()
                .withName("Alice")
                .withPhone("1111")
                .withEmail("alice@example.com")
                .withAddress("123, Main Street")
                .withCompany("Acme Inc")
                .withTags("friends")
                .build();

        assertEquals("123, Main Street", PersonCard.getAddressText(p));
        assertTrue(PersonCard.isAddressVisible(p));

        assertEquals("alice@example.com", PersonCard.getEmailText(p));
        assertTrue(PersonCard.isEmailVisible(p));

        assertEquals("Acme Inc", PersonCard.getCompanyText(p));
        assertTrue(PersonCard.isCompanyVisible(p));
    }
}
