package Program_parser;

import process.PCB;
import process.process;
import java.io.*;
import java.util.*;

public class programParser {
    private int PID = 0;

    public process parseProgram(String fileName) {
        List<Instruction> instructions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split(" ");

                switch (parts[0]) {
                    case "assign":
                        // Variable assignment: assign x input OR assign z add a b
                        if (parts[2].equals("input")) {
                            instructions.add(new Instruction("assign", parts[1], "input", parts[3]));
                        } else {
                            instructions.add(new Instruction("assign", parts[1], parts[2], parts[3]));
                        }
                        break;

                    case "print":
                        // Print command: print x
                        instructions.add(new Instruction("print", parts[1], null, null));
                        break;

                    default:
                        System.out.println("Unknown instruction: " + line);
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return createProcess(instructions);
    }

    public process createProcess(List<Instruction> instructions) {
        int ProcessID = getPID();
        PCB pcb = new PCB(ProcessID);
        process process = new process(ProcessID, instructions, pcb);
        return process;
    }

    public int getPID(){
        int temp = PID;
        PID++;
        return temp;
    }
}
