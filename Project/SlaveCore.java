package Project;

import java.util.Scanner;

public class SlaveCore extends Thread {
    private final int coreId;
    private final SharedMemory sharedMemory;
    private Process currentProcess;
    private boolean idle;
    private boolean terminate;

    public SlaveCore(int coreId, SharedMemory sharedMemory) {
        this.coreId = coreId;
        this.sharedMemory = sharedMemory;
        this.idle = true;
        this.terminate = false;
    }

    public synchronized boolean isIdle() {
        return idle;
    }

    public synchronized void assignTask(Process process) {
        this.currentProcess = process;
        this.idle = false;
        notify();
    }

    public synchronized void terminate() {
        this.terminate = true;
        notify();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (currentProcess == null && !terminate) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        System.err.println("Core " +coreId+ " interrupted.");
                        return;
                    }
                }

                if (terminate && currentProcess == null) {
                    System.out.println("Core " +coreId+ " terminating.");
                    return;
                }
            }

            executeProcess();

            synchronized (this) {
                currentProcess = null;
                idle = true;
            }
        }
    }

    private void executeProcess() {
        for (String instruction : currentProcess.getInstructions()) {
            executeInstruction(instruction);
        }
    }

    private void executeInstruction(String instruction) {
        synchronized (System.out) {
            String[] parts = instruction.split(" ");
            switch (parts[0]) {
                case "assign":
                    if (parts[2].equals("input")) {
                        System.out.print("Core " +coreId+" Enter value for " + parts[1] + ": ");
                        Scanner scanner = new Scanner(System.in);
                        int value = scanner.nextInt();
                        System.out.println("Core " +coreId+" Assigned " + parts[1] + " to " + value);
                        sharedMemory.assign(parts[1], value);
                    } else if (parts[2].equals("add") || parts[2].equals("subtract") || parts[2].equals("multiply") || parts[2].equals("divide")) {
                        String operation = parts[2];
                        String var1 = parts[3];
                        String var2 = parts[4];
                        int result;

                        switch (operation) {
                            case "add":
                                result = sharedMemory.add(var1, var2);
                                break;
                            case "subtract":
                                result = sharedMemory.subtract(var1, var2);
                                break;
                            case "multiply":
                                result = sharedMemory.multiply(var1, var2);
                                break;
                            case "divide":
                                result = sharedMemory.divide(var1, var2);
                                break;
                            default:
                                throw new IllegalArgumentException("Unknown operation: " + operation);
                        }
                        sharedMemory.assign(parts[1], result);
                        System.out.println("Core " +coreId+ " Assigned " + parts[1] + " to " + result);
                    }
                    break;
                case "print":
                    String variable = parts[1];
                    System.out.println("Core " +coreId+ " Print: " + variable + " = " + sharedMemory.get(variable));
                    break;
            }
        }
    }
}
