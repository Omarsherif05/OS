package Project;

public class SJFScheduler {
    public Process getShortestJob(ReadyQueue readyQueue) {

            Process shortestJob = null;
            int minInstructionCount = Integer.MAX_VALUE;

            for (Process process : readyQueue.getProcesses()) {
                int instructionCount = process.getPcb().getInstructionCount();
                if (instructionCount < minInstructionCount) {
                    minInstructionCount = instructionCount;
                    shortestJob = process;
                }
            }
            if (shortestJob != null) {
                readyQueue.removeProcess(shortestJob);
            }

            return shortestJob;
    }
}
