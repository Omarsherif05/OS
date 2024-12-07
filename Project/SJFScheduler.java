package Project;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class SJFScheduler {
    public synchronized void sortReadyQueue(ReadyQueue readyQueue) {
        List<Process> processes = new ArrayList<>();

        // Extract processes from readyQueue and sort them by instruction count
        while (!readyQueue.isEmpty()) {
            processes.add(readyQueue.getNextProcess());
        }

        // Sort processes based on instruction count in their PCB
        processes.sort(Comparator.comparingInt(process -> process.getPcb().getInstructionCount()));

        // Add sorted processes back to the ReadyQueue
        for (Process process : processes) {
            readyQueue.addProcess(process);
        }

        // Debug: Print sorted processes
        System.out.println("SJFScheduler: Processes sorted by instruction count:");

    }
}
