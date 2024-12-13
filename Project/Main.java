package Project;

public class Main {
    public static void main(String[] args) {

            ReadyQueue readyQueue = new ReadyQueue();
            SharedMemory sharedMemory = new SharedMemory();

            String filePath = "C:\\Users\\DELL\\IdeaProjects\\OS";
            Parser.loadProcessesFromDirectory(filePath, readyQueue);

            MasterCore masterCore = new MasterCore(readyQueue, sharedMemory);
            masterCore.run();




    }
}