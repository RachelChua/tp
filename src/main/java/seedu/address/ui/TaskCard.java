package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.task.Task;

/**
 * TaskCard represents a Task Card which contains details of the Task and the position in the
 * listView.
 */
public class TaskCard extends UiPart<Region> {
    private static final String FXML = "TaskListCard.fxml";

    public final Task task;

    @FXML
    private Label id;

    @FXML
    private Label description;

    @FXML
    private Label moduleCode;

    @FXML
    private CheckBox isComplete;

    /**
     * Constructor of the TaskCard. Sets the task and the position.
     *
     * @param task The task being set.
     * @param position The position of the task in the listView.
     */
    public TaskCard(Task task, int position) {
        super(FXML);
        this.task = task;
        id.setText(position + ". ");
        moduleCode.setText(task.getModule().getModuleCode().moduleCode);
        description.setText(task.getDescription().description);
        isComplete.setSelected(task.isComplete());
    }
}
