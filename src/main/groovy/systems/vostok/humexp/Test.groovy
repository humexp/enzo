package systems.vostok.humexp

class Pretender {
    def methodMissing(String name, Object args){
        print "$name $args was called"
    }
}


class Test {
    static void main(String[] args) {
        new Pretender().callMissingMethod('SomeArgsString')

        new Object()
    }

}
