package Project;

public class SlaveCore extends Thread {
    private final int coreId;
    private final SharedMemory sharedMemory;
    private Process currentProcess;
    private boolean terminate;

    public SlaveCore(int coreId, SharedMemory sharedMemory) {
        this.coreId = coreId;
        this.sharedMemory = sharedMemory;
        this.currentProcess = null;
        this.terminate = false;
    }

    public synchronized void assignTask(Process process) {
        this.currentProcess = process;
        notify(); // Notify the thread to start executing the task
    }

    public synchronized void terminate() {
        this.terminate = true;
        notify(); // Notify the thread to terminate
    }

    public synchronized boolean isIdle() {
        return currentProcess == null;
    }

    public synchronized int getCoreId() {
        return coreId;
    }

    public synchronized int getCurrentProcessId() {
        return currentProcess != null ? currentProcess.getProcessId() : -1;
    }

    @Override
    public void run() {
        while (!terminate) {
            synchronized (this) {
                while (currentProcess == null && !terminate) {
                    try {
                        wait(); // Wait for a task to be assigned
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (terminate && currentProcess == null) {
                    return;
                }
            }

            // Execute the process one instruction at a time
            while (currentProcess != null) {
                executeNextInstruction();
            }
        }
    }

    private void executeNextInstruction() {
        synchronized (this) {
            String instruction = currentProcess.getInstructions().get(currentProcess.getPcb().getProgramCounter());
            executeInstruction(instruction);
            currentProcess.getPcb().incrementProgramCounter();

            // Check if the process is complete
            if (currentProcess.getPcb().getProgramCounter() >= currentProcess.getInstructions().size()) {
                System.out.println("Core " + coreId + " completed Process " + currentProcess.getProcessId());
                currentProcess = null; // Mark the process as completed
            }
        }

        // Simulate a single clock cycle delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void executeInstruction(String instruction) {
        synchronized (System.out) {
            String[] parts = instruction.split(" ");
            switch (parts[0]) {
                case "assign":
                    if (parts[2].equals("input")) {
                        System.out.print("Core " + coreId + " Enter value for " + parts[1] + ": ");
                        sharedMemory.assign(parts[1], new java.util.Scanner(System.in).nextDouble());
                    } else if (parts.length >= 4) {
                        double result = switch (parts[2]) {
                            case "add" -> sharedMemory.add(parts[3], parts[4]);
                            case "subtract" -> sharedMemory.subtract(parts[3], parts[4]);
                            case "multiply" -> sharedMemory.multiply(parts[3], parts[4]);
                            case "divide" -> sharedMemory.divide(parts[3], parts[4]);
                            default -> throw new IllegalArgumentException("Unknown operation");
                        };
                        sharedMemory.assign(parts[1], result);
                    }
                    break;
                case "print":
                    System.out.println("Core " + coreId + " Print: " + parts[1] + " = " + sharedMemory.get(parts[1]));
                    break;
            }
        }
    }
}
