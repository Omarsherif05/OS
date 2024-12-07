package process;

import Memory.SharedMemory;
import java.util.Scanner;

public class SlaveCore extends Thread {
    private final int coreId;
    private final SharedMemory sharedMemory;
    private process program;
    private PCB pcb;

    public SlaveCore(int coreId, SharedMemory sharedMemory) {
        this.coreId = coreId;
        this.sharedMemory = sharedMemory;
        this.program = program;
        this.pcb = program.getPcb();
    }

    public void run() {
        while (pcb.getProgramCounter() < pcb.getInstructionCount()) {
            String currentInstruction = program.getInstructions().get(pcb.getProgramCounter());
            processInstruction(currentInstruction);
            pcb.setProgramCounter(pcb.getProgramCounter() + 1);
        }
    }

    private void processInstruction(String instruction) {
        String[] parts = instruction.split(" ");
        switch (parts[0]) {
            case "assign":
                handleAssign(parts);
                break;
            case "print":
                handlePrint(parts);
                break;
            default:
                System.err.println("Unknown instruction: " + instruction);
        }
    }

    private void handleAssign(String[] parts) {
        if (parts.length == 3 && parts[2].equals("input")) {
            // Case: "assign <variable> input"
            handleInputAssign(parts[1]);
        } else if (parts.length == 3) {
            // Case: "assign <variable> <operation>"
            handleOperationAssign(parts);
        } else {
            System.err.println("Invalid assign syntax: " + String.join(" ", parts));
        }
    }

    private void handleInputAssign(String variable) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Core " + coreId + " is asking for input to assign to " + variable + ":");
        int value = scanner.nextInt();
        sharedMemory.assign(variable, value);
    }

    private void handleOperationAssign(String[] parts) {
        // Assuming the operation is always "subtract" or another binary operation
        String variable = parts[1];
        String operator = parts[2]; // "subtract" (or other operation)
        String operand1 = parts[3]; // The first variable
        String operand2 = parts[4]; // The second variable

        int result = 0;

        // Perform the operation using shared memory
        switch (operator) {
            case "add":
                result = sharedMemory.add(operand1, operand2);
                break;
            case "subtract":
                result = sharedMemory.subtract(operand1, operand2);
                break;
            case "multiply":
                result = sharedMemory.multiply(operand1, operand2);
                break;
            case "divide":
                result = sharedMemory.divide(operand1, operand2);
                break;
            default:
                System.err.println("Unknown operation: " + operator);
        }

        // Assign the result to the variable
        sharedMemory.assign(variable, result);
    }

    private void handlePrint(String[] parts) {
        if (parts.length != 2) {
            System.err.println("Invalid print syntax: " + String.join(" ", parts));
            return;
        }
        String variable = parts[1];
        int value = sharedMemory.get(variable);
        System.out.println("Core " + coreId + " prints: " + variable + " = " + value);
    }

    public int getCoreId() {
        return this.coreId;
    }

    public boolean isBusy() {
        return pcb.getProgramCounter() < pcb.getInstructionCount();
    }

    public void setProcess(process currentProcess) {
        this.program = currentProcess;

        this.pcb = currentProcess.getPcb();

    }
}
