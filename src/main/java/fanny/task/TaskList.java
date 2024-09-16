package fanny.task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import fanny.storage.Storage;

/**
 * Manages a list of tasks and interacts with storage to load and save tasks.
 */
public class TaskList {

    /** The list of tasks. */
    private List<Task> list;

    /** The storage handler for saving and loading tasks. */
    private Storage storage;

    /**
     * Constructs a {@code TaskList} and initializes it with tasks loaded from storage.
     *
     * @param storage The storage object used for loading and saving tasks.
     */
    public TaskList(Storage storage) {
        this.list = new ArrayList<>();
        this.storage = storage;
        try {
            this.list = storage.loadTask();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Adds a task to the list and saves the updated list to storage.
     *
     * @param item The task to be added.
     */
    public void add(Task item) {
        try {
            this.list.add(item);
            storage.save(this.list);
        } catch (IOException e) {
            System.out.println("Error saving task");
        }
    }

    /**
     * Deletes a task from the list by its index and saves the updated list to storage.
     *
     * @param index The 1-based index of the task to be deleted.
     * @return A string representing the task that was removed.
     */
    public String delete(int index) {
        String taskToBeRemoved = list.get(index - 1).toString();
        try {
            this.list.remove(index - 1);
            storage.save(this.list);
        } catch (IOException e) {
            System.out.println("Error saving task");
        }
        return taskToBeRemoved;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int getLength() {
        return this.list.size();
    }

    /**
     * Prints the list of tasks to the console.
     */
    public String printList() {
        String message = "Here are the tasks in your list:\n";

        for (int i = 0; i < this.list.size(); i++) {
            message = message + (i + 1) + "." + list.get(i).toString() + "\n";
        }

        System.out.println(message);
        return message;
    }

    /**
     * Marks a task as done by its index and saves the updated list to storage.
     *
     * @param index The 1-based index of the task to be marked as done.
     * @return A string representing the task after it has been marked as done.
     */
    public String markAsDone(int index) {
        assert index > 0 && index <= this.list.size() : "Index out of bounds.";
        Task taskToMark = this.list.get(index - 1);
        try {
            taskToMark.markAsDone();
            storage.save(this.list);
        } catch (IOException e) {
            System.out.println("Error saving task");
        }
        return taskToMark.toString();
    }

    /**
     * Marks a task as not done by its index and saves the updated list to storage.
     *
     * @param index The 1-based index of the task to be marked as not done.
     * @return A string representing the task after it has been marked as not done.
     */
    public String markAsNotDone(int index) {
        assert index > 0 && index <= this.list.size() : "Index out of bounds.";
        Task taskToUnMark = this.list.get(index - 1);
        try {
            taskToUnMark.markAsNotDone();
            storage.save(this.list);
        } catch (IOException e) {
            System.out.println("Error saving task");
        }
        return taskToUnMark.toString();
    }

    /**
     * Finds the list of task that matches the keyword provided using Streams.
     *
     * @param keyword The keyword to search for.
     * @return A list of filtered tasks that contains the keyword.
     */
    public List<Task> findTasks(String keyword) {
        assert keyword != null : "Keyword is null.";
        return list.stream()
                .filter(task -> task.getDescription().contains(keyword))
                .toList();
    }

    /**
     * Gets a list of tasks with upcoming deadlines.
     *
     * @return List of tasks with deadlines within the next 24 hours.
     */
    public List<Deadline> getUpcomingDeadlines() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextDay = now.plusDays(1);

        // Filter tasks that are deadlines and due within 24 hours
        return list.stream()
                .filter(task -> task instanceof Deadline)
                .map(task -> (Deadline) task)
                .filter(deadline -> deadline.getDeadlineTime().isAfter(now)
                        && deadline.getDeadlineTime().isBefore(nextDay))
                .toList();
    }

}
