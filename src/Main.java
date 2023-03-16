import service.TaskService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskService taskService = new TaskService();
        Scanner scanner = new Scanner(System.in);
        InputFromConsole.startService(taskService, scanner);
    }
}



