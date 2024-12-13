package Project;

import java.io.*;
import java.util.*;

public class Parser {

    // Method to load all processes from .txt files in the given directory
    public static void loadProcessesFromDirectory(String directoryPath, ReadyQueue readyQueue) {
        File directory = new File(directoryPath);

        // Ensure the directory exists and is a directory
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

    // Method to load a single process from a .txt file
    private static void loadProcessFromFile(File file, ReadyQueue readyQueue) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int processId = file.hashCode(); // You can use any other unique identifier if needed
            List<String> instructions = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    instructions.add(line.trim());
                }
            }

            // Create a Process from the instructions in the file
            Process process = new Process(processId, instructions,0,5);
            readyQueue.addProcess(process);  // Add the process to the ready queue
            System.out.println("Loaded process from file: " + file.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
