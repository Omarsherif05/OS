package process;

import Memory.SharedMemory;
import Queues.readyQueue;

import java.util.ArrayList;
import java.util.List;

public class MasterCore {
    private final SharedMemory sharedMemory;
    private final readyQueue readyQueue; // Queue containing processes
    private final List<SlaveCore> slaveCores; // List of slave cores for execution

    public MasterCore(SharedMemory sharedMemory, readyQueue readyQueue) {
        this.sharedMemory = sharedMemory;
        this.readyQueue = readyQueue;
        this.slaveCores = new ArrayList<>();
        // Initializing slave cores
        this.slaveCores.add(new SlaveCore(1, sharedMemory, null));
        this.slaveCores.add(new SlaveCore(2, sharedMemory, null));
    }

    public void startExecution() {
        // Start all slave cores
        for (SlaveCore slaveCore : slaveCores) {
            slaveCore.start();
        }

        // Assign processes from the ready queue to the slave cores
        while (!readyQueue.isEmpty()) {
            process currentProcess = readyQueue.popProcess();
            assignProcessToSlaveCore(currentProcess);
        }

        // Wait for all slave cores to finish their tasks
        for (SlaveCore slaveCore : slaveCores) {
            try {
                slaveCore.join(); // Ensures that main thread waits for all slave cores to finish
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void assignProcessToSlaveCore(process currentProcess) {
        // Find an available slave core (one that is not busy)
        for (SlaveCore slaveCore : slaveCores) {
            if (!slaveCore.isAlive()) { // If the core is not executing, assign a new task
                slaveCore = new SlaveCore(slaveCore.getCoreId(), sharedMemory, currentProcess);
                slaveCore.start();
                break;
            }
        }
    }
}
