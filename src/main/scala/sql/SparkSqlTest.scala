package sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSqlTest {
  def main(args: Array[String]): Unit = {
    val dataPath = "data\\sql\\test1\\test1.json"
    val spark = SparkSession.builder()
      .appName(this.getClass.getSimpleName)
      .master("local[*]")
      .getOrCreate()
    import spark.implicits._
    val df = spark.read.json(dataPath)
    df.show()
    spark.stop()
  }
}
