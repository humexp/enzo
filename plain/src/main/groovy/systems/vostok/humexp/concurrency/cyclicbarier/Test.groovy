package systems.vostok.humexp.concurrency.cyclicbarier

import groovy.util.logging.Slf4j

import java.util.concurrent.CyclicBarrier

@Slf4j
class Game extends Thread {
    @Override
    void run() {
        log.info('MixedDoubleTennisGame has been started')
    }
}

@Slf4j
class Player extends Thread {

    CyclicBarrier awaitPoint

    Player(CyclicBarrier point, String name) {
        this.awaitPoint = point
        this.name = name

        this.start()
    }

    @Override
    void run() {
        log.info("player $name started to play")
        awaitPoint.await()
    }
}

class Test {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Game())

        new Player(cyclicBarrier, '1')
        new Player(cyclicBarrier, '2')
        new Player(cyclicBarrier, '3')
    }
}
