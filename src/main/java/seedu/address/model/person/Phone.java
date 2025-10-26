package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {


    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should contain only digits, spaces, hyphens, and plus signs, "
                    + "with at least 3 digits. Valid formats include: 91234567, +65 9123 4567, +1-650-555-0123";
    public static final String VALIDATION_REGEX = "[+\\d\\s-]+";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        checkArgument(isValidPhone(trimmedPhone), MESSAGE_CONSTRAINTS);
        // Store normalized version (remove formatting, keep only digits and leading +)
        value = normalizePhone(trimmedPhone);
    }

    /**
     * Returns true if a given string is a valid phone number.
     * Accepts phone numbers with digits, spaces, hyphens, and plus signs.
     * Must contain at least 3 digits.
     */
    public static boolean isValidPhone(String test) {
        if (test == null || test.trim().isEmpty()) {
            return false;
        }

        String trimmed = test.trim();

        // Must match the basic format (digits, spaces, hyphens, plus)
        if (!trimmed.matches(VALIDATION_REGEX)) {
            return false;
        }

        // Must contain at least 3 digits
        long digitCount = trimmed.chars().filter(Character::isDigit).count();
        return digitCount >= 3;
    }

    /**
     * Normalizes phone number by removing spaces and hyphens while preserving leading plus sign.
     * Examples: "+65-9123-4567" -> "+6591234567", "91234567" -> "91234567"
     */
    private static String normalizePhone(String phone) {
        return phone.replaceAll("[\\s-]", "");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Phone)) {
            return false;
        }

        Phone otherPhone = (Phone) other;
        return value.equals(otherPhone.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
