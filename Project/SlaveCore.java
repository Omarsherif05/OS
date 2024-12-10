package Project;

public class SlaveCore extends Thread {
    private final int coreId;
    private final SharedMemory sharedMemory;
    private Process currentProcess;
    private boolean terminate;

    public SlaveCore(int coreId, SharedMemory sharedMemory) {
        this.coreId = coreId;
        this.sharedMemory = sharedMemory;
        this.terminate = false;
    }

    public synchronized void assignTask(Process process) {
        this.currentProcess = process;
    }

    public synchronized void terminate() {
        this.terminate = true;
        notify();
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
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (terminate && currentProcess == null) {
                    return;
                }
            }


            if (currentProcess != null) {
                executeNextInstruction();
            }
        }
    }


    private void executeNextInstruction() {
        synchronized (this) {
            if (currentProcess != null) {
                int pc = currentProcess.getPcb().getProgramCounter();
                if (pc < currentProcess.getInstructions().size()) {
                    String instruction = currentProcess.getInstructions().get(pc);
                    executeInstruction(instruction);
                    currentProcess.getPcb().incrementProgramCounter();
                }


                if (currentProcess.getPcb().getProgramCounter() >= currentProcess.getInstructions().size()) {
                    System.out.println("Core " + coreId + " completed Process " + currentProcess.getProcessId());
                    currentProcess = null;
                }
            }
        }


        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void executeInstruction(String instruction) {
        synchronized (System.out) {
            try {
                String[] parts = instruction.split(" ");
                if (parts.length == 0) {
                    throw new IllegalArgumentException("Empty instruction received");
                }

                switch (parts[0]) {
                    case "assign":
                        handleAssignInstruction(parts);
                        break;
                    case "print":
                        handlePrintInstruction(parts);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown instruction: " + parts[0]);
                }
            } catch (Exception e) {
                System.err.println("Error in Core " + coreId + " while executing instruction '" + instruction + "': ");
                e.printStackTrace();
            }
        }
    }

    private void handleAssignInstruction(String[] parts) {
        try {
            if (parts[2].equals("input")) {
                System.out.print("Core " + coreId + " Enter value for " + parts[1] + ": ");
                double inputValue = new java.util.Scanner(System.in).nextDouble();
                sharedMemory.assign(parts[1], inputValue);
            } else if (parts.length >= 4) {
                double result = switch (parts[2]) {
                    case "add" -> sharedMemory.add(parts[3], parts[4]);
                    case "subtract" -> sharedMemory.subtract(parts[3], parts[4]);
                    case "multiply" -> sharedMemory.multiply(parts[3], parts[4]);
                    case "divide" -> sharedMemory.divide(parts[3], parts[4]);
                    default -> throw new IllegalArgumentException("Unknown operation: " + parts[2]);
                };
                sharedMemory.assign(parts[1], result);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error in 'assign' instruction", e);
        }
    }

    private void handlePrintInstruction(String[] parts) {
        try {
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid 'print' instruction format");
            }
            String variableName = parts[1];
            double value = sharedMemory.get(variableName);
            System.out.println("Core " + coreId + " Print: " + variableName + " = " + value);
        } catch (Exception e) {
            throw new RuntimeException("Error in 'print' instruction", e);
        }
    }

}
