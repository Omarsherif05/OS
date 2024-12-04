package process;

public class PCB {

    private int programCounter;
    private boolean isComplete;
    private final int PID;

    public PCB(int PID) {
        this.programCounter = 0;
        this.isComplete = false;
        this.PID = PID;
    }



    public void setProcessCompleted()
    {
        this.isComplete = true;
    }

    public int getProgramCounter() {
        return this.programCounter;
    }

    public void updateProgramCounter(){
        programCounter++;
    }

}
