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
}
