import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
* Description of this class.
* @author Tyler Cambron
* @version 1.0
* Assignment 4
* CS322 - Compiler Construction
* Spring 2024
*/

public class WordCounter {
    public static void main(String[] args) {
        HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();
        File wcFolder = new File("wc"); // Gets folder that NovelProcessor wrote to.
        File[] wcFiles = wcFolder.listFiles(); // File list of folder
        for (File wcFile: wcFiles) { 
            // Read each file
            try {
                BufferedReader wcReader = new BufferedReader(new FileReader(wcFile));
                for (String ln = wcReader.readLine(); ln != null; ln = wcReader.readLine()) {
                    // Parse the file for the key and count
                    String[] line = ln.split("\\|");
                    String pattern = line[0];
                    for (int i=1; i < line.length-1; i++) {
                        pattern += "|" + line[i];
                    }
                    // Add to hash map
                    wordCounts.put(pattern, Integer.parseInt(line[line.length-1]));
                }
                wcReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // print
        for (String pattern : wordCounts.keySet()) {
            System.out.println(pattern + " : " + wordCounts.get(pattern));
        }
    } 
}
