package windows

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WindowsTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("a")
      .setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(10))
    // 设置打印日志级别
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
    val wordRDD = ssc.socketTextStream("holiday1", 9999)
    val wrod = wordRDD.flatMap(_.split(",")).map((_, 1))
    val wordCount = wrod.reduceByKeyAndWindow((x: Int, y: Int) => x + y, Seconds(30), Seconds(10))
    wordCount.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
