package Project;

import java.util.List;

public class MasterCore extends Thread {
    private final ReadyQueue readyQueue;
    private final List<SlaveCore> slaves;
    private final SJFScheduler sjfScheduler;
    private int clock = 0;

    public MasterCore(ReadyQueue readyQueue, SharedMemory sharedMemory) {
        this.readyQueue = readyQueue;
        this.slaves = List.of(
                new SlaveCore(1, sharedMemory),
                new SlaveCore(2, sharedMemory)
        );
        this.sjfScheduler = new SJFScheduler();
    }

    public void run() {
        for (SlaveCore slave : slaves) {
            slave.start();
        }

        //cycle1
        clock = 1;
        for (SlaveCore slave : slaves) {
            Process nextProcess = sjfScheduler.getShortestJob(readyQueue);
            if (nextProcess != null) {
                slave.assignTask(nextProcess);
            }
        }
        displaySystemStatus();

        //cycle2
        while (!readyQueue.isEmpty() || anyCoreProcessing()) {
            synchronized (this) {
                clock++;
                displaySystemStatus();

                for (SlaveCore slave : slaves) {
                    synchronized (slave) {
                        slave.notify();
                    }
                }

                for (SlaveCore slave : slaves) {
                    if (slave.isIdle()) {
                        Process nextProcess = sjfScheduler.getShortestJob(readyQueue);
                        if (nextProcess != null) {
                            slave.assignTask(nextProcess);
                        }
                    }
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (SlaveCore slave : slaves) {
            slave.terminate();
        }

        for (SlaveCore slave : slaves) {
            try {
                slave.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nAll processes completed.");
    }

    private synchronized void displaySystemStatus() {
        System.out.println("\nClock Cycle: " + clock);

        System.out.print("Ready Queue: ");
        for (Process process : readyQueue.getProcesses()) {
            System.out.print("P" + process.getProcessId() + " ");
        }
        System.out.println();

        for (SlaveCore slave : slaves) {
            String status = slave.isIdle() ? "Idle" : "Executing Process P" + slave.getCurrentProcessId();
            System.out.println("Core " + slave.getCoreId() + ": " + status);
        }
        slaves.get(0).getSharedMemory().printState();
    }

    private boolean anyCoreProcessing() {
        for (SlaveCore slave : slaves) {
            if (!slave.isIdle()) {
                return true;
            }
        }
        return false;
    }
}