/**
 * Process Class - Stores Process Instance
 * Runs a Thread to Simulate Process Scheduling
 */
public class ProcessRunnable implements Runnable {
    //Name of Process and Respective User
    private String processName, userName;
    static private int nameCounter = 0;

    //Arrival, Duration, Execution Time
    private int readyTime, serviceTime, cpuTime;

    //Process Thread State
    private int processState;

    /**
     * Parametrized Constructor for the Process Class
     * @param service service time of the process given by the input file
     * @param ready ready time of the process given by the input file
     */
    ProcessRunnable(String uName, int ready, int service) {
        userName = uName;
        processName = "P" + nameCounter++;
        processState = -1;

        readyTime = ready;
        serviceTime = service;
        cpuTime = 0;
    }

    /**
     * Set Process State to Pause, Resume or Finish a Process
     * @param state
     */
    public void setProcessState(int state) {processState = state;}

    /**
     * Get Process State and Ready Time
     * @return
     */
    public int getReady() { return readyTime; }
    public int getProcessState() { return processState; }

    /**
     * Thread Run Method
     *
     * States
     * -1 -> Waiting
     *  0 -> Paused
     *  1 -> Resumed
     *  2 -> Running
     *  3 -> Ready
     *  4 -> Finished
     */
    @Override
    public void run() {
        int currTime = Clock.INSTANCE.getTime();
        Clock.INSTANCE.logEvent("Time " + currTime + ", User " + userName + ", Process " + processName + " Started!");

        do {
            synchronized (this) {
                currTime = Clock.INSTANCE.getTime();
                switch (processState) {
                    //Paused -> Ready
                    case 0:
                        Clock.INSTANCE.logEvent("Time " + currTime + ", User " + userName + ", Process " + processName + " Paused.");
                        processState = 3;
                        break;
                    //Resumed -> Running
                    case 1:
                        Clock.INSTANCE.logEvent("Time " + currTime + ", User " + userName + ", Process " + processName + " Resumed.");
                        processState = 2;
                        cpuTime += 10;
                        break;
                    //Running...
                    case 2:
                        cpuTime += 10;
                        break;
                    //Ready...
                    case 3:
                        break;
                    default:
                        main.log.error("Error: State " + processState + " Doesn't Exist!!");
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                main.log.error(e.getMessage());
            }
        } while (cpuTime < 1000 * serviceTime);

        //Terminate Process
        processState = 4;
        Clock.INSTANCE.logEvent("Time " + currTime + ", User " + userName + ", Process " + processName + " Finished!");
    }

    /**
     * Print Process Data
     * @return
     */
    public String getData(){
        return "Process Name: " + processName + " => Ready Time: " + readyTime + ", Service Time: " + serviceTime;
    }
}
