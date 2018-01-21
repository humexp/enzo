package systems.vostok.enzo.essentials.mapreduce.actors

import akka.actor.ActorRef
import akka.actor.UntypedActor
import systems.vostok.enzo.essentials.mapreduce.messages.MapData
import systems.vostok.enzo.essentials.mapreduce.messages.WordCount

// TODO: Use undeprecated actor extension
public class MapActor extends UntypedActor {

    String[] STOP_WORDS = [ "a", "am", "an", "and", "are", "as", "at", "be","do", "go", "if", "in", "is", "it", "of", "on", "the", "to" ]
    List<String> STOP_WORDS_LIST = Arrays.asList(STOP_WORDS)

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            String work = (String) message;
// map the words in the sentence and send the result to MasterActor
            getSender().tell(evaluateExpression(work), ActorRef.noSender());
        } else
            unhandled(message);
    }
    private MapData evaluateExpression(String line) {
//logic to map the words in the sentences

        // TODO: Rewrite too groovy style
        List<WordCount> dataList = new ArrayList<WordCount>();
        StringTokenizer parser = new StringTokenizer(line);
        while (parser.hasMoreTokens()) {
            String word = parser.nextToken().toLowerCase();
            if (!STOP_WORDS_LIST.contains(word)) {
                dataList.add(new WordCount(word,Integer.valueOf(1)));
            }
        }
        return new MapData(dataList);
    }
}
