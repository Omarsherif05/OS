package process;

import Program_parser.Instruction;

import java.util.List;

public class process {

    private final int process_id;
    private final List<Instruction> instructions;
    private final PCB pcb;

    public process(int process_id, List<Instruction> instructions, PCB pcb) {
        this.process_id = process_id;
        this.instructions = instructions;
        this.pcb = new PCB(process_id);
    }

    public Instruction getCurrentInstruction() {
        Instruction inst = instructions.get(pcb.getProgramCounter());
        pcb.updateProgramCounter();
        return inst;
    }



}
