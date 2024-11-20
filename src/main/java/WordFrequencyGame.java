import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    public static final String SPACE = "\\s+";
    public static final String LINE_BREAK = "\n";
    public static final String SEPARATOR = " ";

    public String getWordFrequency(String sentence) {
        if (sentence.split(SPACE).length == 1) {
            return sentence + " 1";
        } else {
            try {
                //split the input string with 1 to n pieces of spaces
                String[] words = sentence.split(SPACE);
                List<WordFrequency> wordFrequencies = Arrays.stream(words)
                        .map(word -> new WordFrequency(word, 1))
                        .toList();
                //get the map for the next step of sizing the same word
                Map<String, List<WordFrequency>> wordToWordFrequenciesMap = getWordToWordFrequenciesMap(wordFrequencies);
                List<WordFrequency> aggregatedWordFrequencies = wordToWordFrequenciesMap.entrySet().stream()
                        .map(entry -> new WordFrequency(entry.getKey(), entry.getValue().size()))
                        .collect(Collectors.toList());
                wordFrequencies = aggregatedWordFrequencies;
                wordFrequencies.sort((word, nextWord) -> nextWord.getWordCount() - word.getWordCount());
                StringJoiner joiner = new StringJoiner(LINE_BREAK);
                for (WordFrequency wordFrequency : wordFrequencies) {
                    String wordFrequencyAsString = wordFrequency.getValue() + SEPARATOR + wordFrequency.getWordCount();
                    joiner.add(wordFrequencyAsString);
                }
                return joiner.toString();
            } catch (Exception e) {
                return "Calculate Error";
            }
        }
    }

    private Map<String, List<WordFrequency>> getWordToWordFrequenciesMap(List<WordFrequency> wordFrequencies) {
        Map<String, List<WordFrequency>> wordToWordFrequenciesMap = new HashMap<>();
        for (WordFrequency wordFrequency : wordFrequencies) {
//       map.computeIfAbsent(input.getValue(), k -> new ArrayList<>()).add(input);
            if (!wordToWordFrequenciesMap.containsKey(wordFrequency.getValue())) {
                ArrayList currentWordFrequencies = new ArrayList<>();
                currentWordFrequencies.add(wordFrequency);
                wordToWordFrequenciesMap.put(wordFrequency.getValue(), currentWordFrequencies);
            } else {
                wordToWordFrequenciesMap.get(wordFrequency.getValue()).add(wordFrequency);
            }
        }
        return wordToWordFrequenciesMap;
    }
}
