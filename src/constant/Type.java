package constant;

public enum Type {
    WORK("рабочая"),
    PERSONAL("личная");

    private final String taskType;

    Type(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskType() {
        return taskType;
    }
}
