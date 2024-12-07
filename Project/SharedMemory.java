package Project;

import java.util.concurrent.ConcurrentHashMap;

public class SharedMemory {
    private final ConcurrentHashMap<String, Integer> memory = new ConcurrentHashMap<>();

    public void assign(String variable, int value) {
        memory.put(variable, value);
    }

    public int get(String variable) {
        return memory.getOrDefault(variable, 0);
    }

    public int add(String var1, String var2) {
        return get(var1) + get(var2);
    }

    public int subtract(String var1, String var2) {
        return get(var1) - get(var2);
    }

    public int multiply(String var1, String var2) {
        return get(var1) * get(var2);
    }

    public int divide(String var1, String var2) {
        int denominator = get(var2);
        if (denominator == 0) throw new ArithmeticException("Division by zero");
        return get(var1) / denominator;
    }

    public void printState() {
        System.out.println("Memory State: " + memory);
    }
}
