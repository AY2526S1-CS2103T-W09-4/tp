package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword (name)
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords (name)
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword among the keywords provided
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords (case-insensitive)
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Partial match: "oh" matches "John"
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("oh"));
        assertTrue(predicate.test(new PersonBuilder().withName("John Doe").build()));

        // Match on phone field
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("8743"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("87438807").build()));

        // Match on email field (partial)
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("example"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alexyeoh@example.com").build()));

        // Match on address field (partial)
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Geylang"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Blk 30 Geylang Street 29, #06-40").build()));

        // Match on tag
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("friend"));
        assertTrue(predicate.test(new PersonBuilder().withTags("friends").build()));

        // Match on company (partial).
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Tech"));
        assertTrue(predicate.test(new PersonBuilder().withCompany("Tech Solutions").build()));

        // OR across fields: one of the keywords matches one of the fields
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "8743", "Tech"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("87438807")
                .withCompany("Tech Solutions").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords -> treat as no match
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword -> no field matches
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        // Previously this test expected false when keywords match phone/email/address only.
        // With the new behaviour (searches all fields), such keywords should now return true.
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);

        String expected = NameContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

    @Test
    public void test_nullEmailOrAddress_doesNotCauseNpeAndBehavesCorrectly() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("example"));
        Person personNoEmailAddress = new PersonBuilder().withName("Foo").withPhone("0000")
                .withEmail(null).withAddress(null).withTags("friends").build();

        assertFalse(predicate.test(personNoEmailAddress));

        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(
                Collections.singletonList("Foo"));
        assertTrue(namePredicate.test(personNoEmailAddress));
    }

}
