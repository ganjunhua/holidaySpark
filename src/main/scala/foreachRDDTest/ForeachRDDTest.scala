package foreachRDDTest

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object ForeachRDDTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[*]")
      .setAppName("*")
    val ssc = new StreamingContext(conf, Seconds(1))
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

    val word = ssc.socketTextStream("holiday1", 9999)
    val wordCount = word.flatMap(_.split(",")).map((_, 1)).reduceByKey(_ + _)

    wordCount.foreachRDD(rdd => {
      rdd.foreachPartition(rddPartition => {
        // 获取连接
        val conn = MysqlPool.getJDBCConn()
        val statment = conn.createStatement()
        rddPartition.foreach(record => {
          val word = record._1
          val count = record._2.toInt
          val sql = s"insert into wordcount values (now(),'$word',$count)"
          statment.execute(sql)
        })
        // 释放连接
        MysqlPool.releaseConn(conn)
      })
    })

    ssc.start()
    ssc.awaitTermination()
  }
}
