package fanny.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import fanny.task.Deadline;
import fanny.task.TaskList;
import fanny.ui.Ui;

/**
 * Represents the command that handles the "deadline" prompt.
 */
public class DeadlineCommand extends Command {

    /** String representation of the description of the deadline task. */
    private String description;
    private Deadline deadline;

    /**
     * Constructs an {@code DeadlineCommand} with the specified description.
     *
     * @param description The description of the deadline task,
     *                    including the task information and due time.
     *
     */
    public DeadlineCommand(String description) {
        this.description = description;
    }

    /**
     * Executes the command to add a deadline task to the task list.
     * Handles any errors that may occur during parsing.
     *
     * @param list The task list to which the event task is added.
     * @param ui The UI object to interact with the user.
     */
    @Override
    public String executeCmd(TaskList list, Ui ui) {
        String message = "";
        ui.showHorizontalLine();

        try {
            generateDeadline();
            list.add(this.deadline);
            message = ui.showAddTaskMsg(this.deadline, list);
        } catch (ArrayIndexOutOfBoundsException e) {
            message = ui.showMessage("Task description and deadline cannot be empty");
        } catch (DateTimeParseException e) {
            message = ui.showMessage("Please enter a valid date and time: YYYY-MM-DD HH:MM");
        } finally {
            ui.showHorizontalLine();
        }

        return message;
    }

    /**
     * Parses the description to extract the task information and deadline.
     * Generate a Deadline task based on the extracted information.
     */
    public void generateDeadline() {
        String[] cmdDeadline = this.description.split("/by ", 2);
        LocalDateTime time = LocalDateTime.parse(cmdDeadline[1], super.getFormatter());
        String description = cmdDeadline[0];
        this.deadline = new Deadline(description, time);
    }

    /**
     * Indicates that executing this command should not exit the application.
     *
     * @return {@code false}, indicating that the application should not exit.
     */
    @Override
    public boolean shouldExit() {
        return false;
    }

}
