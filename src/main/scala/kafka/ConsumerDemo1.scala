package kafka

import kafka.consumer.KafkaStream

class ConsumerDemo1(val consumer: String, val stream: KafkaStream[Array[Byte], Array[Byte]]) extends Runnable {
  override def run() = {
    val it = stream.iterator()
    print("aaaaaaaaaaaa")
    while (it.hasNext()) {
      val data = it.next()
      val topic = data.topic
      val partition = data.partition
      val offset = data.offset
      val msg = new String(data.message())
      println(s"Consumer:$consumer, topic:$topic, partiton:$partition, offset:$offset, msg:$msg")
    }
  }
}
