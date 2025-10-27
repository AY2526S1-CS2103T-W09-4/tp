package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a DeleteCommand object.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given string of arguments as a delete command.
     *
     * @param args the user input string to parse
     *            Examples: "1", "1,2,3", "5-7", "1,3,5-7,10"
     * @return a DeleteCommand object ready for execution
     * @throws ParseException if the format is invalid or indices are invalid
     */
    @Override
    public DeleteCommand parse(String args) throws ParseException {
        try {
            args = args.trim();

            // Check if this is a bulk delete (contains comma or range)
            if (args.contains(",") || (args.contains("-") && !args.startsWith("-"))) {
                // Parse as range/bulk delete
                List<Index> indices = IndexRangeParser.parseIndexRange(args);

                if (indices.isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
                }

                return new DeleteCommand(indices);
            } else {
                // Parse as single index delete
                Index index = ParserUtil.parseIndex(args);
                return new DeleteCommand(index);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
