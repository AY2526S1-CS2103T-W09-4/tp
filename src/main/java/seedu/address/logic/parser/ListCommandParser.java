package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 * Accepts either:
 *   - "" (empty) -> list all
 *   - "t/TAG" -> list by tag
 * Any other input -> ParseException with invalid command format.
 */
public class ListCommandParser implements Parser<ListCommand> {

    @Override
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmed = args.trim();

        if (trimmed.isEmpty()) {
            return new ListCommand();
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TAG);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

        Optional<String> tagValueOptional = argMultimap.getValue(PREFIX_TAG);
        if (tagValueOptional.isPresent()) {
            String tagValue = tagValueOptional.get().trim();
            if (tagValue.isEmpty()) {
                throw new ParseException(String.format(
                        seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }
            return new ListCommand(tagValue);
        }

        throw new ParseException(String.format(
                seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
