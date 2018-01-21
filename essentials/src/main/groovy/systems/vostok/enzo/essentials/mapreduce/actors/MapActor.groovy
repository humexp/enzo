package systems.vostok.enzo.essentials.mapreduce.actors

import akka.actor.AbstractActor
import akka.actor.ActorRef
import systems.vostok.enzo.essentials.mapreduce.messages.MapData
import systems.vostok.enzo.essentials.mapreduce.messages.WordCount

class MapActor extends AbstractActor {
    final List<String> STOP_WORDS_LIST = ['a', 'am', 'an', 'and', 'are', 'as',
                                          'at', 'be', 'do', 'go', 'if', 'in',
                                          'is', 'it', 'of', 'on', 'the', 'to']

    @Override
    AbstractActor.Receive createReceive() {
        receiveBuilder()
                .match(String.class, { getSender().tell(evaluateExpression(it), ActorRef.noSender()) })
                .matchAny({ unhandled(it) })
                .build()
    }

    private MapData evaluateExpression(String line) {
        line.split(' ')
                .collect { it.toLowerCase() }
                .findAll { !(it in STOP_WORDS_LIST) }
                .collect { new WordCount(it, 1) }
                .with { new MapData(it) }
    }
}
