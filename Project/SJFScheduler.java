package Project;

public class SJFScheduler {
    public Process getShortestJob(ReadyQueue readyQueue) {

            Process shortestJob = null;
            int minInstructionCount = Integer.MAX_VALUE;

            // Iterate through the processes in the ready queue and find the one with the least instruction count
            for (Process process : readyQueue.getProcesses()) { // Assuming `getProcesses()` retrieves all processes
                int instructionCount = process.getPcb().getInstructionCount();
                if (instructionCount < minInstructionCount) {
                    minInstructionCount = instructionCount;
                    shortestJob = process;
                }
            }

            // If a process with the smallest instruction count was found, remove it from the queue
            if (shortestJob != null) {
                readyQueue.removeProcess(shortestJob);
            }

            return shortestJob;
    }
}
