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

        while (!readyQueue.isEmpty() || anyCoreProcessing()) {
            synchronized (this) {
                clock++;

                displaySystemStatus();

                Process nextProcess = sjfScheduler.getShortestJob(readyQueue);
                if (nextProcess != null) {
                    SlaveCore availableCore = getAvailableSlave();
                    if (availableCore != null) {
                        availableCore.assignTask(nextProcess);
                    }
                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Terminate slave cores
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

        System.out.println("All processes completed.");
    }

    private void displaySystemStatus() {
        System.out.println("\nClock Cycle: " + clock);
        System.out.println("Ready Queue: " + readyQueue.getProcesses());
        for (SlaveCore slave : slaves) {
            String status = slave.isIdle() ? "Idle" : "Executing Process " + slave.getCurrentProcessId();
            System.out.println("Core " + slave.getCoreId() + ": " + status);
        }
    }

    private SlaveCore getAvailableSlave() {
        for (SlaveCore slave : slaves) {
            if (slave.isIdle()) {
                return slave;
            }
        }
        return null;
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
