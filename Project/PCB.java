package Project;

public class PCB {
    private final int processId;
    private int instructionCount;
    private int programCounter;

    public PCB(int processId, int instructionCount) {
        this.processId = processId;
        this.instructionCount = instructionCount;
        this.programCounter = 0;
    }

    public int getProcessId() {
        return processId;
    }

    public int getInstructionCount() {
        return instructionCount;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void incrementProgramCounter() {
        programCounter++;
    }
    public void incrementInstructionCount(int count) {
        this.instructionCount += count;
    }
}
