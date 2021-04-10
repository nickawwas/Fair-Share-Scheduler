import java.util.ArrayList;
import java.util.List;

/**
 * Scheduler class used to schedule the inputted processes
 */
public class Scheduler implements Runnable{
    private int quantum;

    //Waiting and Ready Queues
    private List<User> waitingUserQ, readyUserQ;

    //Process Threads List
    private List<Thread> threadQueue;

    /**
     * Parameterized Constructor of Scheduler - Given Quantum Size
     * @param q - Quantum
     */
    Scheduler(String q){
        quantum = Integer.parseInt(q);

        readyUserQ = new ArrayList<>();
        waitingUserQ = new ArrayList<>();
        threadQueue = new ArrayList<>();
    }

    /**
     * Parse Input to Create Users and Processes for Scheduling
     * @param input - Input Processes and Users
     */
    public void parseInput(ArrayList<String> input){
        int arrival, duration;

        User user = new User();
        for(int i = 1; i < input.size() - 1; i++)
            //Create New User Given Name and Num Processes
            if(!input.get(i).matches("[0-9]+")) {
                user = new User(input.get(i++), Integer.parseInt(input.get(i)));
                waitingUserQ.add(user);
            //Add New Process to Current User Given Arrival and Duration
            } else {
                arrival = Integer.parseInt(input.get(i++));
                duration = Integer.parseInt(input.get(i));
                user.addProcess(arrival, duration);
            }
    }

    /**
     * Thread Run Method - Schedules Thread using Fair Share Scheduling Algorithm
     * - Checks Whether a Process Can Be Moved to the Ready Queue
     * - Divides the Quantum Based on Number of Ready Processes and Users
     * - Runs the Processes for the Determined Quantum (Fairly)
     * - Removes Users Without Ready Processes from Ready Queue and Empty Users
     */
    @Override
    public void run() {
        main.log.info("Scheduler Started!");

        do {
            //Move Ready Processes and Users
            readyCheck();

            //Divide Quantum per Fair Share Algo
            quantumSeparator();

            //Fair Share Scheduling Algo
            fairShareScheduling();

            //Remove Empty Users
            removeEmptyUser();
        } while(!waitingUserQ.isEmpty() || !readyUserQ.isEmpty());

        //Join All Threads to Terminate Scheduling Properly
        for(Thread thread: threadQueue){
            try {
                thread.join();
            } catch (InterruptedException e) {
                main.log.error(e.getMessage());
            }
        }

        main.log.info("Scheduler Stopped!");
    }

    /**
     * Move Process to Ready Queue Upon Arrival
     */
    public void readyCheck() {
        for (int i = 0; i < waitingUserQ.size(); i++) {
            User user = waitingUserQ.get(i);

            for (int j = 0; j < user.getWaitingProcessQ().size(); j++) {
                ProcessRunnable process = user.getWaitingProcessQ().get(j);

                //Move Process to Ready Process Queue and Out of Waiting Process Queue Upon Arrival
                if (1000 * process.getReady() <= Clock.INSTANCE.getTime() && !user.getReadyProcessQ().contains(process)) {
                    user.addToReadyQueue(process);

                    //Add User to Ready User Queue and Remove From User Waiting Queue
                    if (!readyUserQ.contains(user)) {
                        readyUserQ.add(user);
                        --i;
                    }

                    --j;
                }
            }
        }
    }

    /**
     * Separate Quantum By Number of Active Users and Processes
     */
    public void quantumSeparator() {
        for(User user: readyUserQ)
            user.setUserQuantum(quantum / readyUserQ.size());
    }


    /**
     * Schedule Processes Per Fair Share Algorithm
     * Create process threads and Simulate process execution
     */
    public void fairShareScheduling() {
        int currTime, startTime = Clock.INSTANCE.getTime();

        //Process Scheduling
        for(User user: readyUserQ) {
            for(int i = 0; i < user.getReadyProcessQ().size(); i++) {
                //ProcessRunnable p = user.getReadyProcessQ().get(i);

                //Start Idle Process, Change State to Running
                //Add Thread to Thread Queue
                if(user.getReadyProcessQ().get(i).getProcessState() == -1) {
                    Thread processT = new Thread(user.getReadyProcessQ().get(i));
                    threadQueue.add(processT);
                    user.getReadyProcessQ().get(i).setProcessState(2);
                    processT.start();
                //Resume Existing Thread
                } else {
                    user.getReadyProcessQ().get(i).setProcessState(1);
                }

                //Schedule For Quantum
                currTime = Clock.INSTANCE.getTime();
                while (currTime - startTime < 1000 * user.getUserQuantum()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        main.log.error(e.getMessage());
                    }

                    currTime = Clock.INSTANCE.getTime();
                }

                //Pause Process
                if(user.getReadyProcessQ().get(i).getProcessState() != 4 && user.getReadyProcessQ().get(i).getProcessState() != 0)
                    user.getReadyProcessQ().get(i).setProcessState(0);

                //Missing Sleeper - Give Time for Process to Respond to State Change
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    main.log.error(e.getMessage());
                }

                startTime = Clock.INSTANCE.getTime();
            }
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            main.log.error(e.getMessage());
        }
    }

    /**
     * Remove User from Ready Queue When No Longer Has a Ready Process
     * Add Back User to Waiting Queue When Waiting Processes
     */
    public void removeEmptyUser() {
        //Remove Empty User From Start
        for(int i = 0; i < readyUserQ.size(); i++) {
            User user = readyUserQ.get(i);
            for(int j = 0; j < user.getReadyProcessQ().size(); j++) {
                ProcessRunnable process = user.getReadyProcessQ().get(j);

                //Remove Terminated Processes from User
                if(process.getProcessState() == 4) {
                    user.getReadyProcessQ().remove(process);
                    --j;
                }
            }

            //Remove Empty User from Ready Queue
            if(user.getReadyProcessQ().isEmpty())
                readyUserQ.remove(user);
        }

        //Remove Empty Waiting User from Waiting Queue
        for(int i = 0; i < waitingUserQ.size(); i++) {
            User user = waitingUserQ.get(i);

            //Remove Empty User from Ready Queue
            if(user.getWaitingProcessQ().isEmpty())
                waitingUserQ.remove(user);

        }
    }

    /**
     * Print User and Process Data to Schedule
     */
    public void printData() {
        for(User user : waitingUserQ) {
            //Print User
            main.log.info("User " + user.getName() + " Has " + user.getProcessQty() + " Processes:");

            //Print Processes
            for (ProcessRunnable process : user.getWaitingProcessQ())
                main.log.info(process.getData());

            main.log.info(" ");
        }
    }
}