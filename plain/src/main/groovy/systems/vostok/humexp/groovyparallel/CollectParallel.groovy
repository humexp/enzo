package systems.vostok.humexp.groovyparallel

import groovy.util.logging.Slf4j

import static groovyx.gpars.GParsPool.withPool

@Slf4j
class CollectParallel {

    static void main(String[] args) {
        def numbers = [1, 2, 3, 4, 5, 6]
        def squares = [1, 4, 9, 16, 25, 36]

        withPool {
            assert squares == numbers.collectParallel {
                log.info('calculate {}', it)
                it * it
            }
        }
    }

}
