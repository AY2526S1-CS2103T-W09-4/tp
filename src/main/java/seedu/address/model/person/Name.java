package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain letters (including accented characters), numbers, spaces, hyphens, "
                    + "periods, and apostrophes. It must be 1–100 characters long and cannot be blank.";

    /*
     * The first character must not be whitespace.
     * Length limit: 1–100 characters.
     */
    public static final String VALIDATION_REGEX =
            "^(?=.{1,100}$)[\\p{L}\\p{M}\\p{N}][\\p{L}\\p{M}\\p{N} .'-]*$";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Name
                && fullName.equals(((Name) other).fullName));
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
