package Project;

public class PCB {
    private final int processId;
    private int instructionCount;
    private int programCounter;
    private int[] memoryBoundaries = new int[2];        //its in the docx

    public PCB(int processId, int instructionCount, int boundaryOne, int boundaryTwo) {
        this.processId = processId;
        this.instructionCount = instructionCount;
        this.programCounter = 0;
        this.memoryBoundaries[0] = boundaryOne;         //its in the docx
        this.memoryBoundaries[1] = boundaryTwo;         //its in the docx
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
