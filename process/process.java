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
}
