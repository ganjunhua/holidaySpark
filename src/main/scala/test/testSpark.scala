package test

import org.apache.spark.{SparkConf, SparkContext}

object testSpark {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName(this.getClass.getSimpleName)
    println(this.getClass.getSimpleName)
    val sc = new SparkContext(conf)
    val fileRDD = sc.textFile("E:\\1.txt")
    val wordc = fileRDD.flatMap(x => x.split(",")).map(x => (x, 1)).reduceByKey(_ + _)
    wordc.map(x=>(x._2,x._1)).sortByKey(false).map(x=>(x._2,x._1)).foreach(x => {
      println(x._1 + "--=====" + x._2)
    })


    sc.stop()
  }

}
