package systems.vostok.enzo.essentials.mapreduce.messages

import groovy.transform.Immutable

@Immutable
final class WordCount {
    String word
    Integer count
}
