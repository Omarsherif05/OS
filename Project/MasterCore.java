package Project;

import java.util.List;

public class MasterCore extends Thread {
    private final ReadyQueue readyQueue;
    private final List<SlaveCore> slaves;

    public MasterCore(ReadyQueue readyQueue, SharedMemory sharedMemory) {
        this.readyQueue = readyQueue;
        this.slaves = List.of(
                new SlaveCore(1, sharedMemory),
                new SlaveCore(2, sharedMemory)
        );
    }

    @Override
    public void run() {
        // Start all slave cores
        for (SlaveCore slaveCore : slaves) {
            slaveCore.start();
        }

        // Assign processes to available slave cores
        while (!readyQueue.isEmpty() || anyCoreProcessing()) {
            synchronized (this) {
                Process process = readyQueue.getNextProcess();
                if (process != null) {
                    SlaveCore availableSlave = getAvailableSlave();
                    if (availableSlave != null) {
                        availableSlave.assignTask(process);
                    }
                }
            }
        }

        // Signal all slaves to terminate after all tasks are done
        for (SlaveCore slaveCore : slaves) {
            slaveCore.terminate();
        }

        // Wait for all slaves to finish
        for (SlaveCore slaveCore : slaves) {
            try {
                slaveCore.join();
            } catch (InterruptedException e) {
                System.err.println("MasterCore interrupted while waiting for slaves to terminate.");
            }
        }

        System.out.println("MasterCore: All slave cores have terminated.");
    }

    private SlaveCore getAvailableSlave() {
        while (true) {
            for (SlaveCore slaveCore : slaves) {
                if (slaveCore.isIdle()) {
                    return slaveCore;
                }
            }


        }
    }

    private boolean anyCoreProcessing() {
        for (SlaveCore slaveCore : slaves) {
            if (!slaveCore.isIdle()) {
                return true;
            }
        }
        return false;
    }
}
