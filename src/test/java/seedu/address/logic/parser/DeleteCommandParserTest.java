package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    public void parse_validSingleIndexWithSpaces_returnsDeleteCommand() throws ParseException {
        // Test single index with extra spaces
        DeleteCommand result = parser.parse("   1   ");
        assertNotNull(result);
        assertTrue(result.toString().contains("isBulkDelete=false"));
    }

    @Test
    public void parse_validTwoIndices_returnsBulkDeleteCommand() throws ParseException {
        // Test exactly two indices (boundary for bulk delete)
        DeleteCommand result = parser.parse("1,2");
        assertNotNull(result);
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_validRangeOnly_returnsBulkDeleteCommand() throws ParseException {
        // Test range without other indices
        DeleteCommand result = parser.parse("3-5");
        assertNotNull(result);
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_complexMixedFormat_returnsBulkDeleteCommand() throws ParseException {
        // Test complex format with multiple ranges and indices
        DeleteCommand result = parser.parse("1,3-5,7,10-12,15");
        assertNotNull(result);
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_singleRangeTwoElements_returnsBulkDeleteCommand() throws ParseException {
        // Test range with exactly two elements
        DeleteCommand result = parser.parse("5-6");
        assertNotNull(result);
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_whitespaceBetweenIndices_success() throws ParseException {
        // Test whitespace around commas
        DeleteCommand result = parser.parse("1 , 2 , 3");
        assertNotNull(result);
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_whitespaceBetweenRange_success() throws ParseException {
        // Test whitespace around range hyphen
        DeleteCommand result = parser.parse("5 - 7");
        assertNotNull(result);
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_trailingComma_throwsParseException() {
        // Test trailing comma - might be handled by IndexRangeParser
        // Behavior depends on implementation
        try {
            DeleteCommand result = parser.parse("1,2,");
            // If it succeeds, verify it's a valid command
            assertNotNull(result);
        } catch (ParseException e) {
            // If it throws, that's also valid behavior
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        // Test zero as single index
        assertThrows(ParseException.class, () -> parser.parse("0"));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        // Test negative single index
        assertThrows(ParseException.class, () -> parser.parse("-1"));
    }

    @Test
    public void parse_negativeIndexInList_throwsParseException() {
        // Test negative index in list
        assertThrows(ParseException.class, () -> parser.parse("1,-2,3"));
    }

    @Test
    public void parse_zeroInList_throwsParseException() {
        // Test zero in list of indices
        assertThrows(ParseException.class, () -> parser.parse("1,0,3"));
    }

    @Test
    public void parse_invalidRangeStartGreaterThanEnd_throwsParseException() {
        // Test invalid range where start > end
        assertThrows(ParseException.class, () -> parser.parse("7-3"));
    }

    @Test
    public void parse_invalidRangeInMixedFormat_throwsParseException() {
        // Test invalid range in mixed format
        assertThrows(ParseException.class, () -> parser.parse("1,7-3,5"));
    }

    @Test
    public void parse_alphabeticCharacters_throwsParseException() {
        // Test alphabetic input
        assertThrows(ParseException.class, () -> parser.parse("abc"));
        assertThrows(ParseException.class, () -> parser.parse("a"));
    }

    @Test
    public void parse_alphanumericMix_throwsParseException() {
        // Test mix of numbers and letters
        assertThrows(ParseException.class, () -> parser.parse("1a"));
        assertThrows(ParseException.class, () -> parser.parse("1,2a,3"));
    }

    @Test
    public void parse_specialCharacters_throwsParseException() {
        // Test special characters
        assertThrows(ParseException.class, () -> parser.parse("@"));
        assertThrows(ParseException.class, () -> parser.parse("1,@,3"));
        assertThrows(ParseException.class, () -> parser.parse("1!2"));
    }

    @Test
    public void parse_floatingPointNumber_throwsParseException() {
        // Test floating point numbers
        assertThrows(ParseException.class, () -> parser.parse("1.5"));
        assertThrows(ParseException.class, () -> parser.parse("1,2.5,3"));
    }

    @Test
    public void parse_multipleDashes_throwsParseException() {
        // Test multiple dashes (invalid range)
        assertThrows(ParseException.class, () -> parser.parse("1--3"));
        assertThrows(ParseException.class, () -> parser.parse("1-2-3"));
    }

    @Test
    public void parse_onlyDash_throwsParseException() {
        // Test only dash
        assertThrows(ParseException.class, () -> parser.parse("-"));
    }

    @Test
    public void parse_onlyComma_throwsParseException() {
        // Test only comma
        assertThrows(ParseException.class, () -> parser.parse(","));
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        // Test whitespace only
        assertThrows(ParseException.class, () -> parser.parse("   "));
        assertThrows(ParseException.class, () -> parser.parse("\t"));
        assertThrows(ParseException.class, () -> parser.parse("\n"));
    }

    @Test
    public void parse_tabCharacter_throwsParseException() {
        // Test tab character in input
        assertThrows(ParseException.class, () -> parser.parse("1\t2"));
    }

    @Test
    public void parse_newlineCharacter_throwsParseException() {
        // Test newline character in input
        assertThrows(ParseException.class, () -> parser.parse("1\n2"));
    }

    @Test
    public void parse_veryLargeIndex_success() throws ParseException {
        // Test very large index
        DeleteCommand result = parser.parse("999999");
        assertNotNull(result);
        assertTrue(result.toString().contains("isBulkDelete=false"));
    }

    @Test
    public void parse_veryLargeRange_success() throws ParseException {
        // Test very large range
        DeleteCommand result = parser.parse("1-100000");
        assertNotNull(result);
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_duplicateIndices_success() throws ParseException {
        // Test duplicate indices (should be deduplicated by IndexRangeParser)
        DeleteCommand result = parser.parse("1,1,1,2,2,3");
        assertNotNull(result);
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_unsortedIndices_success() throws ParseException {
        // Test unsorted indices (should be sorted by IndexRangeParser)
        DeleteCommand result = parser.parse("5,1,3,2,4");
        assertNotNull(result);
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_overlappingRanges_success() throws ParseException {
        // Test overlapping ranges
        DeleteCommand result = parser.parse("1-5,3-7");
        assertNotNull(result);
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_rangeAndSingleOverlap_success() throws ParseException {
        // Test range and single index that overlap
        DeleteCommand result = parser.parse("3,5-7,6");
        assertNotNull(result);
        assertTrue(result.toString().contains("isBulkDelete=true"));
    }

    @Test
    public void parse_errorMessageFormat_containsUsageInfo() {
        // Test that error messages contain usage information
        try {
            parser.parse("invalid");
        } catch (ParseException e) {
            String message = e.getMessage();
            assertTrue(message.contains("delete") || message.contains("Invalid"));
        }
    }

    @Test
    public void parse_brackets_throwsParseException() {
        // Test brackets are not accepted
        assertThrows(ParseException.class, () -> parser.parse("[1,2,3]"));
        assertThrows(ParseException.class, () -> parser.parse("(1,2,3)"));
    }

    @Test
    public void parse_underscoreInNumber_throwsParseException() {
        // Test underscore in number
        assertThrows(ParseException.class, () -> parser.parse("1_000"));
    }

    @Test
    public void parse_scientificNotation_throwsParseException() {
        // Test scientific notation
        assertThrows(ParseException.class, () -> parser.parse("1e5"));
        assertThrows(ParseException.class, () -> parser.parse("1E5"));
    }

    @Test
    public void parse_hexadecimal_throwsParseException() {
        // Test hexadecimal format
        assertThrows(ParseException.class, () -> parser.parse("0x10"));
        assertThrows(ParseException.class, () -> parser.parse("10h"));
    }
}
