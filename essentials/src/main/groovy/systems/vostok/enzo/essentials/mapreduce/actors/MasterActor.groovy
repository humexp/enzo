package systems.vostok.enzo.essentials.mapreduce.actors

import akka.actor.AbstractActor
import akka.actor.ActorRef
import akka.actor.Props
import akka.routing.RoundRobinRouter
import systems.vostok.enzo.essentials.mapreduce.messages.MapData
import systems.vostok.enzo.essentials.mapreduce.messages.ReduceData
import systems.vostok.enzo.essentials.mapreduce.messages.Result

class MasterActor extends AbstractActor {





    //TODO: Implement routers
    ActorRef mapActor = getContext().actorOf(
            new Props(MapActor.class).withRouter(new
                    RoundRobinRouter(5)), "map");
    ActorRef reduceActor = getContext().actorOf(
            new Props(ReduceActor.class).withRouter(new
                    RoundRobinRouter(5)), "reduce");
    ActorRef aggregateActor = getContext().actorOf(
            new Props(AggregateActor.class), "aggregate");


    //TODO: Research forward
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
