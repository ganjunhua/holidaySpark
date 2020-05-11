package kafka

import java.util.Properties

import com.alibaba.fastjson.JSONObject
import kafka.producer.{KeyedMessage, Producer, ProducerConfig}

object KafkaPrudcer {
  def main(args: Array[String]): Unit = {
    val topic = "test4"
    val brokers = "holiday-1:9092,holiday-2:9092:holiday-3:9092"
    val props = new Properties()
    props.put("metadata.broker.list", brokers)
    props.put("serializer.class", "kafka.serializer.StringEncoder")

    val kafkaConfig = new ProducerConfig(props)
    val producer = new Producer[String, String](kafkaConfig)
    var whileTrue = true
    var whileCnt = 0
    while (whileTrue) {
      val event = new JSONObject()
      event.put("udi", "holiday")
      event.put("event_time", System.currentTimeMillis().toString)
      producer.send(new KeyedMessage[String, String](topic, event.toString()))
      println(event)
      whileCnt += 1
      if (whileCnt == 100) {
        whileTrue = false
      }
    }
  }

}
