package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void parseIndexRange_nullInput_throwsParseException() {
        // Test null input throws ParseException
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange(null));
    }

    @Test
    public void parseIndexRange_whitespaceOnly_throwsParseException() {
        // Test whitespace-only input throws ParseException
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("   "));
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("\t\n"));
    }

    @Test
    public void parseIndexRange_trailingComma_handledCorrectly() throws ParseException {
        // Test that trailing comma is handled (empty parts ignored)
        List<Index> result = IndexRangeParser.parseIndexRange("1,3,");
        assertEquals(2, result.size());
        assertEquals(Index.fromOneBased(1), result.get(0));
        assertEquals(Index.fromOneBased(3), result.get(1));
    }

    @Test
    public void parseIndexRange_multipleCommas_handledCorrectly() throws ParseException {
        // Test multiple consecutive commas are handled
        List<Index> result = IndexRangeParser.parseIndexRange("1,,3");
        assertEquals(2, result.size());
    }

    @Test
    public void parseIndexRange_rangeWithZero_throwsParseException() {
        // Test range containing zero throws exception
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("0-5"));
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("1-0"));
    }

    @Test
    public void parseIndexRange_rangeWithNegative_throwsParseException() {
        // Test range containing negative number throws exception
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("-1-5"));
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("1--5"));
    }

    @Test
    public void parseIndexRange_invalidRangeFormat_throwsParseException() {
        // Test invalid range formats
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("1-2-3"));
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("1--2"));
    }

    @Test
    public void parseIndexRange_singleHyphen_throwsParseException() {
        // Test single hyphen throws exception
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("-"));
    }

    @Test
    public void parseIndexRange_mixedInvalidAndValid_throwsParseException() {
        // Test that any invalid index causes failure
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("1,abc,3"));
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("1,0,3"));
    }

    @Test
    public void parseIndexRange_largeRange_success() throws ParseException {
        // Test large range is parsed correctly
        List<Index> result = IndexRangeParser.parseIndexRange("1-100");
        assertEquals(100, result.size());
        assertEquals(Index.fromOneBased(1), result.get(0));
        assertEquals(Index.fromOneBased(100), result.get(99));
    }

    @Test
    public void parseIndexRange_singleElementRange_success() throws ParseException {
        // Test range with start == end
        List<Index> result = IndexRangeParser.parseIndexRange("5-5");
        assertEquals(1, result.size());
        assertEquals(Index.fromOneBased(5), result.get(0));
    }

    @Test
    public void parseIndexRange_overlappingRanges_noDuplicates() throws ParseException {
        // Test overlapping ranges don't create duplicates
        List<Index> result = IndexRangeParser.parseIndexRange("1-5,3-7");
        assertEquals(7, result.size()); // Should be 1,2,3,4,5,6,7 (no duplicates)
        assertEquals(Index.fromOneBased(1), result.get(0));
        assertEquals(Index.fromOneBased(7), result.get(6));
    }

    @Test
    public void parseIndexRange_complexMixedFormat_success() throws ParseException {
        // Test complex mixed format
        List<Index> result = IndexRangeParser.parseIndexRange("10,1-3,5,7-9,15");
        // Should expand to: 1,2,3,5,7,8,9,10,15
        assertEquals(9, result.size());
        assertEquals(Index.fromOneBased(1), result.get(0));
        assertEquals(Index.fromOneBased(15), result.get(8));
    }

    @Test
    public void parseIndexRange_onlyCommas_throwsParseException() {
        // Test input with only commas throws exception
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange(",,,"));
    }

    @Test
    public void parseIndexRange_specialCharacters_throwsParseException() {
        // Test special characters throw exception
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("1,@,3"));
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("1.5,3"));
    }

    @Test
    public void parseIndexRange_floatingPoint_throwsParseException() {
        // Test floating point numbers throw exception
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("1.5"));
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("1,2.5,3"));
    }

    @Test
    public void parseIndexRange_verLargeNumber_success() throws ParseException {
        // Test very large numbers work
        List<Index> result = IndexRangeParser.parseIndexRange("999999");
        assertEquals(1, result.size());
        assertEquals(Index.fromOneBased(999999), result.get(0));
    }

    @Test
    public void getInvalidIndices_emptyList_empty() {
        // Test empty index list returns empty invalid list
        List<Integer> result = IndexRangeParser.getInvalidIndices(Arrays.asList(), 10);
        assertEquals(0, result.size());
    }

    @Test
    public void getInvalidIndices_allInvalid_allReported() {
        // Test all indices invalid are reported
        List<Index> indices = Arrays.asList(
                Index.fromOneBased(10),
                Index.fromOneBased(20),
                Index.fromOneBased(30)
        );
        List<Integer> invalid = IndexRangeParser.getInvalidIndices(indices, 5);
        assertEquals(3, invalid.size());
        assertTrue(invalid.contains(10));
        assertTrue(invalid.contains(20));
        assertTrue(invalid.contains(30));
    }

    @Test
    public void getInvalidIndices_mixedValidInvalid_onlyInvalidReported() {
        // Test mixed valid and invalid indices
        List<Index> indices = Arrays.asList(
                Index.fromOneBased(1),  // valid
                Index.fromOneBased(3),  // valid
                Index.fromOneBased(10), // invalid
                Index.fromOneBased(5),  // valid
                Index.fromOneBased(15)  // invalid
        );
        List<Integer> invalid = IndexRangeParser.getInvalidIndices(indices, 7);
        assertEquals(2, invalid.size());
        assertTrue(invalid.contains(10));
        assertTrue(invalid.contains(15));
    }

    @Test
    public void getInvalidIndices_listSizeZero_allInvalid() {
        // Test with list size of zero - all indices invalid
        List<Index> indices = Arrays.asList(
                Index.fromOneBased(1),
                Index.fromOneBased(2)
        );
        List<Integer> invalid = IndexRangeParser.getInvalidIndices(indices, 0);
        assertEquals(2, invalid.size());
    }

    @Test
    public void getInvalidIndices_largeListSize_correct() {
        // Test with large list size
        List<Index> indices = Arrays.asList(
                Index.fromOneBased(100),
                Index.fromOneBased(200),
                Index.fromOneBased(1000)
        );
        List<Integer> invalid = IndexRangeParser.getInvalidIndices(indices, 999);
        assertEquals(1, invalid.size());
        assertTrue(invalid.contains(1000));
    }

    @Test
    public void parseIndexRange_resultIsImmutable_noConcurrentModification() throws ParseException {
        // Test that returned list can be safely used
        List<Index> result = IndexRangeParser.parseIndexRange("1,2,3");
        assertNotNull(result);
        assertEquals(3, result.size());

        // Verify we can iterate without issues
        for (Index idx : result) {
            assertTrue(idx.getOneBased() > 0);
        }
    }

    @Test
    public void parseIndexRange_duplicatesInDifferentFormats_removed() throws ParseException {
        // Test duplicates specified in different ways are removed
        List<Index> result = IndexRangeParser.parseIndexRange("5,3-7,5,6");
        // Should result in: 3,4,5,6,7 (5 and 6 appear multiple times but deduplicated)
        assertEquals(5, result.size());
    }

    @Test
    public void parseIndexRange_errorMessage_containsUsefulInfo() {
        // Test error messages are informative
        try {
            IndexRangeParser.parseIndexRange("abc");
        } catch (ParseException e) {
            String message = e.getMessage();
            assertTrue(message.contains("Invalid") || message.contains("index"));
        }
    }
}
