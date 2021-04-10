/**
 * Clock Using Enum to Implement As Singleton
 * Counts Passing of Time in Units of 10ms
 * Runs as a Separate Thread
 */
public enum Clock implements Runnable{
    INSTANCE();

    private int time;
    private boolean status;

    /**
     * Default Constructor - Initializes the Clock to Paused and Starting at Time 1s
     */
    Clock() {
        time = 1000;
        status = false;
    }

    /**
     * Get Current Clock Time
     */
    public int getTime() {
        return time;
    }

    /**
     * Set Status of Clock to Running or Terminated
     */
    public void setStatus(boolean state) {
        status = state;
    }

    /**
     * Log Events
     * @param m - Message Passed to Logger
     */
    public void logEvent(String m) {
        main.log.info(m);
    }

    /**
     * Run Thread Until Status is Finished
     * Clock Count Increases by Intervals of 10ms
     */
    @Override
    public void run() {
        main.log.info("Clock Started!");

        while(!status) {
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
                main.log.error(e.getMessage());
            }

            time += 10;
        }

        main.log.info("Clock Stopped!");
    }
}