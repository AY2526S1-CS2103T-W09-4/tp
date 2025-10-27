package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses index ranges and individual indices from user input.
 */
public class IndexRangeParser {

    /** Error message for invalid range format */
    public static final String MESSAGE_INVALID_INDEX_RANGE = "Invalid index range format. "
            + "Use format like: 1,3,5-7,10";

    /** Error message when range start is greater than end */
    public static final String MESSAGE_INVALID_RANGE = "Invalid range: %s. Start index must be <= end index.";

    /** Error message when index is invalid (not positive integer) */
    public static final String MESSAGE_INVALID_INDEX = "Invalid index: %s. Index must be a positive integer.";

    /**
     * Parses a range string into a sorted list of unique indices.
     *
     * @param indexRangeString the string containing indices and/or ranges
     * @return a sorted list of unique indices (1-based) for display
     * @throws ParseException if the format is invalid, contains non-numeric values,
     *                       or indices are invalid (zero, negative, or range issues)
     */
    public static List<Index> parseIndexRange(String indexRangeString) throws ParseException {
        if (indexRangeString == null || indexRangeString.trim().isEmpty()) {
            throw new ParseException("No valid indices provided.");
        }

        Set<Integer> uniqueIndices = new HashSet<>();
        String[] parts = indexRangeString.trim().split(",");

        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty()) {
                continue;
            }

            if (part.contains("-")) {
                String[] rangeParts = part.split("-");
                if (rangeParts.length != 2) {
                    throw new ParseException(MESSAGE_INVALID_INDEX_RANGE);
                }

                try {
                    int startIdx = Integer.parseInt(rangeParts[0].trim());
                    int endIdx = Integer.parseInt(rangeParts[1].trim());

                    if (startIdx <= 0 || endIdx <= 0) {
                        throw new ParseException(String.format(MESSAGE_INVALID_INDEX,
                                "Range: " + startIdx + "-" + endIdx));
                    }

                    if (startIdx > endIdx) {
                        throw new ParseException(String.format(MESSAGE_INVALID_RANGE, part));
                    }

                    for (int i = startIdx; i <= endIdx; i++) {
                        uniqueIndices.add(i);
                    }
                } catch (NumberFormatException e) {
                    throw new ParseException(MESSAGE_INVALID_INDEX_RANGE);
                }
            } else {
                try {
                    int idx = Integer.parseInt(part);
                    if (idx <= 0) {
                        throw new ParseException(String.format(MESSAGE_INVALID_INDEX, part));
                    }
                    uniqueIndices.add(idx);
                } catch (NumberFormatException e) {
                    throw new ParseException(String.format(MESSAGE_INVALID_INDEX, part));
                }
            }
        }

        if (uniqueIndices.isEmpty()) {
            throw new ParseException("No valid indices provided.");
        }

        List<Integer> sortedIndices = new ArrayList<>(uniqueIndices);
        Collections.sort(sortedIndices);

        List<Index> result = new ArrayList<>();
        for (Integer idx : sortedIndices) {
            result.add(Index.fromOneBased(idx));
        }

        return result;
    }

    /**
     * Validates that all provided indices are within the list size.
     *
     * @param indices the list of indices to validate
     * @param listSize the size of the list being indexed
     * @return a list of invalid indices (those >= listSize), empty if all valid
     */
    public static List<Integer> getInvalidIndices(List<Index> indices, int listSize) {
        List<Integer> invalidIndices = new ArrayList<>();
        for (Index idx : indices) {
            if (idx.getZeroBased() >= listSize) {
                invalidIndices.add(idx.getOneBased());
            }
        }
        return invalidIndices;
    }
}
