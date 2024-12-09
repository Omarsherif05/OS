package Project;

import java.io.*;
import java.util.*;

public class Parser {


    public static void loadProcessesFromDirectory(String directoryPath, ReadyQueue readyQueue) {
        File directory = new File(directoryPath);


        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {
                        loadProcessFromFile(file, readyQueue);
                    }
                }
            } else {
                System.err.println("No files found in the directory.");
            }
        } else {
            System.err.println("The provided path is not a valid directory.");
        }
    }


    private static void loadProcessFromFile(File file, ReadyQueue readyQueue) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int processId = file.hashCode();
            List<String> instructions = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    instructions.add(line.trim());
                }
            }
            Process process = new Process(processId, instructions);
            readyQueue.addProcess(process);
            System.out.println("Loaded process from file: " + file.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
