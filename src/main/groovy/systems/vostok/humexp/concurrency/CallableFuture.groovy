package systems.vostok.humexp.concurrency

import groovy.util.logging.Slf4j

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Slf4j
class CallableFuture implements Callable<String> {
    int a
    int b

    CallableFuture(int a, int b) {
        this.a = a
        this.b = b
    }

    @Override
    String call() throws Exception {
        log.info a as String
        sleep(500)
        a + b
    }
}

@Slf4j
class  CallableExecutor {
    static void main(String[] args) {
        long beforeTime = System.currentTimeMillis()
        //println new CallableExecutor().executeSynk(0..10)   // execution time 5660
        println new CallableExecutor().executeAsynk(0..10)
        println  (System.currentTimeMillis() - beforeTime)
    }

    int executeSynk(List numbers) {
        ExecutorService executorService = Executors.newFixedThreadPool(10)

        def sum = numbers.inject(0) { result, i ->
            executorService.submit(new CallableFuture(result as Integer, i as Integer)).get()
        } as Integer

        executorService.shutdown()
        sum
    }

    int executeAsynk(List numbers) {


    }
}
