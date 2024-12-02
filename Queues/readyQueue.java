package Queues;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import process.process;
public class readyQueue {

    private Queue<process> readyQueue;

    public readyQueue(){
        this.readyQueue = new LinkedList<process>();
    }

    public void addProcess(process p)
    {
        readyQueue.add(p);
    }

    public process popProcess()
    {
        if(!readyQueue.isEmpty()) {
            process temp = readyQueue.peek();
            readyQueue.remove();
            return temp;
        }
        return null;
    }
}
