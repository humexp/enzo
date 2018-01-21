package systems.vostok.enzo.essentials.mapreduce.messages

final class MapData {

    private final List<WordCount> dataList

    List<WordCount> getDataList() {
        return dataList
    }

    MapData(List<WordCount> dataList) {
        this.dataList = dataList
    }
}
