package tramsaformtest

import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}


object TransformTest {

  val fun = (vales: Seq[Int], state: Option[Int]) => {
    val count = vales.sum
    val lastcount = state.getOrElse(0)
    Some(count + lastcount)
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("TransformTest")
    val ssc = new StreamingContext(conf, Seconds(20))

     ssc.checkpoint("hdfs://holiday1:8020/tmp/x")
    val wordRDD = ssc.socketTextStream("holiday1", 9999)
    val blackWord = Array("?", "!", ":").map((_, true))
    // 广播变量
    val boradCastWord = ssc.sparkContext.broadcast(blackWord)

    //val blackW = ssc.sparkContext.parallelize(blackWord)
     val blackW = ssc.sparkContext.parallelize(boradCastWord.value)

    val wordCount = wordRDD.flatMap(_.split(",")).map((_, 1))
      .transform(rdd => {

        // ?,1          join ? true
        // String = key 值
        // Int 1
        // Option[Boolean] 两个RDD 如果没有连接上，则=null isEmpty
        // RDD[(String, (Int, Option[Boolean]))] = x._2._2  Option[Boolean]
        val value: RDD[(String, (Int, Option[Boolean]))] = rdd.leftOuterJoin(blackW)
        val resultRDD = value.filter(x => {
          val y = x._2
          if (y._2.isEmpty) {
            true
          } else {
            false
          }
        })

        resultRDD.map(x => {
          (x._1, 1)
        })
      })

    wordCount.updateStateByKey(fun).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
