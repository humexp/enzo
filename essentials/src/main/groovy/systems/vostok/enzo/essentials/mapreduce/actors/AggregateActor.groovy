package systems.vostok.enzo.essentials.mapreduce.actors

import akka.actor.UntypedActor
import systems.vostok.enzo.essentials.mapreduce.messages.ReduceData
import systems.vostok.enzo.essentials.mapreduce.messages.Result

class AggregateActor extends UntypedActor {
    private Map<String, Integer> finalReducedMap =
            new HashMap<String, Integer>()

    @Override
    void onReceive(Object message) throws Exception {
        if (message instanceof ReduceData) {
            ReduceData reduceData = (ReduceData) message

            aggregateInMemoryReduce(reduceData.
                    getReduceDataList())
        } else if (message instanceof Result) {
            getSender().tell(finalReducedMap.toString())
        } else
            unhandled(message)
    }
    private void aggregateInMemoryReduce(Map<String,
            Integer> reducedList) {
        Integer count = null
        for (String key : reducedList.keySet()) {
            if (finalReducedMap.containsKey(key)) {
                count = reducedList.get(key) +
                        finalReducedMap.get(key)
                finalReducedMap.put(key, count)
            } else {
                finalReducedMap.put(key, reducedList.get(key))
            }
        }
    }
}
