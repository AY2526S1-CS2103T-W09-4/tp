package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains unit tests for DeleteCommandParser.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validSingleIndex_returnsDeleteCommand() throws ParseException {
        // Single index should be parsed correctly
        DeleteCommand result = parser.parse("1");
        assertEquals(1, result.toString().split("targetIndices=")[1].split("}")[0].split(",").length);
    }

    @Test
    public void parse_validMultipleIndices_returnsDeleteCommand() throws ParseException {
        // Multiple comma-separated indices should trigger bulk delete
        DeleteCommand result = parser.parse("1,3,5");
        assertEquals(true, result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_validRange_returnsDeleteCommand() throws ParseException {
        // Range format "5-7" should be parsed and trigger bulk delete
        DeleteCommand result = parser.parse("5-7");
        assertEquals(true, result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_validMixedFormat_returnsDeleteCommand() throws ParseException {
        // Mixed format with indices and ranges should work
        DeleteCommand result = parser.parse("1,3,5-7,10");
        assertEquals(true, result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_invalidSingleIndex_throwsParseException() {
        // Zero index is invalid
        assertThrows(ParseException.class, () -> parser.parse("0"));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        // Non-numeric input should throw exception
        assertThrows(ParseException.class, () -> parser.parse("abc"));
    }

    @Test
    public void parse_invalidRange_throwsParseException() {
        // Range with start > end should throw exception
        assertThrows(ParseException.class, () -> parser.parse("5-3"));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_largeIndex_returnsDeleteCommand() throws ParseException {
        // Large valid indices should parse correctly
        DeleteCommand result = parser.parse("999");
        assertEquals(true, result != null);
    }

    @Test
    public void parse_whitespaceInInput_returnsDeleteCommand() throws ParseException {
        // Whitespace around indices should be ignored
        DeleteCommand result = parser.parse("  1 , 3 , 5  ");
        assertEquals(true, result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        // Negative indices are invalid
        assertThrows(ParseException.class, () -> parser.parse("-1"));
    }

    @Test
    public void parse_invalidRangeFormat_throwsParseException() {
        // Non-numeric range should throw exception
        assertThrows(ParseException.class, () -> parser.parse("a-b"));
    }
}
