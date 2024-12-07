package Project;

import java.util.List;

public class MasterCore extends Thread {
    private final ReadyQueue readyQueue;
    private final List<SlaveCore> slaves;
    private final SJFScheduler sjfScheduler;

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
        for (SlaveCore slaveCore : slaves) {
            slaveCore.start();
        }
        while (!readyQueue.isEmpty() || anyCoreProcessing()) {
            synchronized (this) {
                Process process = sjfScheduler.getShortestJob(readyQueue);
                if (process != null) {
                    SlaveCore availableSlave = getAvailableSlave();
                    if (availableSlave != null) {
                        availableSlave.assignTask(process);
                    }
                }
            }
        }
        for (SlaveCore slaveCore : slaves) {
            slaveCore.terminate();
        }
        for (SlaveCore slaveCore : slaves) {
            try {
                slaveCore.join();
            } catch (InterruptedException e) {
            }
        }
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
