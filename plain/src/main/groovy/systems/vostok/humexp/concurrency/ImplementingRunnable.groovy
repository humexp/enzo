package systems.vostok.humexp.concurrency

import groovy.util.logging.Slf4j

@Slf4j
class ImplementingRunnable {

    static void main(String[] args) {
        log.info('In main')
        new Thread(new Executor()).start()
    }

    static class Executor implements Runnable {
        @Override
        void run() {
            log.info('In executor')
        }
    }
}
