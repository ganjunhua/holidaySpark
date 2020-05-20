package kafka


import kafka.serializer.StringDecoder
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}


object StreamingKafkaConsumer {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("StreamingKafkaProduce")
      .setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(1))
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val topic =("test1.json").split("\\,").toSet
    val kafkaParmas = Map[String, String]("metadata.broker.list" -> "holiday-1:9092,holiday-2:9092:holiday-3:9092")
    // createDirectStream[String, String, StringDecoder, StringDecoder]
    // String = 主题的类型
    //String = 消息数据的信息
    // StringDecoder, StringDecoder = 解码器，固定
    // map(_._2) 获取数据  _._1是主题信息  _._2是数据
    val kafkaRDD = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParmas, topic).map(_._2)
    kafkaRDD.flatMap(_.split(",")).map((_, 1))
      .reduceByKey((_ + _)).print()
    ssc.start()
    ssc.awaitTermination()
  }
}
