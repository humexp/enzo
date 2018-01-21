package systems.vostok.enzo.essentials.mapreduce.messages

final class WordCount {
    private final String word
    private final Integer count

    WordCount(String inWord, Integer inCount) {
        word = inWord
        count = inCount
    }

    String getWord() {
        word
    }

    Integer getCount() {
        count
    }
}
