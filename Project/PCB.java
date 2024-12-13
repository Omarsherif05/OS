package Project;

public class PCB {
    private final int processId;
    private int instructionCount;
    private int programCounter;
    private int base;       //its in the docx
    private int limit;              //its in the docx
    private int topBoundary;       //its in the docx

    public PCB(int processId, int instructionCount, int base, int limit) {
        this.processId = processId;
        this.instructionCount = instructionCount;
        this.programCounter = 0;
        this.base = base;                           //its in the docx
        this.limit = limit;                         //its in the docx
        this.topBoundary = base + limit;        //its in the docx
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
