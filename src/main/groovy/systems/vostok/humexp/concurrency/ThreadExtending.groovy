package systems.vostok.humexp.concurrency

import groovy.util.logging.Slf4j

@Slf4j
class ThreadExtending {

    static void main(String[] args) {
        log.info('In main')
        new Executor().run() // it executed method run() in the same thread
        new Executor().start()
    }

    static class Executor extends Thread {
        @Override
        void run() {
            log.info('In executor')
        }
    }
}
