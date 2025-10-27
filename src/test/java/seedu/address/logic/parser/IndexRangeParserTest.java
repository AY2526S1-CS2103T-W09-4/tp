package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;

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
        List<Index> result = IndexRangeParser.parseIndexRange("5-7");
        assertEquals(3, result.size());
    }

    @Test
    public void parseIndexRange_mixedFormat_success() throws ParseException {
        List<Index> result = IndexRangeParser.parseIndexRange("1,3,5-7,10");
        assertEquals(6, result.size());
    }

    @Test
    public void parseIndexRange_duplicates_removed() throws ParseException {
        List<Index> result = IndexRangeParser.parseIndexRange("1,1,1,3,3");
        assertEquals(2, result.size());
    }

    @Test
    public void parseIndexRange_unorderedInput_sorted() throws ParseException {
        List<Index> result = IndexRangeParser.parseIndexRange("5,1,3");
        assertEquals(3, result.size());
        assertEquals(Index.fromOneBased(1), result.get(0));
    }

    @Test
    public void parseIndexRange_zeroIndex_failure() {
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("0"));
    }

    @Test
    public void parseIndexRange_negativeIndex_failure() {
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("-1"));
    }

    @Test
    public void parseIndexRange_invalidRange_failure() {
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("5-3"));
    }

    @Test
    public void parseIndexRange_nonNumericInput_failure() {
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange("abc"));
    }

    @Test
    public void parseIndexRange_emptyInput_failure() {
        assertThrows(ParseException.class, () -> IndexRangeParser.parseIndexRange(""));
    }

    @Test
    public void parseIndexRange_whitespaceHandling_success() throws ParseException {
        List<Index> result = IndexRangeParser.parseIndexRange("  1 , 3 , 5-7  ");
        assertEquals(5, result.size());
    }

    @Test
    public void getInvalidIndices_allValid_empty() {
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
        List<Index> indices = Arrays.asList(
                Index.fromOneBased(1),
                Index.fromOneBased(10),
                Index.fromOneBased(20)
        );
        List<Integer> invalid = IndexRangeParser.getInvalidIndices(indices, 5);
        assertEquals(2, invalid.size());
    }
}
