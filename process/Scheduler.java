package process;

import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
    private Queue<PCB> readyQueue = new LinkedList<>();
    private int quantum;

    public Scheduler(int quantum) {
        this.quantum = quantum;
    }

    public void addProcess(PCB pcb) {
        readyQueue.add(pcb);
    }

    public PCB getNextProcess() {
        return readyQueue.poll();
    }

    public void addBack(PCB pcb) {
        readyQueue.add(pcb);
    }

    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }

    public void displayQueue() {
        System.out.print("Ready Queue: ");
        for (PCB pcb : readyQueue) {
            System.out.print("P" + pcb.getProcessId() + " ");
        }
        System.out.println();
    }
}
