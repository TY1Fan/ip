package fanny.command;

import fanny.task.TaskList;
import fanny.ui.Ui;

/**
 * Represents the command that handles the "mark" prompt.
 */
public class MarkCommand extends Command {

    /** The input from the user in the form of string. */
    private String input;

    /**
     * Constructs a {@code MarkCommand} with the user input.
     *
     * @param input The user's input.
     */
    public MarkCommand(String input) {
        this.input = input;
    }

    /**
     * Executes the "mark" command by marking the task specified in the
     * user input as complete.
     *
     * @param list The current list of tasks.
     * @param ui The UI object to interact with the user.
     */
    @Override
    public void actionable(TaskList list, Ui ui) {
        ui.showHorizontalLine();
        int taskId = Integer.parseInt(input);
        ui.showMessage("Fanny:\nNice! I've marked this task as done:");
        ui.showMessage(list.markAsDone(taskId));
        ui.showHorizontalLine();
    }

    /**
     * Indicates that executing this command should not exit the application.
     *
     * @return {@code false}, indicating that the application should not exit.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
