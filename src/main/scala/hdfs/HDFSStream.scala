package hdfs

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object HDFSStream {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("HDFS")
    val ssc = new StreamingContext(conf, Seconds(10))

    val fileStream = ssc.textFileStream("hdfs://holiday1:8020/streaminghdfs")
    val wordCountRDD = fileStream.flatMap(_.split(",")).map(x => (x, 1)).reduceByKey((_ + _))
    wordCountRDD.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
