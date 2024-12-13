package Project;

public class MasterCore {
    private int clock = 1;
    private ReadyQueue readyQueue;
    private SlaveCore core1;
    private SlaveCore core2;
    private SJFScheduler scheduler;

    public MasterCore(ReadyQueue readyQueue, SharedMemory sharedMemory) {
        this.readyQueue = readyQueue;
        this.core1 = new SlaveCore(1, sharedMemory);
        this.core2 = new SlaveCore(2, sharedMemory);
        this.scheduler = new SJFScheduler();
    }

    public void run() {
        synchronized (readyQueue) {
            if (!readyQueue.isEmpty()) {

                if (core1.isIdle()) {
                    Process process = scheduler.getShortestJob(readyQueue);
                    core1.assignTask(process);
                }

                if (core2.isIdle() && !readyQueue.isEmpty()) {
                    Process process = scheduler.getShortestJob(readyQueue);
                    core2.assignTask(process);
                }
            }
        }

        displaySystemStatus();

        while (true) {
            synchronized (readyQueue) {

                if (readyQueue.isEmpty() && core1.isIdle() && core2.isIdle()) {
                    break;
                }


                if (core1.isIdle() && !readyQueue.isEmpty()) {
                    Process process = scheduler.getShortestJob(readyQueue);
                    core1.assignTask(process);
                }

                if (core2.isIdle() && !readyQueue.isEmpty()) {
                    Process process = scheduler.getShortestJob(readyQueue);
                    core2.assignTask(process);
                }
            }


            core1.executeNextInstruction();
            core2.executeNextInstruction();

            clock++;
            displaySystemStatus();


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        waitForCompletion();
        System.out.println("\nAll processes completed.");
    }

    public void displaySystemStatus() {
        System.out.println("Clock Cycle: " + clock);
        System.out.print("Ready Queue: ");

        for (Process process : readyQueue.getProcesses()) {
            System.out.print("P" +process.getProcessId()+ " ");
        }
        System.out.println();
        System.out.println("Core 1: " + (core1.isIdle() ? "Idle" : "Executing Process " + core1.getCurrentProcessId()));
        System.out.println("Core 2: " + (core2.isIdle() ? "Idle" : "Executing Process " + core2.getCurrentProcessId()));

        core1.getSharedMemory().printState();
    }

    public void waitForCompletion() {
        try {
            core1.join();
            core2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
