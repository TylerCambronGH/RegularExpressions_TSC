import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogFileProcessor {

    static Pattern[] getPatterns() {
        File patternFile = new File("logPattern.txt");
        Pattern ip_pattern;
        Pattern user_pattern;
        try {
            BufferedReader patternReader = new BufferedReader(new FileReader(patternFile));
            
            String line_1 = patternReader.readLine();
            ip_pattern = Pattern.compile(line_1);
            
            String line_2 = patternReader.readLine();
            user_pattern = Pattern.compile(line_2);
            
            patternReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        Pattern[] patterns = new Pattern[2];
        patterns[0] = ip_pattern;
        patterns[1] = user_pattern;
        return patterns;
    }

    static void printHashMap(HashMap<String, Integer> hashMap) {
        for (String x : hashMap.keySet()) {
            System.out.println(x + ": " + hashMap.get(x));
        }
    }

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

        int linesParsed = 0;
        HashMap<String, Integer> uniqueIPs = new HashMap<String, Integer>();
        HashMap<String, Integer> uniqueUsers = new HashMap<String, Integer>();
        File authLog = new File(fileName);
        try {
            Pattern[] patterns = getPatterns();
            BufferedReader logReader = new BufferedReader(new FileReader(authLog));
            for (String ln = logReader.readLine(); ln != null; ln = logReader.readLine()) {
                Matcher ipMatcher = patterns[0].matcher(ln);
                Matcher userMatcher = patterns[1].matcher(ln);
                while (ipMatcher.find()) {
                    String match = ipMatcher.group();
                    if (uniqueIPs.containsKey(match)) {
                        int updateInt = uniqueIPs.get(match) + 1;
                        uniqueIPs.put(match, updateInt);
                    } else {
                        uniqueIPs.put(match, 1);
                    }
                }
                while (userMatcher.find()) {
                    String match = userMatcher.group();
                    if (uniqueUsers.containsKey(match)) {
                        int updateInt = uniqueUsers.get(match) + 1;
                        uniqueUsers.put(match, updateInt);
                    } else {
                        uniqueUsers.put(match, 1);
                    }
                }
                linesParsed++;
            }
            logReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (arg2 == 1) {
            printHashMap(uniqueIPs);
        } else if (arg2 == 2) {
            printHashMap(uniqueUsers);
        }
        System.out.println();
        System.out.println(linesParsed + " lines in the log file were parsed.");
        System.out.println("There are " + uniqueIPs.size() + " unique IP addresses in the log.");
        System.out.println("There are " + uniqueUsers.size() + " unique users in the log.");
    }
}
