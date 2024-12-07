package Project;

public class Main {
    public static void main(String[] args) {
        ReadyQueue readyQueue = new ReadyQueue();
        SharedMemory sharedMemory = new SharedMemory();

        String filePath = "Program_1.txt";
        Parser.loadProcessesFromFile(filePath, readyQueue);

        MasterCore masterCore = new MasterCore(readyQueue, sharedMemory);
        masterCore.run();

        sharedMemory.printState();
    }
}