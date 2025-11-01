package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's priority level in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_CONSTRAINTS =
            "Priority should be one of: HIGH, MEDIUM, LOW (case-insensitive), or 1-5 where 1-2=HIGH, 3-4=MEDIUM, 5=LOW";

    /**
     * Represents the priority level of a person.
     */
    public enum Level {
        HIGH(1, "HIGH", "#ff4444"),
        MEDIUM(3, "MEDIUM", "#ffbb33"),
        LOW(5, "LOW", "#00C851");

        private final int numericValue;
        private final String displayName;
        private final String colorHex;

        Level(int numericValue, String displayName, String colorHex) {
            this.numericValue = numericValue;
            this.displayName = displayName;
            this.colorHex = colorHex;
        }

        public int getNumericValue() {
            return numericValue;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getColorHex() {
            return colorHex;
        }
    }

    public final Level level;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority level.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        String trimmed = priority.trim().toUpperCase();
        checkArgument(isValidPriority(trimmed), MESSAGE_CONSTRAINTS);
        this.level = parseLevel(trimmed);
    }

    /**
     * Constructs a {@code Priority} from a Level enum.
     */
    public Priority(Level level) {
        requireNonNull(level);
        this.level = level;
    }

    /**
     * Returns true if a given string is a valid priority.
     */
    public static boolean isValidPriority(String test) {
        String upper = test.trim().toUpperCase();

        // Check if it matches HIGH, MEDIUM, LOW
        if (upper.equals("HIGH") || upper.equals("MEDIUM") || upper.equals("LOW")) {
            return true;
        }

        // Check if it's a number 1-5
        try {
            int num = Integer.parseInt(upper);
            return num >= 1 && num <= 5;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parses a string into a Priority Level.
     */
    private static Level parseLevel(String priority) {
        switch (priority) {
        case "HIGH":
        case "1":
        case "2":
            return Level.HIGH;
        case "MEDIUM":
        case "3":
        case "4":
            return Level.MEDIUM;
        case "LOW":
        case "5":
            return Level.LOW;
        default:
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns the symbol representation of the priority.
     */
    public String getSymbol() {
        switch (level) {
        case HIGH:
            return "!!!";
        case MEDIUM:
            return "!!";
        case LOW:
            return "!";
        default:
            return "";
        }
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return level.getDisplayName();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Priority)) {
            return false;
        }

        Priority otherPriority = (Priority) other;
        return level == otherPriority.level;
    }

    @Override
    public int hashCode() {
        return level.hashCode();
    }
}
