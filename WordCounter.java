import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class WordCounter {
    public static void main(String[] args) {
        HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();
        File wcFolder = new File("wc");
        File[] wcFiles = wcFolder.listFiles();
        for (File wcFile: wcFiles) {
            try {
                BufferedReader wcReader = new BufferedReader(new FileReader(wcFile));
                for (String ln = wcReader.readLine(); ln != null; ln = wcReader.readLine()) {
                    String[] line = ln.split("\\|");
                    String pattern = line[0];
                    for (int i=1; i < line.length-1; i++) {
                        pattern += "|" + line[i];
                    }
                    wordCounts.put(pattern, Integer.parseInt(line[line.length-1]));
                }
                wcReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (String pattern : wordCounts.keySet()) {
            System.out.println(pattern + " : " + wordCounts.get(pattern));
        }
    } 
}
