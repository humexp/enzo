package systems.vostok.humexp.parameterclass

class ClassParameters {
    static void main(String[] args) {
        printExecutor(ClassExecutor)
    }

    static void printExecutor(Class targetClass) {
        targetClass.printer()
    }
}
