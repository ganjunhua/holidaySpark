package kafka;

public interface KafkaProperties {
    final static String zkConnect = "holiday1:2181,holiday2:2181,holiday3:2181";
    final static String groupID = "group1";
    final static String topic = "holidayspark";
    final static String kafkaServerUrl = "holidayspark";
    final static int kafkaServerPort = 9092;
    final static int kafkaProducerBufferSize = 64 * 1024;
    final static int connectionTimeOut = 100000;
    final static int reconnectInterval = 10000;
    final static String topic2 = "flink";
    final static String topic3 = "flink";
    final static String clientID = "SimpleConsumerDemoClient";


}
