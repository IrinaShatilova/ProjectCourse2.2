import constant.Type;
import dto.*;
import exception.IncorrectArgumentException;
import exception.TaskNotFoundException;
import service.TaskService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputFromConsole {

    public static void startService(TaskService taskService, Scanner scanner) {
        getMenu();
        while (scanner.hasNextLine()) {
            String menu = scanner.nextLine();
            switch (menu) {
                case "1":
                    taskService.addTask(
                            getRegularity(getTaskTitle(),
                                    getTaskDescription(),
                                    getTaskType(),
                                    LocalDateTime.now())
                    );
                    getMenu();
                    break;
                case "2":
                    System.out.println("Список всех задач");
                    try {
                        taskService.printAllTasks();
                    } catch (TaskNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    getMenu();
                    break;
                case "3":
                    System.out.println("Введите id задачи, которую необходимо удалить: ");
                    int taskId = scanner.nextInt();
                    try {
                        taskService.removeAndArchivedTask(taskId);
                        System.out.println("Задача удалена.");
                    } catch (TaskNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Ошибка ввода! Необходимо ввести номер задачи.");
                    }
                    getMenu();
                    scanner.nextLine();
                    break;
                case "4":
                    System.out.println("Список задач на завтра ");
                    try {
                        taskService.printTasksForNextDay();
                    } catch (TaskNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    getMenu();
                    break;
                case "5":
                    System.out.println("Введите дату в формате ГГГГ-ММ-ДД: ");
                    String date = scanner.nextLine();
                    try {
                        LocalDate localDate = InputFromConsole.checkAndGetDate(date);
                        System.out.println("Список задач на " + localDate + ":");
                        try {
                            taskService.printGetTasksByDate(localDate);
                        } catch (TaskNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println(e.getMessage());
                    }
                    getMenu();
                    break;
                case "6":
                    try {
                        System.out.println("Список всех задач, сгруппированных по дате");
                        taskService.printGroupedByDate();
                    } catch (TaskNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    getMenu();
                    break;
                case "7":
                    System.out.println("Введите id задачи: ");
                    try {
                        taskId = scanner.nextInt();
                        System.out.println("Следующая дата выполнения задачи " + taskId + ": ");
                        taskService.printInfoAboutNextDateTime(taskId);
                    } catch (TaskNotFoundException e) {
                        System.out.println(e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Ошибка ввода! Введите id задачи. ");
                    }
                    getMenu();
                    scanner.nextLine();
                    break;
                case "8":
                    System.out.println("Введите id задачи: ");
                    try {
                        taskId = scanner.nextInt();
                        System.out.println("Существующее название: " + taskService.getTaskMap().get(taskId).getTitle());
                        System.out.println("Введите новое название задачи: ");
                        scanner.nextLine();
                        String title = scanner.nextLine();
                        taskService.updateTitle(taskId, title);
                        System.out.println("Название изменено");
                    } catch (InputMismatchException e) {
                        System.out.println("Ошибка ввода! Введите id задачи.");
                    }
                    getMenu();

                    break;
                case "9":
                    System.out.println("Введите id задачи: ");
                    try {
                        taskId = scanner.nextInt();
                        System.out.println("Существующее описание: " + taskService.getTaskMap().get(taskId).getDescription());
                        System.out.println("Введите новое описание задачи: ");
                        scanner.nextLine();
                        String description = scanner.nextLine();
                        taskService.updateDescription(taskId, description);
                        System.out.println("Описание изменено");
                    } catch (InputMismatchException e) {
                        System.out.println("Ошибка ввода! Введите id задачи: ");
                    }
                    getMenu();

                    break;
                case "10":

                    System.out.println("Архив удаленных задач");
                    try {
                        taskService.printRemovedTasks();
                    } catch (TaskNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    getMenu();
                    break;
                case "11":
                    System.exit(0);
                default:
                    System.out.println("Ошибка ввода! Введите цифры, указанное в меню от 1 до 11");
                    getMenu();
            }
        }
        scanner.close();

    }

    /**
     * Метод для считывания названия задачи из консоли
     */
    private static String getTaskTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название задачи: ");
        try {
            String argument = scanner.nextLine();
            checkArgument(argument);
            return argument;
        } catch (IncorrectArgumentException e) {
            System.out.println(e);
            return getTaskTitle();
        }
    }

    /**
     * Метод для считывания описания задачи из консоли
     */
    private static String getTaskDescription() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите описание задачи: ");
        try {
            String argument = scanner.nextLine();
            checkArgument(argument);
            return argument;
        } catch (IncorrectArgumentException e) {
            System.out.println(e);
            return getTaskDescription();
        }
    }

    /**
     * Метод для считывания типа задачи из консоли
     */
    private static Type getTaskType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите тип задачи (1 - рабочая, 2 - личная): ");
        String typeValue = scanner.nextLine();
        try {
            checkArgument(typeValue);
        } catch (IncorrectArgumentException e) {
            System.out.println(e);
            return getTaskType();
        }
        switch (typeValue) {
            case "1":
                return Type.WORK;
            case "2":
                return Type.PERSONAL;
            default:
                System.out.println("Выберите 1 или 2");
                return getTaskType();
        }
    }

    /**
     * Метод для выбора и считывания повторяемости выполнения задачи из консоли
     */
    private static Task getRegularity(String title, String description, Type type, LocalDateTime localDateTime) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите повторяемость выполнения задачи (1 - однократно, 2 - ежедневно, 3 - еженедельно, " +
                "4 - ежемесячно, 5 - ежегодно): ");
        String period = scanner.nextLine();
        try {
            checkArgument(period);
        } catch (IncorrectArgumentException e) {
            System.out.println(e);
            return getRegularity(title, description, type, localDateTime);
        }
        switch (period) {
            case "1":
                System.out.println("Введите дату в формате ГГГГ-ММ-ДД: ");
                try {
                    String date = scanner.nextLine();
                    checkAndGetDate(date);
                    LocalDate localDate = LocalDate.parse(date);
                    return new OneTimeTask(title, description, type, LocalDateTime.now(), localDate);
                } catch (DateTimeParseException e) {
                    System.out.println(e.getMessage());
                    return getRegularity(title, description, type, localDateTime);
                }
            case "2":
                return new DailyTask(title, description, type, LocalDateTime.now());
            case "3":
                return new WeeklyTask(title, description, type, LocalDateTime.now());
            case "4":
                return new MonthlyTask(title, description, type, LocalDateTime.now());
            case "5":
                return new YearlyTask(title, description, type, LocalDateTime.now());
            default:
                System.out.println("Выберите значение меню от 1 до 5");
                return getRegularity(title, description, type, localDateTime);
        }
    }

    private static void getMenu() {
        System.out.println("\n Выберите пункт из меню: " +
                "\n 1 - добавить новую задачу " +
                "\n 2 - вывести список всех задач с id" +
                "\n 3 - удалить задачу по id " +
                "\n 4 - вывести список задач на завтра " +
                "\n 5 - вывести список задач на указанную дату " +
                "\n 6 - вывести список всех задач, сгруппированных по дате " +
                "\n 7 - получить информацию о следующей дате выполнения задачи по id " +
                "\n 8 - редактировать название задачи " +
                "\n 9 - редактировать описание задачи " +
                "\n 10 - архив удаленных задач " +
                "\n 11 - выйти \n");
    }

    private static void checkArgument(String argument) {
        if (argument.isEmpty() || argument.isBlank()) {
            throw new IncorrectArgumentException(argument);
        }
    }

    private static LocalDate checkAndGetDate(String argument) {
        try {
            LocalDate localDate = LocalDate.parse(argument);
            if (argument.isEmpty() || argument.isBlank() || localDate.isBefore(LocalDate.now())) {
                throw new DateTimeParseException("Введена неправильная дата! Дата не может быть раньше текущей!", argument, 2);
            }
            return localDate;
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Ошибка ввода! Необходимо ввести дату в указанном формате.", argument, 0);
        }
    }
}
