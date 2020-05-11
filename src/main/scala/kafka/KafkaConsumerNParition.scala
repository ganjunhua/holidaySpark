package kafka

import java.util.Properties
import java.util.concurrent.Executors

import kafka.consumer.{Consumer, ConsumerConfig, KafkaStream}

import scala.collection.mutable

object KafkaConsumerNParition {
  def main(args: Array[String]): Unit = {
    val topic = "test2"
    val threads = 3
    val props = new Properties()
    props.put( "zookeeper.connect", "holiday-2:2181,holiday-3:2181,holiday-1:2181")
    props.put("group.id", "holiday111oo091")
    props.put("auto.offset.reset", "smallest")
    val conSumConf = new ConsumerConfig(props)
    // 创建consumer对象
    val consumer = Consumer.create(conSumConf)
    var topicCountMap = new mutable.HashMap[String, Int]()
    topicCountMap.put(topic, threads)
    // 获取数据,返回的map类型中key：topic名称，value：topic对应的数据
    val consumMap = consumer.createMessageStreams(topicCountMap)
    // 获取指定topic的数据
    val streams = consumMap.get(topic)
    // 有几个分区 就创建 几个线程
    val pool = Executors.newFixedThreadPool(3.toInt)

    for (i <- 0 until streams.size) {
      println("i" + i)
      pool.execute(new ConsumerDemo1(i.toString, streams.get(i)))
    }
  }
}
