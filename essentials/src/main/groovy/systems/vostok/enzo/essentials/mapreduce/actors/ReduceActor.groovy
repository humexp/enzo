package systems.vostok.enzo.essentials.mapreduce.actors

import akka.actor.AbstractActor
import akka.actor.ActorRef
import systems.vostok.enzo.essentials.mapreduce.messages.MapData
import systems.vostok.enzo.essentials.mapreduce.messages.ReduceData
import systems.vostok.enzo.essentials.mapreduce.messages.WordCount

class ReduceActor extends AbstractActor {
    @Override
    AbstractActor.Receive createReceive() {
        receiveBuilder()
                .match(MapData.class, { getSender().tell(reduce(it.dataList), ActorRef.noSender()) })
                .matchAny({ unhandled(it) })
                .build()
    }

    private ReduceData reduce(List<WordCount> dataList) {
        dataList.groupBy { it.word }
                .collectEntries { [(it.key as String): it.value.size()] }
                .with { new ReduceData(it) }
    }
}
