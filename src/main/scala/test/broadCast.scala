package test

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

object broadCast {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    conf.setAppName(this.getClass.getSimpleName)
    conf.setMaster("local")
    val sc = new SparkContext(conf)

    //广播变量
    val a = 3;
    val broadcastA = sc.broadcast(a)

    val array = Array(1, 2, 3, 4, 5)
    val r1 = sc.parallelize(array)
    r1.map(x => x * broadcastA.value).foreach(println)

    r1.persist(StorageLevel.MEMORY_ONLY)

    // 计数器
    val asum = sc.longAccumulator("asum")
    r1.foreach(x => asum.add(x))
    println(asum.value)


    sc.stop()
  }
}
