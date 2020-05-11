package updatestatebykey

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object UpdateStateByKeyTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("update")
      .setMaster("local[*]")

    val ssc = new StreamingContext(conf, Seconds(10))
    ssc.checkpoint("hdfs://holiday1:8020/checkpoint/streaming")
    val wordRDD = ssc.socketTextStream("holiday1", 9999)
    val wordCount = wordRDD.flatMap(_.split(",")).map(x => (x, 1))

    // state: Option[S]) 上一次这个单词出现次数的结果
    //values: Seq[V] 当前批次里面这个key出现的次数
    val updateFun = (values: Seq[Int], state: Option[Int]) => {
      val count = values.sum
      val lastCount = state.getOrElse(0)
      Some(count + lastCount)
    }
    wordCount.updateStateByKey(updateFun).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
