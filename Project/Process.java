package Project;

import java.util.List;

public class Process {
    private final PCB pcb;
    private final List<String> instructions;

    public Process(int processId, List<String> instructions, int boundaryOne, int boundaryTwo) {
        int instructionCount = calculateInstructionCount(instructions);
        this.pcb = new PCB(processId, instructionCount, boundaryOne, boundaryTwo);
        this.instructions = instructions;
    }

    public int getProcessId() {
        return pcb.getProcessId();
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public int getInstructionCount() {
        return pcb.getInstructionCount();
    }

    public PCB getPcb() {
        return pcb;
    }

    private int calculateInstructionCount(List<String> instructions) {
        int count = 0;
        for (String instruction : instructions) {
            if (instruction.startsWith("assign")) {
                count += 2;
            } else if (instruction.startsWith("print")) {
                count += 1;
            }
        }
        return count;
    }
}
