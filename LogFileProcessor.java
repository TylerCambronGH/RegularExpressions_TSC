import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LogFileProcessor {
    public static void main(String[] args) {
        String fileName;
        int arg2;
        if (args.length == 0) {
            System.out.println("You need to enter arguments [fileName] [print=(0|1|2)]");
            return;
        } else {
            fileName = args[0];
            if (args.length == 1) {
                arg2 = 0;
            } else {
                arg2 = Integer.parseInt(args[1]);
            }
        } 

        File authLog = new File(fileName);
        try {
            BufferedReader logReader = new BufferedReader(new FileReader(authLog));
            for (String ln = logReader.readLine(); ln != null; ln = logReader.readLine()) {
                
            }
            logReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
