package Memory;

import java.util.concurrent.ConcurrentHashMap;

public class SharedMemory {
    private final ConcurrentHashMap<String, Integer> memory = new ConcurrentHashMap<>();

    public void assign(String variable, int value) {
        memory.put(variable, value);
        System.out.println("Assigned " + variable + " = " + value);

    }

    public int get(String variable) {
        return memory.getOrDefault(variable, 0);
    }

    public void printState() {
        System.out.println("Current Memory State: " + memory);
    }

    public int add(String var1, String var2) {
        return get(var1)+get(var2);
    }

    public int subtract(String var1, String var2) {
        return get(var1)-get(var2);
    }

    public int multiply(String var1, String var2) {
        return get(var1)*get(var2);
    }
    public int divide(String var1, String var2) {
        return get(var1)/get(var2);
    }
}
