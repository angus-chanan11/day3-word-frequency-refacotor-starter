import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    public static final String SPACE = "\\s+";
    public static final String LINE_BREAK = "\n";
    public static final String SEPARATOR = " ";
    public static final String CALCULATE_ERROR = "Calculate Error";

    public String getWordFrequency(String sentence) {
        try {
            List<WordFrequency> wordFrequencies = getInitialWordFrequencies(sentence);
            Map<String, List<WordFrequency>> wordToWordFrequenciesMap = getWordToWordFrequenciesMap(wordFrequencies);
            wordFrequencies = getWordFrequencies(wordToWordFrequenciesMap);
            return joinResult(wordFrequencies);
        } catch (Exception e) {
            return CALCULATE_ERROR;
        }
    }

    private static String joinResult(List<WordFrequency> wordFrequencies) {
        return wordFrequencies.stream()
                .map(wordFrequency -> wordFrequency.getValue() + SEPARATOR + wordFrequency.getWordCount())
                .collect(Collectors.joining(LINE_BREAK));
    }

    private static List<WordFrequency> getWordFrequencies(Map<String, List<WordFrequency>> wordToWordFrequenciesMap) {
        return wordToWordFrequenciesMap.entrySet().stream()
                .map(entry -> new WordFrequency(entry.getKey(), entry.getValue().size()))
                .sorted((word, nextWord) -> nextWord.getWordCount() - word.getWordCount())
                .collect(Collectors.toList());
    }

    private static List<WordFrequency> getInitialWordFrequencies(String sentence) {
        String[] words = sentence.split(SPACE);
        return Arrays.stream(words)
                .map(word -> new WordFrequency(word, 1))
                .toList();
    }

    private Map<String, List<WordFrequency>> getWordToWordFrequenciesMap(List<WordFrequency> wordFrequencies) {
        return wordFrequencies.stream()
                .collect(Collectors.groupingBy(WordFrequency::getValue));
    }
}
