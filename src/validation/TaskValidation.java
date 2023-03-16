package validation;

import dto.Task;
import exception.TaskNotFoundException;
import exception.ValidationException;

import java.util.List;
import java.util.Map;

import static constant.Constants.*;
import static java.util.Objects.isNull;

public class TaskValidation {

    private TaskValidation() {
    }

    public static void isTaskEmpty(Map<Integer, Task> taskMap) {
        if (taskMap.isEmpty()) {
            throw new TaskNotFoundException(TASK_LIST_IS_EMPTY);
        }
    }

    public static void isTaskEmpty(List<Task> tasks) {
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException(TASK_LIST_IS_EMPTY);
        }
    }

    public static void fieldValidate(Task task) {
        if (isNullOrEmpty(task.getTitle()) || isNullOrEmpty(task.getDescription()) || isNull(task.getType())) {
            throw new ValidationException(
                    String.format("Поля заданы некорретно: title=[%s], description=[%s], type=[%s]", task.getTitle(), task.getDescription(), task.getType())
            );
        }
    }

    public static boolean isNullOrEmpty(String str) {

        return isNull(str) || str.isEmpty() || str.isBlank();
    }

    public static void taskIsNull(Task task, String message) {
        if (isNull(task)) {
            throw new ValidationException(message);
        }
    }

    public static void taskIsFound(Task task) {
        if (isNull(task)) {
            throw new TaskNotFoundException(TASK_NOT_FOUND_MSG);
        }
    }
}
