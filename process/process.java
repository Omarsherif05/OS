package process;

import java.util.List;

public class process {
    int processId;
    List<String> instructions;
    PCB pcb;

    public process(int processId, List<String> instructions) {
        this.processId = processId;
        this.instructions = instructions;
        this.pcb = new PCB(processId, instructions.size());
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public PCB getPcb() {
        return pcb;
    }

    public void setPcb(PCB pcb) {
        this.pcb = pcb;
    }
}