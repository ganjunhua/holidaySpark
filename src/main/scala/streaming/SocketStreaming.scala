package streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SocketStreaming {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("x")
      // 即线程数，至少两个 local[*]
      .setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(10))

    val dataRDD = ssc.socketTextStream("holiday1", 9999)

    val wordRDD = dataRDD.flatMap(x => x.split(","))
    val wordCount = wordRDD
      .map((_, 1)).reduceByKey((_ + _))
    wordCount.print()
    ssc.start()
    ssc.awaitTermination()

  }
}
