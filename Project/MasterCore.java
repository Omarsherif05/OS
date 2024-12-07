package Project;

import java.util.List;

public class MasterCore extends Thread {
    private final ReadyQueue readyQueue;
    private final List<SlaveCore> slaves;
    private final SJFScheduler sjfScheduler; // Use SJF scheduler
    private int clock = 0; // Global clock

    public MasterCore(ReadyQueue readyQueue, SharedMemory sharedMemory) {
        this.readyQueue = readyQueue;
        this.slaves = List.of(
                new SlaveCore(1, sharedMemory),
                new SlaveCore(2, sharedMemory)
        );
        this.sjfScheduler = new SJFScheduler();
    }

    @Override
    public void run() {
        // Start slave cores
        for (SlaveCore slave : slaves) {
            slave.start();
        }

        while (!readyQueue.isEmpty() || anyCoreProcessing()) {
            synchronized (this) {
                // Increment the global clock
                clock++;

                // Display system status at the start of each clock cycle
                displaySystemStatus();

                // Schedule a task for an idle core
                Process nextProcess = sjfScheduler.getShortestJob(readyQueue);
                if (nextProcess != null) {
                    SlaveCore availableCore = getAvailableSlave();
                    if (availableCore != null) {
                        availableCore.assignTask(nextProcess);
                    }
                }
            }

            // Allow some time for cores to execute
            try {
                Thread.sleep(100); // Simulate a clock cycle delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Terminate slave cores
        for (SlaveCore slave : slaves) {
            slave.terminate();
        }

        // Wait for all slave cores to finish execution
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
        return null; // No idle core found
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
