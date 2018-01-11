package systems.vostok.humexp.sockets

class InetAddress {
    public static void main(String[] args) {
        Enumeration enumeration = NetworkInterface.getNetworkInterfaces()

        while(enumeration.hasMoreElements()) {
            def value = enumeration.nextElement()
            value
        }
    }
}
