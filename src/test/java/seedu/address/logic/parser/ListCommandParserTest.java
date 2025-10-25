package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArgs_success() {
        assertParseSuccess(parser, "", new ListCommand());
        assertParseSuccess(parser, "   \t\n  ", new ListCommand());
    }

    @Test
    public void parse_validTag_success() {
        assertParseSuccess(parser, " " + PREFIX_TAG + "friends", new ListCommand("friends"));
        assertParseSuccess(parser, " " + PREFIX_TAG + "  friends  ", new ListCommand("friends"));
        assertParseSuccess(parser, " " + PREFIX_TAG + "FrIeNdS", new ListCommand("FrIeNdS"));
    }

    @Test
    public void parse_missingTagValue_failure() {
        assertParseFailure(parser, " " + PREFIX_TAG, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "something", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "foo " + PREFIX_TAG + "friends", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));

        assertParseFailure(parser, PREFIX_TAG + "friends another", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListCommand.MESSAGE_USAGE));
    }
}
