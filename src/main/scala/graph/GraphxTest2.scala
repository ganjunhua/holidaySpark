package graph

import org.apache.spark.graphx.GraphLoader
import org.apache.spark.sql.SparkSession

object GraphxTest2 {
  def main(args: Array[String]): Unit = {
    val userData = "data\\graph\\test1\\users.txt"
    val followersData = "data\\graph\\test1\\followers.txt"

    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName(this.getClass.getSimpleName)
      .getOrCreate()
    val sc = spark.sparkContext

    //加载边的数据,每一行数据即表示一张图
    val graph = GraphLoader.edgeListFile(sc, followersData)
    // graph.vertices.foreach(println)
    //  这个方法数据打印结果,给每个顶点去重 且加了 1
    /*
(1,1)
(4,1)
(3,1)
(6,1)
(7,1)
(2,1)
     */

    // 这个方法很重要，返回结果，去重，且以一组最小的值作为 value
    val cc = graph.connectedComponents().vertices
    /*
    (1,1)
    (4,1)
    (3,3)
   (6,3)
   (7,3)
  (2,1)
     */
    val usersRDD = sc.textFile(userData)
      .map(x => {
        val fields = x.split(",")
        (fields(0).toLong, fields(1))
      })


    usersRDD.join(cc).map {
      case (id, (name, cc)) => (cc, name)
    }.reduceByKey((x: String, y: String) => {
      x + "-" + y
    }).foreach(println)
    spark.stop()
  }
}
