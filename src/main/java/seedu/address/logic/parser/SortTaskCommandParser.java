package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CRITERIA;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.SortTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commons.Criteria;

/**
 * SortTaskCommandParser represents a parser which parses the arguments
 * given by the user to create a SortTaskCommand object.
 */
public class SortTaskCommandParser implements Parser<Command> {
    @Override
    public Command parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CRITERIA);
        if (!isPrefixPresent(argumentMultimap)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    SortTaskCommand.MESSAGE_USAGE));
        }

        Criteria criteria = ParserUtil.parseCriteria(argumentMultimap.getValue(PREFIX_CRITERIA).get());
        return new SortTaskCommand(criteria);
    }

    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap) {
        return argumentMultimap.getValue(PREFIX_CRITERIA).isPresent();
    }
}
