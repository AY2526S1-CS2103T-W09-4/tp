package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains unit tests for IndexRangeParser.
 */
public class IndexRangeParserTest {

    @Test
    public void parseIndexRange_singleIndex_success() throws ParseException {
        List<Index> result = IndexRangeParser.parseIndexRange("1");
        assertEquals(1, result.size());
        assertEquals(Index.fromOneBased(1), result.get(0));
    }

    @Test
    public void parseIndexRange_multipleIndices_success() throws ParseException {
        List<Index> result = IndexRangeParser.parseIndexRange("1,3,5");
        assertEquals(3, result.size());
    }

    @Test
    public void parseIndexRange_simpleRange_success() throws ParseException {
        // Range "5-7" should expand to indices 5, 6, 7
        List<Index> result = IndexRangeParser.parseIndexRange("5-7");
        assertEquals(3, result.size());
    }

    @Test
    public void parseIndexRange_mixedFormat_success() throws ParseException {
        // Mixed format "1,3,5-7,10" should expand and parse correctly
        List<Index> result = IndexRangeParser.parseIndexRange("1,3,5-7,10");
        assertEquals(6, result.size());
    }

    @Test
    public void parseIndexRange_duplicates_removed() throws ParseException {
        // Duplicates should be automatically removed
        List<Index> result = IndexRangeParser.parseIndexRange("1,1,1,3,3");
        assertEquals(2, result.size());
    }

    @Test
    public void parseIndexRange_unorderedInput_sorted() throws ParseException {
        // Unsorted input should be sorted in ascending order
        List<Index> result = IndexRangeParser.parseIndexRange("5,1,3");
        assertEquals(3, result.size());
        assertEquals(Index.fromOneBased(1), result.get(0));
    }

    @Test
    public void parseIndexRange_zeroIndex_failure() {
        // Zero is not a valid index
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("0"));
    }

    @Test
    public void parseIndexRange_negativeIndex_failure() {
        // Negative indices are not valid
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("-1"));
    }

    @Test
    public void parseIndexRange_invalidRange_failure() {
        // Range start must be <= end
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("5-3"));
    }

    @Test
    public void parseIndexRange_nonNumericInput_failure() {
        // Non-numeric input should throw exception
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("abc"));
    }

    @Test
    public void parseIndexRange_emptyInput_failure() {
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange(""));
    }

    @Test
    public void parseIndexRange_whitespaceHandling_success() throws ParseException {
        // Whitespace should be trimmed and ignored
        List<Index> result = IndexRangeParser.parseIndexRange("  1 , 3 , 5-7  ");
        assertEquals(5, result.size());
    }

    @Test
    public void getInvalidIndices_allValid_empty() {
        // All indices within list size should return empty list
        List<Index> indices = Arrays.asList(
                Index.fromOneBased(1),
                Index.fromOneBased(2),
                Index.fromOneBased(3)
        );
        List<Integer> invalid = IndexRangeParser.getInvalidIndices(indices, 5);
        assertEquals(0, invalid.size());
    }

    @Test
    public void getInvalidIndices_someInvalid_reported() {
        // Indices exceeding list size should be reported as invalid
        List<Index> indices = Arrays.asList(
                Index.fromOneBased(1),
                Index.fromOneBased(10),
                Index.fromOneBased(20)
        );
        List<Integer> invalid = IndexRangeParser.getInvalidIndices(indices, 5);
        assertEquals(2, invalid.size());
    }
}
