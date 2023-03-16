package service;

import dto.GroupByDate;
import dto.Task;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static constant.Constants.*;
import static validation.TaskValidation.*;

public class TaskService {
    private Map<Integer, Task> taskMap = new HashMap<>();
    private List<Task> removedTasks = new ArrayList<>();

    public Map<Integer, Task> getTaskMap() {
        return taskMap;
    }

    /**
     * Метод для добавления задач
     */
    public void addTask(Task task) {
        taskIsNull(task, TASK_NOT_FOUND_MSG);
        fieldValidate(task);
        taskMap.put(task.getId(), task);
    }

    /**
     * Метод для удаления задачи из списка по идентификационному номеру задачи
     */
    public void removeAndArchivedTask(int id) {
        Task task = taskMap.remove(id);
        taskIsFound(task);
        removedTasks.add(task);
    }

    /**
     * Метод для изменения названия задачи
     */
    public void updateTitle(int id, String title) {
        isTaskEmpty(taskMap);
        Task task = taskMap.get(id);
        taskIsNull(task, TASK_NOT_FOUND_MSG);
        task.setTitle(title);
        fieldValidate(task);
    }

    /**
     * Метод для изменения описания задачи
     */
    public void updateDescription(int id, String description) {
        isTaskEmpty(taskMap);
        Task task = taskMap.get(id);
        taskIsNull(task, TASK_NOT_FOUND_MSG);
        task.setDescription(description);
        fieldValidate(task);
    }

    /**
     * Метод для вывода списка всех задач в консоль
     */
    public void printAllTasks() {
        isTaskEmpty(taskMap);
        taskMap.forEach((key, value) -> System.out.println(key + ". " + value));
    }

    /**
     * Метод для вывода списка задач на следующий день в консоль
     */
    public void printTasksForNextDay() {
        getTasksForNextDay().forEach(System.out::println);
    }

    /**
     * Метод для списка задач на на указанную дату в консоль
     */
    public void printGetTasksByDate(LocalDate localDate) {
        getTasksByDate(localDate).forEach(System.out::println);
    }

    /**
     * Метод для вывода архива удаленных задач в консоль
     */
    public void printRemovedTasks() {
        isTaskEmpty(removedTasks);
        removedTasks.forEach(System.out::println);
    }

    /**
     * Метод для получения следующей даты выполнения задачи по идентификационному номеру задачи
     */
    public void printInfoAboutNextDateTime(int id) {
        Task task = taskMap.get(id);
        taskIsNull(task, TASK_NOT_FOUND_MSG);
        System.out.println(task.getNextDate(task.getLocalDateTime()));
    }

    /**
     * Метод для вывода в консоль списка всех задач, сгруппированных по дате
     */
    public void printGroupedByDate() {
        Map<LocalDate, List<Task>> groupedByDate = getGroupedByDate();
        for (Map.Entry<LocalDate, List<Task>> date : groupedByDate.entrySet()) {
            System.out.println(date.getKey() + ":");
            List<Task> tasks = date.getValue();
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    /**
     * Метод для получения списка задач на указанную дату
     */
    private List<Task> getTasksByDate(LocalDate localDate) {
        isTaskEmpty(taskMap);
        return taskMap
                .values()
                .stream()
                .filter(task -> task.appearsIn(localDate))
                .collect(Collectors.toList());
    }

    /**
     * Метод для получения списка задач на следующий день
     */
    private List<Task> getTasksForNextDay() {
        isTaskEmpty(taskMap);
        return taskMap
                .values()
                .stream()
                .filter(task -> task.appearsIn(task.getLocalDateTime().toLocalDate().plusDays(1)))
                .collect(Collectors.toList());

    }

    /**
     * Метод для получения списка всех задач, сгруппированных по дате
     */
    private Map<LocalDate, List<Task>> getGroupedByDate() {
        isTaskEmpty(taskMap);
        List<LocalDate> localDateList = taskMap
                .values()
                .stream()
                .map(task -> task.getNextDate(task.getLocalDateTime()))
                .sorted(new GroupByDate())
                .collect(Collectors.toList());

        Map<LocalDate, List<Task>> allTasksGroupedByDate = new LinkedHashMap<>();
        for (LocalDate localDate : localDateList) {
            allTasksGroupedByDate.put(localDate, getTasksByDate(localDate));
        }
        return allTasksGroupedByDate;
    }
}