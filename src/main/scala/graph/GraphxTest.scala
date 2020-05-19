package graph

import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object GraphxTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setAppName("graph")
      .setMaster("local[*]")
    val sc = new SparkContext(conf)

    // 创建顶点RDD (3L, ("rxin", "student")
    val users: RDD[(VertexId, (String, String))] =
      sc.parallelize(Array((3L, ("rxin", "student")), (7L, ("jgonzal", "postdoc")),
        (5L, ("franklin", "prof")), (2L, ("istoica", "prof"))))

    // 创建边
    val relationships: RDD[Edge[String]] =
      sc.parallelize(Array(Edge(3L, 7L, "collab"), Edge(5L, 3L, "advisor"),
        Edge(2L, 5L, "colleague"), Edge(5L, 7L, "pi")))
    val graph = Graph(users, relationships)

    // 有多少人是教授
    // 获取一共所有的顶点
    val vert = graph.vertices
    val count = vert.filter {
      case (id, (name, pos)) => pos == "prof"
    }.count()
    println(count)
    sc.stop()

  }
}
