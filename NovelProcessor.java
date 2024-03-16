import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NovelProcessor {
    static int scanBook(File bookFile, Pattern pattern) {
        try {
            int pCount = 0;
            BufferedReader bookReader = new BufferedReader(new FileReader(bookFile));
            for (String line = bookReader.readLine(); line != null; line = bookReader.readLine()) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                   pCount += 1;
                }
            }
            closeReader(bookReader);
            return pCount;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    static void closeReader(BufferedReader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 

    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        // Book File
        System.out.print("Book Name (.txt): ");
        String bookInput = inputScanner.nextLine();
        File bookFile;
        BufferedReader bookReader;
        try {
            bookFile = new File(bookInput + ".txt");
            bookReader = new BufferedReader(new FileReader(bookFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            inputScanner.close();
            return;
        }
        closeReader(bookReader);

        // Pattern File
        System.out.print("\nPattern File Name (.txt): ");
        String patternInput = inputScanner.nextLine();
        File patternFile;
        BufferedReader patternReader;
        try {
            patternFile = new File(patternInput+".txt");
            patternReader = new BufferedReader(new FileReader(patternFile));;
        } catch (FileNotFoundException e) {
            inputScanner.close();
            return;
        }

        try {
            FileWriter output = new FileWriter("wc/"+bookInput+"_wc.txt");
            for (String patternLine = patternReader.readLine(); patternLine != null; patternLine = patternReader.readLine()) {
                System.out.println(patternLine);
                Pattern pattern = Pattern.compile(patternLine);
                int pCount = scanBook(bookFile, pattern);
                System.out.println(pCount);
                output.write(patternLine+"|"+pCount+"\n");
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
            inputScanner.close();
            closeReader(patternReader);
            return;
        }

        System.out.print("\n");
        inputScanner.close();
        closeReader(patternReader);
    }
}