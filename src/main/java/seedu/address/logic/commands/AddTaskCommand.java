package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Adds a task to the task list.
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "addtask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103T "
            + PREFIX_DESCRIPTION + "lecture quiz";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";
    public static final String MODULE_NOT_FOUND = "This task's module does not exist";

    private final Task toAdd;

    /**
     * Creates a AddTaskCommand to add the specified {@code Task}
     */
    public AddTaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(toAdd.getModule())) {
            throw new CommandException(MODULE_NOT_FOUND);
        } else if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.addTask(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
