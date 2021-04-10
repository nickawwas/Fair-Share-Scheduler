import java.util.ArrayList;
import org.apache.log4j.Logger;

public class main {
    public static final Logger log = Logger.getLogger("FS");

    /**
     * Driver / Main Program
     * Calls the FileReader Method to Obtain Input
     * Parses User and Process Data
     * Creates Clock and Scheduler Threads
     * @param args
     */
    public static void main(String[] args) {
        //Read File Input
        String fileName = "Input";
        FileReader fileReader = new FileReader();
        ArrayList<String> input = fileReader.readFile(fileName);

        //Create Scheduler Object
        Scheduler scheduler = new Scheduler(input.get(0));
        scheduler.parseInput(input);
        scheduler.printData();

        log.info("Fair Share Scheduling Started!");

        //Creates the Scheduler and Clock Threads
        Thread schedulerT = new Thread(scheduler);
        schedulerT.start();

        Thread clockT = new Thread(Clock.INSTANCE);
        clockT.start();

        //Join Threads to Ensuring the Scheduler Thread Terminates Properly
        try {
            schedulerT.join();
            Clock.INSTANCE.setStatus(true);
            clockT.join();
        } catch (InterruptedException e) {
            main.log.error(e.getMessage());
        }

        log.info("Fair Share Scheduling Complete!");
    }
}