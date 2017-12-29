package systems.vostok.humexp.concurrency

class Deadlocks {
    static void main(String[] args) {
        DeadlockExecutor deadlockExecutor = new DeadlockExecutor()

        Thread first = new Thread(deadlockExecutor)
        Thread second = new Thread(deadlockExecutor)
        Thread third = new Thread(deadlockExecutor)
        Thread forth = new Thread(deadlockExecutor)

        first.start()
        second.start()
        third.start()
        forth.start()

        first.join()
        second.join()
        third.join()
        forth.join()

        println Balls.counter
        println Runs.counter
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

class Balls {
    static int counter
}

class Runs {
    static int counter
}

