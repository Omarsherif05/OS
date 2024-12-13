package Project;

public class Main {
    public static void main(String[] args) {

        ReadyQueue readyQueue = new ReadyQueue();
        SharedMemory sharedMemory = new SharedMemory();

        String filePath = "C:\\Users\\Karim\\Downloads\\OS project (4)";
        Parser.loadProcessesFromDirectory(filePath, readyQueue);

        MasterCore masterCore = new MasterCore(readyQueue, sharedMemory);
        masterCore.run();
    }
}