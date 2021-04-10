import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;

public class FileReader {
    /**
     * Opens and Reads File
     * @param fileName - File to Be Opened
     * @return
     */
    public ArrayList<String> readFile(String fileName) {
        ArrayList<String> tokens = new ArrayList<>();

        try {
            Scanner sf = new Scanner(new File(fileName + ".txt"));
            while(sf.hasNext())
                tokens.add(sf.next());
        } catch (IOException e){
            main.log.error(e);
        }

        return tokens;
    }
}