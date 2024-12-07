package Project;

import java.io.*;
import java.util.*;

public class Parser {

    public static void loadProcessesFromFile(String filePath, ReadyQueue readyQueue) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int processId = 1;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    List<String> instructions = Arrays.asList(line.trim().split(";"));
                    Process process = new Process(processId++, instructions);
                    readyQueue.addProcess(process);
                }
            }
        } catch (IOException e) {
        }
    }
}
