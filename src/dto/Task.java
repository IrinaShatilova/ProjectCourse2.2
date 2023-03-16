package dto;

import constant.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Task {

    private static int idGenerator;
    private final int id;
    private String title;
    private String description;
    private final Type type;
    private final LocalDateTime localDateTime;

    public Task(String title,
                String description,
                Type type,
                LocalDateTime localDateTime) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.localDateTime = formatDate(localDateTime);
        id = ++idGenerator;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    /**
     * Метод для проверки повторяемости задачи
     */
    public abstract boolean appearsIn(LocalDate localDate);

    /**
     * Метод для получения следующей даты выполнения задачи
     */
    public abstract LocalDate getNextDate(LocalDateTime localDateTime);

    public LocalDateTime formatDate(LocalDateTime localDateTime) {
        DateTimeFormatter fd = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String text = localDateTime.format(fd);
        return LocalDateTime.parse(text, fd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && title.equals(task.title) && description.equals(task.description) && type == task.type && localDateTime.equals(task.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, type, localDateTime);
    }

    @Override
    public String toString() {
        return "Название задачи: " + title +
                "\n   Описание задачи: " + description +
                "\n   Тип задачи: " + type.getTaskType() +
                "\n   Время создания задачи: " + localDateTime;
    }
}
