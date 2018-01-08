package systems.vostok.humexp.concurrency

import groovy.transform.Canonical
import groovy.util.logging.Slf4j

import java.util.concurrent.atomic.AtomicInteger

@Slf4j
class RaceConditionInAtomic {
    /*
    * Synchronized atomic
    */

    static void main(String[] args) {
        new Executor(name: 'child').with { executor ->
            3.times {
                new Thread(executor).start()
            }
        }

        sleep(100)
        log.info(Counter.counter as String)
    }

    @Canonical
    static class Executor implements Runnable {
        String name

        @Override
        void run() {
            incrementAndPrint()
            incrementAndPrint()
            incrementAndPrint()
        }

        void incrementAndPrint() {
            log.info("${Counter.counter.incrementAndGet()} in thread with name ${name}")

        }
    }

    static class Counter {
        private static AtomicInteger counter = new AtomicInteger(0)
    }
}

