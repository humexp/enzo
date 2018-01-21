package systems.vostok.enzo.essentials.mapreduce.actors

import akka.actor.UntypedActor
import systems.vostok.enzo.essentials.mapreduce.messages.MapData
import systems.vostok.enzo.essentials.mapreduce.messages.ReduceData
import systems.vostok.enzo.essentials.mapreduce.messages.WordCount

class ReduceActor extends UntypedActor {
    @Override
    void onReceive(Object message) throws Exception {
        if (message instanceof MapData) {
            MapData mapData = (MapData) message
// reduce the incoming data and forward the result to Master actor

            getSender().tell(reduce(mapData.getDataList()))
        } else
            unhandled(message)
    }
    private ReduceData reduce(List<WordCount> dataList) {
        HashMap<String, Integer> reducedMap = new HashMap<String, Integer>()
        for (WordCount wordCount : dataList) {
            if (reducedMap.containsKey(wordCount.getWord())) {
                Integer value = (Integer)reducedMap.get(wordCount.getWord())
                value++
                reducedMap.put(wordCount.getWord(), value)
            } else {
                reducedMap.put(wordCount.getWord(),
                        Integer.valueOf(1))
            }
        }
        return new ReduceData(reducedMap)
    }
}
