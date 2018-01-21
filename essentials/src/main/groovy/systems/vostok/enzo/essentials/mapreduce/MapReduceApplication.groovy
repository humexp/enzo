package systems.vostok.enzo.essentials.mapreduce

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.Patterns
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.Duration
import systems.vostok.enzo.essentials.mapreduce.actors.MasterActor
import systems.vostok.enzo.essentials.mapreduce.messages.Result

class MapReduceApplication {
    final static List PATTERN = ['The quick brown fox tried to jump over the lazy dog and fell on the dog',
                                 'Dog is man\'s best friend',
                                 'Dog and Fox belong to the same family']

    static void main(String[] args) {
        Timeout timeout = new Timeout(Duration.create(5, 'seconds'))
        ActorSystem system = ActorSystem.create('MapReduceApp')

        ActorRef master = system.actorOf(Props.create(MasterActor.class), 'master')

        PATTERN.each { master.tell(it, ActorRef.noSender()) }

        Thread.sleep(5000)

        Future<Object> future = Patterns.ask(master, new Result(), timeout)
        String result = (String) Await.result(future, timeout.duration())
        System.out.println(result)
        system.terminate()
    }
}
