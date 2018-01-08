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
        //log.info a as String
        //sleep(500)
        a + b
    }
}

@Slf4j
class  CallableExecutor {
    static void main(String[] args) {
        long beforeTime = System.currentTimeMillis()
        println new CallableExecutor().executeSynch(0..1_000_000)   //
        // println new CallableExecutor().executeAsynch(0..10_000_000)  // out of memory
        println  (System.currentTimeMillis() - beforeTime)
    }

    int executeSynch(List numbers) {
        ExecutorService executorService = Executors.newFixedThreadPool(1)

        def sum = numbers.inject(0) { result, i ->
            executorService.submit(new CallableFuture(result as Integer, i as Integer)).get()
        } as Integer

        executorService.shutdown()
        sum
    }


    int executeAsynch(List numbers) {
        ExecutorService executorService = Executors.newFixedThreadPool(10)

        int result = calculateAsynch(numbers, executorService)
        executorService.shutdown()
        result
    }

    int calculateAsynch(List numbers, ExecutorService executorService) {
        int previous = 0
        int numSize = numbers.size()

        List sumList = numbers.indexed(1)
        .collect { index, item ->
            if (index%2==0) {
                return executorService.submit(new CallableFuture(previous, item))
            }
            previous = item as Integer
            index == numSize ? item : null
        }.findAll { it }
        .collect {
            if(it instanceof Future) {
                it.get() as Integer
            } else {
                it
            }
        }

        if (sumList.size() == 1) {
            return sumList.first()
        } else {
            return executeAsynch(sumList)
        }
    }
}
