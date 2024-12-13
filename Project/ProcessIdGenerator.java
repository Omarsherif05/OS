package Project;

public class ProcessIdGenerator {
    private static int currentId = 1;

    public static synchronized int generateId() {
        return currentId++;
    }
}