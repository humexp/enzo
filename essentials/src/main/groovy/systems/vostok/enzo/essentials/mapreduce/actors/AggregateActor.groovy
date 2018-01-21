package systems.vostok.enzo.essentials.mapreduce.actors

import akka.actor.AbstractActor
import akka.actor.ActorRef
import systems.vostok.enzo.essentials.mapreduce.messages.ReduceData
import systems.vostok.enzo.essentials.mapreduce.messages.Result

class AggregateActor extends AbstractActor {
    private Map<String, Integer> finalReducedMap = new HashMap<>()

    @Override
    AbstractActor.Receive createReceive() {
        receiveBuilder()
                .match(ReduceData.class, { aggregateInMemoryReduce(it.reduceDataList) })
                .match(Result.class, { getSender().tell(finalReducedMap.toString(), ActorRef.noSender()) })
                .matchAny({ unhandled(it) })
                .build()
    }

    private void aggregateInMemoryReduce(Map<String, Integer> reducedList) {
        reducedList.each { key, value ->
            if (finalReducedMap.containsKey(key)) {
                finalReducedMap[key] = reducedList[key] + finalReducedMap[key]
            } else {
                finalReducedMap[key] = reducedList[key]
            }
        }
    }
}
