package systems.vostok.humexp.concurrency

import groovy.util.logging.Slf4j

@Slf4j
class ExecutorServiceForRunnable implements Runnable {
    @Override
    void run() {
        log.info 'in runnable'
    }
}

class ExecutorServiceForRunnableTest {
    static void main(String[] args) {

        /*ExecutorService executorService = Executors.newFixedThreadPool(2)
        executorService.submit(new ExecutorServiceForRunnable())

        executorService.shutdown()
        */

        new Thread(new ExecutorServiceForRunnable()).start()
    }
}
