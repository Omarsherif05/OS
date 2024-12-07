package Project;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ReadyQueue {
    private final Queue<Process> readyQueue;

    public ReadyQueue() {
        this.readyQueue = new LinkedList<>();
    }

    public void addProcess(Process process) {
        readyQueue.add(process);
    }

    public Process getNextProcess() {
        return readyQueue.poll();
    }

    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }
    public synchronized void removeProcess(Process process) {
        readyQueue.remove(process);
    }
    public synchronized List<Process> getProcesses() {
        return new ArrayList<>(readyQueue);
    }
}
