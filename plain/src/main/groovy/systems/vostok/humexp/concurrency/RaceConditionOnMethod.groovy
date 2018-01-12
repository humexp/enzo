package systems.vostok.humexp.concurrency

import groovy.transform.Canonical
import groovy.util.logging.Slf4j

@Slf4j
class RaceConditionOnMethod {
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

        // using new objects
       /* new Thread(new Executor(name: 'child 1')).start()
        new Thread(new Executor(name: 'child 2')).start()
        new Thread(new Executor(name: 'child 3')).start()*/

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

        // avoid race condition
        synchronized void incrementAndPrint() {

            // race condition
            log.info("${++Counter.counter} in thread with name ${name}")
        }
    }
}
