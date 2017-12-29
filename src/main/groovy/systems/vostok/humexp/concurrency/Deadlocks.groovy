package systems.vostok.humexp.concurrency

import groovy.util.logging.Slf4j

@Slf4j
class Deadlocks {
    static void main(String[] args) {
        new DeadlockExecutor()
                .with { executor -> new Thread[6].collect { new Thread(executor) } }
                .each { it.start() }
                .each { it.join() }

        [Balls, Runs].each { log.info(it.counter as String) }
    }
}

class DeadlockExecutor implements Runnable {

    @Override
    void run() {
        incrementBallsAfterRuns()
        incrementRunsAfterBalls()
    }

    void incrementRunsAfterBalls() {
        synchronized (Balls.class) {
            synchronized (Runs.class) {
                Balls.counter++
                Runs.counter++
            }
        }
    }

    void incrementBallsAfterRuns() {
        synchronized (Runs.class) {
            synchronized (Balls.class) {
                Runs.counter++
                Balls.counter++
            }
        }
    }
}

interface Inventar {

}

class Balls implements Inventar {
    static int counter
}

class Runs implements Inventar {
    static int counter
}

