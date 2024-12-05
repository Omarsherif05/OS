package Program_parser;

import java.io.*;
import java.util.*;
import process.process;
public class programParser {
    public static List<String> parseInstructionFile(String filePath) {
        List<String> instructions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    instructions.add(line.trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return instructions;
    }
    public static process loadProgramFromFile(String filePath, int processId) {
        List<String> instructions = parseInstructionFile(filePath);
        return new process(processId, instructions);
    }
}
