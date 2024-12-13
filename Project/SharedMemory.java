package Project;

import java.util.HashMap;

public class SharedMemory {
    private final HashMap<String, Double> memory = new HashMap<>();

    public synchronized void assign(String processId, String variable, double value) {
        String key = processId + "_" + variable;
        memory.put(key, value);
    }

    public synchronized double get(String processId, String variable) {
        String key = processId + "_" + variable;
        if (!memory.containsKey(key)) {
            throw new IllegalArgumentException("Variable '" + variable + "' for process '" + processId + "' is not initialized");
        }
        return memory.get(key);
    }

    public synchronized double add(String processId, String var1, String var2) {
        return get(processId, var1) + get(processId, var2);
    }

    public synchronized double subtract(String processId, String var1, String var2) {
        return get(processId, var1) - get(processId, var2);
    }

    public synchronized double multiply(String processId, String var1, String var2) {
        return get(processId, var1) * get(processId, var2);
    }

    public synchronized double divide(String processId, String var1, String var2) {
        double denominator = get(processId, var2);
        if (denominator == 0) throw new ArithmeticException("Division by zero");
        return get(processId, var1) / denominator;
    }

    public synchronized void printState() {
        System.out.println("Memory State: " + memory);
    }
}