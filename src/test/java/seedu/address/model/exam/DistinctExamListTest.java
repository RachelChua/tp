package seedu.address.model.exam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EXAMTWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_EXAMTWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_EXAMTWO;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExams.EXAMONE;
import static seedu.address.testutil.TypicalExams.EXAMTWO;
import static seedu.address.testutil.TypicalExams.FINAL_EXAM;
import static seedu.address.testutil.TypicalExams.MIDTERM_EXAM;
import static seedu.address.testutil.TypicalExams.getTypicalExams;
import static seedu.address.testutil.TypicalModules.CS2030;
import static seedu.address.testutil.TypicalModules.CS2040;
import static seedu.address.testutil.TypicalTasks.TASK_A;
import static seedu.address.testutil.TypicalTasks.TASK_B;
import static seedu.address.testutil.TypicalTasks.TASK_D;
import static seedu.address.testutil.TypicalTasks.TASK_F;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.exam.exceptions.DuplicateExamException;
import seedu.address.model.exam.exceptions.ExamIdentityModifiedException;
import seedu.address.model.exam.exceptions.ExamNotFoundException;
import seedu.address.model.module.Module;
import seedu.address.model.task.DistinctTaskList;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.model.task.exceptions.WrongTaskModifiedException;
import seedu.address.testutil.ExamBuilder;
import seedu.address.testutil.ModuleBuilder;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TypicalExams;
import seedu.address.testutil.TypicalTasks;

public class DistinctExamListTest {

    private final DistinctExamList distinctExamList = new DistinctExamList();

    @Test
    public void contains_nullExam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctExamList.contains(null));
    }

    @Test
    public void contains_examNotInList_returnsFalse() {
        assertFalse(distinctExamList.contains(EXAMONE));
    }

    @Test
    public void contains_examInList_returnsTrue() {
        distinctExamList.addExam(EXAMONE);
        assertTrue(distinctExamList.contains(EXAMONE));
    }

    @Test
    public void contains_examWithSameIdentityFieldsInList_returnsTrue() {
        distinctExamList.addExam(EXAMONE);
        Exam editedExam = new ExamBuilder(EXAMONE).withDate(VALID_DATE_EXAMTWO)
                .withDescription(VALID_DESCRIPTION_EXAMTWO)
                .withModule(VALID_MODULE_EXAMTWO).build();
        distinctExamList.replaceExam(distinctExamList.examList.get(0), editedExam, false);
        assertTrue(distinctExamList.contains(editedExam));
    }

    @Test
    public void containsModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctExamList.containsModule(null));
    }

    @Test
    public void containsModule_moduleNotInList_returnsFalse() {
        assertFalse(distinctExamList.containsModule(CS2030));
    }

    @Test
    public void containsModule_moduleInList_returnsTrue() {
        distinctExamList.addExam(EXAMONE);
        Module actualModule = EXAMONE.getModule();
        assertTrue(distinctExamList.containsModule(actualModule));
    }

    @Test
    public void containsModule_moduleWithSameIdentityFieldsInList_returnsTrue() {
        distinctExamList.addExam(EXAMONE);
        Module actualModule = new ModuleBuilder()
            .withModuleCode(EXAMONE.getModule().getModuleCode().moduleCode).build();
        assertTrue(distinctExamList.containsModule(actualModule));
    }

    @Test
    public void add_nullExam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctExamList.addExam(null));
    }

    @Test
    public void add_duplicateExam_throwsDuplicateExamException() {
        distinctExamList.addExam(EXAMONE);
        assertThrows(DuplicateExamException.class, () -> distinctExamList.addExam(EXAMONE));
    }

    @Test
    public void replaceExam_nullTargetExam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            distinctExamList.replaceExam(null, EXAMONE, false));
    }

    @Test
    public void replaceExam_nullEditedExam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctExamList
            .replaceExam(EXAMONE, null, false));
    }

    @Test
    public void replaceExam_targetExamNotInList_throwsExamNotFoundException() {
        assertThrows(ExamNotFoundException.class, () -> distinctExamList.replaceExam(EXAMONE, EXAMTWO, false));
    }

    @Test
    public void replaceExam_expectedSameExam_success() {
        distinctExamList.addExam(EXAMONE);
        distinctExamList.replaceExam(EXAMONE, EXAMONE, true);
        DistinctExamList expectedDistinctExamList = new DistinctExamList();
        expectedDistinctExamList.addExam(EXAMONE);
        assertEquals(expectedDistinctExamList, distinctExamList);
    }

    @Test
    public void replaceExam_expectedSameExamButIsDifferentExam_throwsWrongExamModifiedException() {
        distinctExamList.addExam(EXAMONE);
        assertThrows(ExamIdentityModifiedException.class, () -> distinctExamList
            .replaceExam(EXAMONE, EXAMTWO, true));
    }

    @Test
    public void replaceExam_expectedDifferentExam_success() {
        distinctExamList.addExam(EXAMONE);
        distinctExamList.replaceExam(EXAMONE, EXAMTWO, false);
        DistinctExamList expectedDistinctTaskList = new DistinctExamList();
        expectedDistinctTaskList.addExam(EXAMTWO);
        assertEquals(expectedDistinctTaskList, distinctExamList);
    }

    @Test
    public void replaceExam_expectedDifferentExamButIsSameExam_throwsDuplicateExamException() {
        // edited task has non-unique identity
        distinctExamList.addExam(EXAMONE);
        distinctExamList.addExam(EXAMTWO);
        assertThrows(DuplicateExamException.class, () -> distinctExamList.replaceExam(EXAMONE, EXAMTWO, false));
    }

    @Test
    public void remove_nullExam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctExamList.remove(null));
    }

    @Test
    public void remove_examDoesNotExist_throwsExamNotFoundException() {
        assertThrows(ExamNotFoundException.class, () -> distinctExamList.remove(EXAMONE));
    }

    @Test
    public void remove_existingExam_removesExam() {
        distinctExamList.addExam(EXAMONE);
        distinctExamList.remove(EXAMONE);
        DistinctExamList expectedDistinctExamList = new DistinctExamList();
        assertEquals(expectedDistinctExamList, distinctExamList);
    }

    @Test
    public void updateTotalNumOfTasks_nullExam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            distinctExamList.updateTotalNumOfTasks(null, new DistinctTaskList()));
    }

    @Test
    public void updateTotalNumOfTasks_nullTasks_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            distinctExamList.updateTotalNumOfTasks(EXAMONE, null));
    }

    @Test
    public void updateTotalNumOfTasks_examDoesNotExist_throwsExamNotFoundException() {
        assertThrows(ExamNotFoundException.class, () ->
            distinctExamList.updateTotalNumOfTasks(EXAMONE, new DistinctTaskList()));
    }
    /*
    @Test
    public void updateTotalNumOfTasks_success() {
        distinctExamList.setExams(TypicalExams.getTypicalExams());
        DistinctTaskList tasks = new DistinctTaskList();
        tasks.setTasks(TypicalTasks.getTypicalTasks());
        distinctExamList.updateTotalNumOfTasks(MIDTERM_EXAM, tasks);
        Exam expectedExam = new ExamBuilder(MIDTERM_EXAM).withTotalNumOfTasks(1);
        assertTrue(distinctExamList.examList.get(0).hasAllSameFields(expectedExam));
    }

     */

    @Test
    public void updateNumOfCompletedTasks_nullExam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            distinctExamList.updateTotalNumOfTasks(null, new DistinctTaskList()));
    }

    @Test
    public void updateNumOfCompletedTasks_nullTasks_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            distinctExamList.updateTotalNumOfTasks(EXAMONE, null));
    }

    @Test
    public void updateNumOfCompletedTasks_examDoesNotExist_throwsExamNotFoundException() {
        assertThrows(ExamNotFoundException.class, () ->
            distinctExamList.updateTotalNumOfTasks(EXAMONE, new DistinctTaskList()));
    }
/*
    @Test
    public void updateNumOfCompletedTasks_success() {
        distinctExamList.setExams(TypicalExams.getTypicalExams());
        DistinctTaskList tasks = new DistinctTaskList();
        tasks.setTasks(TypicalTasks.getTypicalTasks());
        Task taskWithWrongExam = new TaskBuilder(TASK_B).withExam(FINAL_EXAM).build();
        tasks.addTask(taskWithWrongExam);
        distinctExamList.updateTotalNumOfTasks(MIDTERM_EXAM, tasks);

        // count for MIDTERM_EXAM is updated
        Exam expectedExam = new ExamBuilder(MIDTERM_EXAM).withTotalNumOfTasks(1);
        assertTrue(distinctExamList.examList.get(0).hasAllSameFields(expectedExam));

        // count for FINAL_EXAM not updated
        assertTrue(distinctExamList.examList.get(1).hasAllSameFields(CS2040));
    }

    @Test
    public void resetAllTaskCount() {
    // no tasks
        Exam finalExamWithCount = new ExamBuilder(FINAL_EXAM)
            .withNumOfCompletedTasks(2).withTotalNumOfTasks(5).build();
        Exam midtermExamWithCount = new ExamBuilder(MIDTERM_EXAM)
            .withNumOfCompletedTasks(3).build();
        distinctExamList.addExam(finalExamWithCount);
        distinctExamList.addExam(midTermExamWithCount);
        distinctExamList.resetAllTaskCount();
        distinctExamList.examList.forEach(exam -> {
            assertEquals(exam.getNumOfCompletedTasks() == 0);
            assertEquals(exam.getTotalNumOfTasks() == 0);
        });
    }

 */

    @Test
    public void deleteExamsWithModule_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            distinctExamList.deleteExamsWithModule(null));
    }

    @Test
    public void deleteExamsWithModule_success() {
        distinctExamList.setExams(getTypicalExams());
        distinctExamList.deleteExamsWithModule(CS2030);
        DistinctExamList expectedList = new DistinctExamList();
        expectedList.setExams(new ArrayList(Arrays.asList(MIDTERM_EXAM, EXAMONE, EXAMTWO)));
        assertEquals(expectedList, distinctExamList);
    }

    @Test
    public void setExams_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> distinctExamList.setExams(null));
    }

    @Test
    public void setTasks_list_replacesOwnListWithProvidedList() {
        distinctExamList.addExam(EXAMONE);
        List<Exam> examList = Collections.singletonList(EXAMTWO);
        distinctExamList.setExams(examList);
        DistinctExamList expectedDistinctExamList = new DistinctExamList();
        expectedDistinctExamList.addExam(EXAMTWO);
        assertEquals(expectedDistinctExamList, distinctExamList);
    }

    @Test
    public void getUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> distinctExamList.getUnmodifiableExamList().remove(0));
    }
}
