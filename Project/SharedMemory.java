package Project;

import java.util.HashMap;

public class SharedMemory {
    private final HashMap<String, Double> memory = new HashMap<>();

    public void assign(String variable, double value) {
        memory.put(variable, value);
    }

    public Double get(String variable) {
        return memory.getOrDefault(variable, 0.0);
    }

    public Double add(String var1, String var2) {
        return get(var1) + get(var2);
    }

    public Double subtract(String var1, String var2) {
        return get(var1) - get(var2);
    }

    public Double multiply(String var1, String var2) {
        return get(var1) * get(var2);
    }

    public Double divide(String var1, String var2) {
        Double denominator = get(var2);
        if (denominator == 0) throw new ArithmeticException("Division by zero");
        return get(var1) / denominator;
    }

    public void printState() {
        System.out.println("Memory State: " + memory);
    }
}
