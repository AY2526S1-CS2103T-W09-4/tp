package seedu.address.model.person;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.SearchUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s fields contain any of the keywords given.
 *
 * <p>Matching is:
 * <ul>
 *   <li>case-insensitive</li>
 *   <li>partial (substring) match — e.g. keyword "oh" matches "John"</li>
 *   <li>OR across keywords and OR across fields — a person matches when any keyword matches any field</li>
 * </ul>
 */
public class NameContainsKeywordsPredicate implements java.util.function.Predicate<Person> {

    private final List<String> keywords;

    /**
     * Constructs a predicate from the given list of keywords.
     *
     * @param keywords list of keywords to match; must be non-null (but may be empty).
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = Objects.requireNonNull(keywords);
    }

    @Override
    public boolean test(Person person) {
        // If no keywords provided, treat as no match.
        if (keywords == null || keywords.isEmpty()) {
            return false;
        }

        // For each keyword, see if it matches any of the person's searchable fields.
        return keywords.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .anyMatch(keyword -> matchesAnyField(person, keyword));
    }

    /**
     * Returns true if the keyword matches any supported field of the person.
     *
     * @param person the person to check; must be non-null.
     * @param keyword the keyword to search for; must be non-null, non-empty.
     */
    private boolean matchesAnyField(Person person, String keyword) {
        Objects.requireNonNull(person);
        Objects.requireNonNull(keyword);

        // Name
        if (SearchUtil.containsSubstringIgnoreCase(person.getName().fullName, keyword)) {
            return true;
        }

        // Phone
        if (SearchUtil.containsSubstringIgnoreCase(person.getPhone().value, keyword)) {
            return true;
        }

        // Email
        if (person.getEmail() != null && SearchUtil.containsSubstringIgnoreCase(person.getEmail().value, keyword)) {
            return true;
        }

        // Address
        if (person.getAddress() != null && SearchUtil.containsSubstringIgnoreCase(person.getAddress().value, keyword)) {
            return true;
        }

        // Tags (any tag containing the substring)
        Set<Tag> tags = person.getTags();
        if (tags != null && tags.stream()
                .map(tag -> tag.tagName)
                .anyMatch(tagName -> SearchUtil.containsSubstringIgnoreCase(tagName, keyword))) {
            return true;
        }

        // Company implementation pull request not yet approved
        try {
            Method getCompanyMethod = person.getClass().getMethod("getCompany");
            Object companyObj = getCompanyMethod.invoke(person);
            if (companyObj != null) {
                String companyString = null;
                try {
                    Method valueMethod = companyObj.getClass().getMethod("toString");
                    companyString = (String) valueMethod.invoke(companyObj);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                    System.out.println("");
                }
                if (companyString != null && SearchUtil.containsSubstringIgnoreCase(companyString, keyword)) {
                    return true;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            System.out.println("");
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }
}
