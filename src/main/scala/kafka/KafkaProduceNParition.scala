package kafka

import java.util
import java.util.Properties

import kafka.producer.{KeyedMessage, Producer, ProducerConfig}

object KafkaProduceNParition {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put("metadata.broker.list", "holiday-1:9092")
    //数据写入到kafka中的是用到的序列化方式
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    val kafkaConf = new ProducerConfig(props)
    val producer = new Producer[String, String](kafkaConf)

    for (i <- 0 to 2) {

      val messageList = new util.ArrayList[KeyedMessage[String, String]]()


      for (k <- 0 to 100) {
        val mess = new KeyedMessage[String, String]("test2", i.toString, "mess" + k.toString + "->" + i.toString)
        println(mess)
        producer.send(mess)
      }
      producer.send()
    }
  }
}
