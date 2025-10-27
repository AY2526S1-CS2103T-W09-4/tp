package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validSingleIndex_returnsDeleteCommand() throws ParseException {
        DeleteCommand result = parser.parse("1");
        assertEquals(1, result.toString().split("targetIndices=")[1].split("}")[0].split(",").length);
    }

    @Test
    public void parse_validMultipleIndices_returnsDeleteCommand() throws ParseException {
        DeleteCommand result = parser.parse("1,3,5");
        assertEquals(true, result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_validRange_returnsDeleteCommand() throws ParseException {
        DeleteCommand result = parser.parse("5-7");
        assertEquals(true, result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_validMixedFormat_returnsDeleteCommand() throws ParseException {
        DeleteCommand result = parser.parse("1,3,5-7,10");
        assertEquals(true, result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_invalidSingleIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("0"));
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("abc"));
    }

    @Test
    public void parse_invalidRange_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("5-3"));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
    }

    @Test
    public void parse_largeIndex_returnsDeleteCommand() throws ParseException {
        DeleteCommand result = parser.parse("999");
        assertEquals(true, result != null);
    }

    @Test
    public void parse_whitespaceInInput_returnsDeleteCommand() throws ParseException {
        DeleteCommand result = parser.parse("  1 , 3 , 5  ");
        assertEquals(true, result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("-1"));
    }

    @Test
    public void parse_invalidRangeFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("a-b"));
    }
}
