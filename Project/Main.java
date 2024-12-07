package Project;

public class Main {
    public static void main(String[] args) {
        ReadyQueue readyQueue = new ReadyQueue();
        SharedMemory sharedMemory = new SharedMemory();

        // Load processes from file
        String filePath = "Program_2.txt";
        Parser.loadProcessesFromFile(filePath, readyQueue);


        // Start the MasterCore
        MasterCore masterCore = new MasterCore(readyQueue, sharedMemory);
        masterCore.run();

        sharedMemory.printState();
    }
}