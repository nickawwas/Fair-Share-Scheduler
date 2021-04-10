import java.util.List;
import java.util.ArrayList;

/**
 * User Class - Stores Instance of User and All Respective Processes
 */
public class User {
    private String userName;
    private int processQty, userQuantum;

    //Waiting and Ready Process Lists
    private List<ProcessRunnable> waitingQ, readyQ;

    /**
     * Base Constructor for Empty User
     */
    User(){ userName = " "; processQty = 0;}

    /**
     * Parametrized Constructor for User
     * @param name name of the user from the input file
     * @param qty amount of precesses of the user from the input file
     */
    User(String name, int qty) {
        userName = name;
        processQty = qty;
        waitingQ = new ArrayList<>();
        readyQ = new ArrayList<>();
    }

    /**
     * Get User Name, Quantum, Number of Processes
     * @return
     */
    public String getName() {return userName;}
    public int getUserQuantum() {return userQuantum;}
    public int getProcessQty() {return processQty;}

    /**
     * Get Ready and Waiting Queues
     * @return
     */
    public List<ProcessRunnable> getWaitingProcessQ() {return waitingQ;}
    public List<ProcessRunnable> getReadyProcessQ() {return readyQ;}

    /**
     * Add new Process to User
     * @param ready - Arrival Time
     * @param service - Duration
     */
    public void addProcess(int ready, int service){
        if (waitingQ.size() >= processQty)
            main.log.error("Number of Processes Exceeds Specified Quantity!");
        else
            waitingQ.add(new ProcessRunnable(userName, ready, service));
    }

    /**
     * Move Process to Ready Queue
     * @param process - Process to Be Added
     */
    public void addToReadyQueue(ProcessRunnable process) {
        readyQ.add(0, process);
        waitingQ.remove(process);
    }

    /**
     * Assign User Quantum Based on Number of Ready Processes
     * @param quantum - Define userQuantum
     */
    public void setUserQuantum(int quantum) {
        userQuantum = (quantum / readyQ.size());
    }

    /**
     * Print Process Data of User
     */
    public void getProcessData() {
        for(int i = 0; i < waitingQ.size(); i++)
            main.log.info(waitingQ.get(i).getData());
    }
}