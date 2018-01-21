package systems.vostok.enzo.essentials.mapreduce.actors

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props
import akka.routing.RoundRobinPool
import systems.vostok.enzo.essentials.mapreduce.messages.MapData
import systems.vostok.enzo.essentials.mapreduce.messages.ReduceData
import systems.vostok.enzo.essentials.mapreduce.messages.Result

class MasterActor extends AbstractActor {

    ActorRef mapActor = getContext().actorOf(Props.create(MapActor.class).withRouter(new RoundRobinPool(5)), 'map')
    ActorRef reduceActor = getContext().actorOf(Props.create(ReduceActor.class).withRouter(new RoundRobinPool(5)), 'reduce')
    ActorRef aggregateActor = getContext().actorOf(Props.create(AggregateActor.class), 'aggregate')

    @Override
    Receive createReceive() {
        return receiveBuilder()
                .match(String.class, { mapActor.tell(it, getSelf()) })
                .match(MapData.class, { reduceActor.tell(it, getSelf()) })
                .match(ReduceData.class, { aggregateActor.tell(it, ActorRef.noSender()) })
                .match(Result.class, { aggregateActor.forward(it, getContext()) })
                .matchAny({ unhandled(it) })
                .build()
    }
}
