package kafka

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import kafka.utils.{ZKGroupTopicDirs, ZkUtils}
import org.I0Itec.zkclient.ZkClient
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreamingAndKafkaOffset {


  def main(args: Array[String]): Unit = {
    val checkpointPath = "hdfs://holiday-1:8020/kafkaoffset"
    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("SparkStreamingAndKafkaOffset")
    val ssc = new StreamingContext(conf, Seconds(10))
    // ssc.checkpoint(checkpointPath)
    Logger.getLogger("org.apche.spark").setLevel(Level.WARN)
    val brokerList = "holiday-1:9092"
    val topic = "test4"
    val group_id = "1111"
    val kafkaParams = Map("metadata.broker.list" -> brokerList,
      "group.id" -> group_id)
    // 在zk创建一个目录，其实这个目录 就是zk里面帮忙保存偏移量的目录
    // 第一个参数 group id ,第二个参数 主题
    val topicDir = new ZKGroupTopicDirs(group_id, topic)
    //通过 zkPath这个对象获取到这个对象 的目录 ,这个就是用来保存偏移量信息
    val zkPath = topicDir.consumerOffsetDir
    val zkList = "holiday-1:2181,holiday-2:2181,holiday-3:2181"
    // 创建zk客户端

    val zkClient = new ZkClient(zkList)
    //获取刚刚目录下有多少个子节点
    val zkChildren = zkClient.countChildren(zkPath)

    //用于保存偏移量
    var fromOffSet: Map[TopicAndPartition, Long] = Map()
    // 用于创建 kafkadstream
    var kafkaStream: InputDStream[(String, String)] = null
    // zkChildren大于 表示该 分区已经有记录偏移量了
    if (zkChildren > 0) {
      for (i <- 0 until zkChildren) {
        //循环获取到每个分区中的偏移量 ,partitionOffset 这个就是偏移量
        val partitionOffset = zkClient.readData[String](s"${zkPath}/${i}")
        //获取 topic 对应的 分区
        val topicAndPart = new TopicAndPartition(topic, i)
        //test_1 -> 100  将不同分区的偏移量信息记录起来
        fromOffSet += (topicAndPart -> partitionOffset.toLong)
      }
      /*
      第一个参数StreamingContext ：ssc
      第二参数kafkaParams：kafkaParams
      第三个参数offset:Map[topicandPartition,long] 主题分区 与偏移量
      第四个参数messageHandler:它是一个函数参数
      */
      // (mmd: MessageAndMetadata[String, String]): 第一个String代表主题,第二个String代表数据
      val messageHandler = (mmd: MessageAndMetadata[String, String]) => (
        mmd.topic, mmd.message()
      )
      kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder, (String, String)](ssc,
        kafkaParams,
        fromOffSet,
        messageHandler)
      print("1111111111111111111111111111111111")
    } else {
      /*
      没有偏移量，第一次读
       */

      kafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topic.split(",").toSet)
   print("elsexxxxxxxxxxxxxxxxxxxxx")
    }

    print("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyy")
    // 用于存储偏移量
    var offsetRanges = Array[OffsetRange]()
    kafkaStream.transform(rdd => {
      print("asssssssssssssssssssssssssssssssss")
      // 获取 到分区的偏移量
      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd
    })// .map(x => x._2)
      .foreachRDD(rdd => {
        rdd.foreachPartition(partitions => {
          partitions.foreach(record => {
            // 这里面写业务代码
            println( "=====99999999999999999999999999===================")
            println(record + "=======================")
          })
        })
        //处理完数据后，变更偏移量
        for (o <- offsetRanges) {
          // val newZKPath = s"${zkPath}/${o.partition}"
          ZkUtils.updatePersistentPath(zkClient, s"${zkPath}/${o.partition}", o.fromOffset.toString)
        }
      })
    ssc.start()
    ssc.awaitTermination()
  }
}
