package process;

public class PCB {
    int processId;
    int programCounter;
    int instructionCount;

    public PCB(int processId, int instructionCount) {
        this.processId = processId;

        this.programCounter = 0;

        this.instructionCount = instructionCount;

    }

    public int getProcessId() {
        return processId;

    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public int getInstructionCount() {
        return instructionCount;
    }

    public void setInstructionCount(int instructionCount) {
        this.instructionCount = instructionCount;
    }
}