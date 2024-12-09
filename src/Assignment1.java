import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
            while (sc.hasNext()) {
                String next = sc.next();
                text.append(next.toLowerCase() + " ");

                //While input file has words left to read
                //Store word in variable word
                String word = next.replaceAll("[.]", "").toLowerCase();
                //If our words dictionary already contains our word
                if (words.contains(word)) {
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


        sentenceArray = text.toString().split("[.]");
        for (int i = 0; i < sentenceArray.length; i++) {
            Dictionary<String, Integer> sentenceWordCounts = new Dictionary<>();
            Scanner wordSc = new Scanner(sentenceArray[i]);
            while (wordSc.hasNext()) {
                String next = wordSc.next();
                //If our words dictionary already contains our word
                if (sentenceWordCounts.contains(next)) {
                    //Increment count of word by one
                    sentenceWordCounts.put(next, sentenceWordCounts.get(next) + 1);
                } else {
                    //Else, add word to our dictionary with initial value of 1
                    sentenceWordCounts.put(next, 1);
                }
            }

            sentences.put(sentenceArray[i], sentenceWordCounts);
        }

        // For questionOne
        String questionOneOutput = questionOne(words);
        writeToFile(outputFilePrefix + "1.txt", questionOneOutput);

        // For questionTwo
        String questionTwoOutput = questionTwo(words);
        writeToFile(outputFilePrefix + "2.txt", questionTwoOutput);

        // For questionThree
        String questionThreeOutput = questionThree(sentences);
        writeToFile(outputFilePrefix + "3.txt", questionThreeOutput);

        // For getWordFreq and getPhraseFreq
        String wordFreqThe = getWordFreq(sentences, "the");
        writeToFile(outputFilePrefix + "4.txt", wordFreqThe);

        String wordFreqOf = getWordFreq(sentences, "of");
        writeToFile(outputFilePrefix + "5.txt", wordFreqOf);

        String wordFreqWas = getWordFreq(sentences, "was");
        writeToFile(outputFilePrefix + "6.txt", wordFreqWas);

        String phraseFreqButThe = getPhraseFreq(sentences, "but the");
        writeToFile(outputFilePrefix + "7.txt", phraseFreqButThe);

        String phraseFreqItWas = getPhraseFreq(sentences, "it was");
        writeToFile(outputFilePrefix + "8.txt", phraseFreqItWas);

        String phraseFreqInMy = getPhraseFreq(sentences, "in my");
        writeToFile(outputFilePrefix + "9.txt", phraseFreqInMy);

    }


    public static ArrayList<Entry<String, Integer>> getEntriesByFrequency(Dictionary<String, Integer> dict, int index) {
        Dictionary<String, Integer> wordsDict = new Dictionary<>(dict.getArray().clone());

        Dictionary<Integer, ArrayList<Entry<String, Integer>>> freqDict = new Dictionary<>();

        wordsDict.sort();

        for (Entry<String, Integer> entry :
                wordsDict.getArray()) {
            if (entry == null) {
                break;
            }
            if (freqDict.contains(entry.getValue())) {
                ArrayList<Entry<String, Integer>> entryList = freqDict.get(entry.getValue());
                entryList.add(entry);
                freqDict.put(entry.getValue(), entryList);
            } else {
                ArrayList<Entry<String, Integer>> entryList = new ArrayList<>();
                entryList.add(entry);
                freqDict.put(entry.getValue(), entryList);
            }
        }


        if (index > freqDict.size() - 1 || freqDict.getArray()[index] == null) {
            throw new IndexOutOfBoundsException();
        }

        return freqDict.getArray()[index].getValue();
    }


    public static String printListOfEntries(ArrayList<Entry<String, Integer>> listOfEntries) {
        StringBuilder result = new StringBuilder();
        for (Entry<String, Integer> entry : listOfEntries) {
            result.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }

        return result.toString();
    }

    public static String questionOne(Dictionary<String, Integer> dict) {
        return printListOfEntries(getEntriesByFrequency(dict, 0));
    }

    public static String questionTwo(Dictionary<String, Integer> dict) {
        return printListOfEntries(getEntriesByFrequency(dict, 2));
    }

    public static String questionThree(Dictionary<String, Dictionary<String, Integer>> dict) {
        Dictionary<String, Dictionary<String, Integer>> sentenceDict = new Dictionary<>(dict.getArray().clone());

        ArrayList<Entry<String, ArrayList<Entry<String, Integer>>>> listOfEntries = new ArrayList<>();

        for (int i = 0; i < sentenceDict.size() - 1; i++) {
            Entry<String, Dictionary<String, Integer>> sentence = sentenceDict.getArray()[i];
            if (sentence == null) {
                continue;
            }
            listOfEntries.add(new Entry<>(sentence.getKey(), getEntriesByFrequency(sentence.getValue(), 0)));
        }

        listOfEntries.sort((a, b) -> b.getValue().get(0).getValue().compareTo(a.getValue().get(0).getValue()));

        StringBuilder max = new StringBuilder();
        int maxFreq = listOfEntries.get(0).getValue().get(0).getValue();

        for (Entry<String, ArrayList<Entry<String, Integer>>> entry :
                listOfEntries) {
            for (Entry<String, Integer> wordEntry : entry.getValue()) {
                if (wordEntry.getValue() == maxFreq) {
                    max.append(wordEntry.getKey() + ":" + wordEntry.getValue() + ":" + entry.getKey().trim() + "\n");
                }
            }
        }
        return max.toString();
    }

    public static String getWordFreq(Dictionary<String, Dictionary<String, Integer>> dict, String word) {
        Dictionary<String, Dictionary<String, Integer>> sentenceDict = new Dictionary<>(dict.getArray().clone());

        Dictionary<Integer, ArrayList<String>> freqDict = new Dictionary<>();

        for (Entry<String, Dictionary<String, Integer>> entry : sentenceDict.getArray()) {
            if (entry == null) {
                break;
            }

            if (entry.getValue().contains(word)) {
                int frequency = entry.getValue().get(word);
                if (freqDict.contains(frequency)) {
                    ArrayList<String> temporaryList = freqDict.get(frequency);
                    temporaryList.add(entry.getKey());
                    freqDict.put(frequency, temporaryList);
                } else {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(entry.getKey());
                    freqDict.put(frequency, list);
                }
            }

        }

        int maxFrequency = Integer.MIN_VALUE;
        StringBuilder resultSentence = new StringBuilder();

        for (Entry<Integer, ArrayList<String>> freqEntry : freqDict.getArray()) {
            if (freqEntry != null && freqEntry.getKey() > maxFrequency) {
                maxFrequency = freqEntry.getKey();
            }
        }

        // Build the result string with the desired format
        for (String sentence : freqDict.get(maxFrequency)) {
            resultSentence.append(word).append(":").append(maxFrequency).append(":").append(sentence.trim()).append("\n");
        }

        return resultSentence.toString().trim(); // Remove trailing newline

    }
    public static String getPhraseFreq(Dictionary<String, Dictionary<String, Integer>> dict, String word) {
        Dictionary<String, Dictionary<String, Integer>> sentenceDict = new Dictionary<>(dict.getArray().clone());

        Dictionary<Integer, ArrayList<String>> freqDict = new Dictionary<>();

        for (Entry<String, Dictionary<String, Integer>> entry : sentenceDict.getArray()) {
            if (entry == null) {
                continue;
            }

            int frequency = countOccurrences(entry.getKey(), word);

            if (entry.getKey().contains(word)) {

                if (freqDict.contains(frequency)) {
                    ArrayList<String> temporaryList = freqDict.get(frequency);
                    temporaryList.add(entry.getKey());
                    freqDict.put(frequency, temporaryList);
                } else {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(entry.getKey());
                    freqDict.put(frequency, list);
                }
            }

        }

        int maxFrequency = Integer.MIN_VALUE;
        StringBuilder resultSentence = new StringBuilder();

        for (Entry<Integer, ArrayList<String>> freqEntry : freqDict.getArray()) {
            if (freqEntry != null && freqEntry.getKey() > maxFrequency) {
                maxFrequency = freqEntry.getKey();
            }
        }

        // Build the result string with the desired format
        if (freqDict.get(maxFrequency) != null ) {
            for (String sentence : freqDict.get(maxFrequency)) {
                resultSentence.append(word).append(":").append(maxFrequency).append(":").append(sentence.trim()).append("\n");
            }
        } else {
            resultSentence.append("No phrases found in given text");
        }

        return resultSentence.toString().trim(); // Remove trailing newline

    }

    private static int countOccurrences(String sentence, String phrase) {
        int count = 0;
        int index = sentence.indexOf(phrase);
        while (index != -1) {
            count++;
            index = sentence.indexOf(phrase, index + phrase.length());
        }
        return count;
    }

    public static void writeToFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


}