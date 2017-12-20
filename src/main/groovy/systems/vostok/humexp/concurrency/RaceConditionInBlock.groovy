package systems.vostok.humexp.concurrency

import groovy.transform.Canonical
import groovy.util.logging.Slf4j

@Slf4j
class RaceConditionInBlock {
    /*
    * Synchronized blocks
    * Synchronization on methods
    */

    static void main(String[] args) {
        log.info('In main')
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
            // avoid race condition
            synchronized (this) {
                log.info("${++Counter.counter} in thread with name ${name}")
            }

            // race condition
            //log.info("${++Counter.counter} in thread with name ${name}")
        }
    }
}

