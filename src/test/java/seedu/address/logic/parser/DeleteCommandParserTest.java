package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains unit tests for {@code DeleteCommandParser}.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validSingleIndex_returnsDeleteCommand() throws ParseException {
        // Valid single index "1" should return DeleteCommand with that index
        DeleteCommand result = parser.parse("1");
        assertTrue(result != null);
    }

    @Test
    public void parse_validMultipleIndices_returnsDeleteCommand() throws ParseException {
        // Valid multiple indices "1,3,5" should return bulk DeleteCommand
        DeleteCommand result = parser.parse("1,3,5");
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_validRange_returnsDeleteCommand() throws ParseException {
        // Valid range "5-7" should return bulk DeleteCommand with expanded indices
        DeleteCommand result = parser.parse("5-7");
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_validMixedFormat_returnsDeleteCommand() throws ParseException {
        // Mixed format "1,3,5-7,10" should return bulk DeleteCommand
        DeleteCommand result = parser.parse("1,3,5-7,10");
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_invalidSingleIndex_throwsParseException() {
        // Zero index should throw ParseException
        assertThrows(ParseException.class, () -> parser.parse("0"));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        // Non-numeric input should throw ParseException
        assertThrows(ParseException.class, () -> parser.parse("abc"));
    }

    @Test
    public void parse_invalidRange_throwsParseException() {
        // Invalid range (start > end) should throw ParseException
        assertThrows(ParseException.class, () -> parser.parse("5-3"));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        // Empty string should throw ParseException
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_largeIndex_returnsDeleteCommand() throws ParseException {
        // Large valid index should still return DeleteCommand
        DeleteCommand result = parser.parse("999");
        assertTrue(result != null);
    }

    @Test
    public void parse_whitespaceInInput_returnsDeleteCommand() throws ParseException {
        // Extra whitespace should be handled correctly
        DeleteCommand result = parser.parse("  1 , 3 , 5  ");
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        // Negative index should throw ParseException
        assertThrows(ParseException.class, () -> parser.parse("-1"));
    }

    @Test
    public void parse_invalidRangeFormat_throwsParseException() {
        // Non-numeric range should throw ParseException
        assertThrows(ParseException.class, () -> parser.parse("a-b"));
    }
}
