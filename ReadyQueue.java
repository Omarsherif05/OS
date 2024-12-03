//package PACKAGE_NAME;
public class ReadyQueue {
 private Queue<Process> ReadyQueue = new LinkedList<>();

    public void addProces(Process i){
        ReadyQueue.add(i);
    }
    public Process removeProces(){
        return  ReadyQueue.poll();

    }
    public boolean isEmpty(){
        return ReadyQueue.isEmpty();
    }

}
