package systems.vostok.humexp.concurrency

import groovy.util.logging.Slf4j

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveTask

@Slf4j
class ForkJoinDcExample {

    private static long N = 10_000_000
    private static int NUM_THREADS = 10

    static class RecursiveSumOfN extends RecursiveTask<Long> {

        long from, to

        RecursiveSumOfN(long from, long to) {
            this.from = from
            this.to = to
        }

        @Override
        protected Long compute() {

            if ((to - from) <= N / NUM_THREADS) {
                long localSum = 0

                for (long i = from; i <= to; i++) {
                    localSum += i
                }

                return localSum
            } else {
                long mid = (from + to) / 2
                RecursiveSumOfN firstHalf = new RecursiveSumOfN(from, mid)

                firstHalf.fork()

                RecursiveSumOfN secondHalf = new RecursiveSumOfN(mid + 1, to)

                long resultSecond = secondHalf.compute()

                firstHalf.join() + resultSecond
            }
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(NUM_THREADS)

        long timeBefore = System.currentTimeMillis()

        //long computedSum = pool.invoke(new RecursiveSumOfN(0, N)) // 137

        //long formulaSum = (N * (N + 1)) / 2 // 19

        // 39
        long iteratorSum = 0
        for (long i = 0; i <= N; i++) {
            iteratorSum += i
        }

        // 1805
        /*long listSum = (0 .. 10_000_000).inject(0) { result, i ->
            result + i
        }*/

        log.info ((System.currentTimeMillis() - timeBefore) as String)
        log.info iteratorSum as String


    }
}
