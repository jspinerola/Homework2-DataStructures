import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Assignment1 {
    public static void main(String[] args) {
        String inputFile = args[0];
        String outputFilePrefix = args[1];
        File file = new File(inputFile);
        try {
            Scanner sc = new Scanner(file);
            // Add your code to use the Scanner here
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }



    }
}