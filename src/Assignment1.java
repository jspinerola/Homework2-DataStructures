import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Assignment1 {
    public static void main(String[] args) {
        String inputFile = args[0];
        String outputFilePrefix = args[1];
        File file = new File(inputFile);
        Dictionary<String, Integer> words = new Dictionary<>();
        Dictionary<String, Dictionary<String, Integer>> sentences = new Dictionary<>();
        StringBuilder text = new StringBuilder("");
        String[] sentenceArray;

        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNext()){
                String next = sc.next();
                text.append(next.toLowerCase() + " ");

                //While input file has words left to read
                //Store word in variable word
                String word = next.replaceAll("[.]", "").toLowerCase();
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

        System.out.println(text);
        sentenceArray = text.toString().split("[.]");
        for (int i = 0; i < sentenceArray.length; i++) {
            Dictionary<String, Integer> sentenceWordCounts = new Dictionary<>();
            Scanner wordSc = new Scanner(sentenceArray[i]);
            while (wordSc.hasNext()){
                String next = wordSc.next();
                //If our words dictionary already contains our word
                if(sentenceWordCounts.contains(next)){
                    //Increment count of word by one
                    sentenceWordCounts.put(next, words.get(next) + 1);
                } else {
                    //Else, add word to our dictionary with initial value of 1
                    sentenceWordCounts.put(next, 1);
                }
            }

            sentences.put(sentenceArray[i], sentenceWordCounts);
        }

        for (Entry entry:
                sentences.getArray()) {
            if (entry == null) {
                break;
            }
            System.out.print(entry.getEntry() + ", ");
        }
        System.out.println("\n");

        for (Entry entry:
                words.getArray()) {
            if (entry == null) {
                break;
            }
            System.out.print(entry.getEntry() + ", ");

        }
        System.out.println("\n");

        System.out.println(questionOne(words));

        System.out.println(questionTwo(words));

//        for (Entry entry:
//                words.getArray()) {
//            if (entry == null) {
//                break;
//            }
//            System.out.print(entry.getEntry() + ", ");
//
//        }
//        System.out.println("\n");

    }

//    public static String questionOne(Dictionary<String, Integer> dict){
//        Dictionary<String, Integer> wordsDict = new Dictionary<>(dict.getArray().clone());
//        ArrayList<Entry> mostFrequentWords = new ArrayList<>();
//        wordsDict.sort();
//        Entry<String, Integer> mostFrequentWord = wordsDict.getArray()[0];
//        mostFrequentWords.add(mostFrequentWord);
//        int maxFreq = mostFrequentWord.getValue();
//        int i = 1;
//        while(wordsDict.getArray()[i].getValue() == maxFreq && i < wordsDict.size() - 1){
//            mostFrequentWords.add(wordsDict.getArray()[i]);
//            i++;
//        }
//
//        StringBuilder result = new StringBuilder();
//        for (Entry<String, Integer> entry : mostFrequentWords) {
//            result.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
//        }
//
//        return result.toString();
//        //Find way to get most frequent word.
//        /*
//        * One way to do so is by getting first index of the sorted words dictionary, and iterating through rest of the
//        * words dictionary and checking if that word has the same value as the first word. If it does, add that word to
//        * a new ArrayList "Most Frequent Words."
//        * */
//    }
    public static ArrayList<Entry<String, Integer>> getListOfEntries(Dictionary<String, Integer> dict, int index){
        Dictionary<String, Integer> wordsDict = new Dictionary<>(dict.getArray().clone());

        Dictionary<Integer, ArrayList<Entry<String, Integer>>> freqDict = new Dictionary<>();

        wordsDict.sort();

        for (Entry<String, Integer> entry:
                wordsDict.getArray()) {
            if (entry == null) {
                break;
            }
            if (freqDict.contains(entry.getValue())){
                ArrayList<Entry<String, Integer>> entryList = freqDict.get(entry.getValue());
                entryList.add(entry);
                freqDict.put(entry.getValue(), entryList);
            } else {
                ArrayList<Entry<String, Integer>> entryList = new ArrayList<>();
                entryList.add(entry);
                freqDict.put(entry.getValue(), entryList);
            }
        }

        if (index > freqDict.size() - 1){
            throw new IndexOutOfBoundsException();
        }

        return freqDict.getArray()[index].getValue();
    }

    public static String printListOfEntries(ArrayList<Entry<String, Integer>> listOfEntries){
        StringBuilder result = new StringBuilder();
        for (Entry<String, Integer> entry : listOfEntries) {
            result.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }

        return result.toString();
    }

    public static String questionOne(Dictionary<String, Integer> dict){
        return printListOfEntries(getListOfEntries(dict, 0));
    }
    public static String questionTwo(Dictionary<String, Integer> dict){
        return printListOfEntries(getListOfEntries(dict, 2));
    }
}