package test

import org.apache.spark.{SparkConf, SparkContext}

object TransformationOperator {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local").setAppName("xx")
    val sc = new SparkContext(conf)
    val array = Array("holiday", "h1", "h2")
    val rdd1 = sc.parallelize(array)

    val rdd2 = rdd1.map(x => "hello" + x)
    //rdd2.foreach(println)
    val a1 = Array(1, 2, 3, 4, 5, 6)
    val rdd3 = sc.parallelize(a1)
    //rdd3.filter(x => x % 2 == 0).foreach(println)

    val a2 = Array("1,2,3,4", "4,5,7,8")
    val rdd4 = sc.parallelize(a2)
    // rdd4.flatMap(x=>x.split(",")).foreach(println)

    val a3 = List(("java", "scala"), ("oracle", "mysql"), ("oracle", "sql"))
    val rdd5 = sc.parallelize(a3)

    rdd5.groupByKey().map(x => x._2).foreach(x => {
      val a = x.toArray
      for (i <- 0 until a.size) {
        //println(a(i))
      }
    })

    val a4 = List(("java", "1".toInt), ("oracle", 1), ("oracle", 1))
    val r6 = sc.parallelize(a4)
    r6.reduceByKey(_ + _)

    val a5 = List(("java", "1".toInt), ("oracle", 2), ("oracle", 3))
    val r7 = sc.parallelize(a5)

    r7.map(x=>(x._2,x._1)).sortByKey(false).map(x=>(x._2,x._1)).foreach(println)


    sc.stop()
  }
}
