package systems.vostok.humexp.concurrency

import groovy.util.logging.Slf4j

import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

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
        // println new CallableExecutor().executeSynk(0..100)   // execution time 10: 5710 sum: 55 // 100: 50833 sum: 5050
        println new CallableExecutor().executeAsynk(0..10)  // execution time 10: 3694 sum: 55  //
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
        ExecutorService executorService = Executors.newFixedThreadPool(10)
        int previous = 0
        int numSize = numbers.size()

        Integer sum = numbers.indexed(1)
                .collect { index, item ->
                            if (index%2==0) {
                                return executorService.submit(new CallableFuture(previous, item))
                            }
                            previous = item as Integer
                            index == numSize ? item : null
                }.findAll { it }
                .collect {
                            if(it instanceof Future) {
                                it.get()
                            } else {
                                it
                            }
                }.inject(0) { result, i ->
                             executorService.submit(new CallableFuture(result as Integer, i as Integer)).get() } as Integer

        executorService.shutdown()
        sum
    }
}
