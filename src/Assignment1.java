import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Assignment1 {
    public static void main(String[] args) {
        String inputFile = args[0];
        String outputFilePrefix = args[1];
        File file = new File(inputFile);
        Dictionary<String, Integer> words = new Dictionary<>();
        Dictionary<String, Integer> sentences = new Dictionary<>();


        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNext()){ //While input file has words left to read
                //Store word in variable word
                String word = sc.next().replaceAll("[.]", "").toLowerCase();
                //If our words dictionary already contains our word
                if(words.contains(word)){
                    //Increment count of word by one
                    words.put(word, words.get(word) + 1);
                } else {
                    //Else, add word to our dictionary with initial value of 1
                    words.put(word, 1);
                }

            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        //Prints each entry to user.
        for (Entry entry:
                words.getArray()) {
            if (entry == null) {
                break;
            }
            System.out.println(entry.getEntry());
        }
    }
}