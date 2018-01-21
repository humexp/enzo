package systems.vostok.enzo.essentials.mapreduce.messages

final class ReduceData {
    private final HashMap<String, Integer> reduceDataList

    HashMap<String, Integer> getReduceDataList() {
        reduceDataList
    }

    ReduceData(HashMap<String, Integer> reduceDataList) {
        this.reduceDataList = reduceDataList
    }
}
