package Project;

public class Main {
    public static void main(String[] args) {
        ReadyQueue readyQueue = new ReadyQueue();
        SharedMemory sharedMemory = new SharedMemory();

        // Update the file path to the specified folder path
        String filePath = "C:\\Users\\Karim\\IdeaProjects\\OS";
        Parser.loadProcessesFromDirectory(filePath, readyQueue);

        MasterCore masterCore = new MasterCore(readyQueue, sharedMemory);
        masterCore.run();

        sharedMemory.printState();
    }
}