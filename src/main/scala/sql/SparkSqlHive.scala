package sql

import java.io.File

import org.apache.spark.sql.{Dataset, SparkSession}

object SparkSqlHive {

  case class Person(var name: String, var age: Long)

  def main(args: Array[String]): Unit = {
    // hive 所在的hdfs上的 warehouse 路径  hdfs://holiday-1:9000/user/hive/warehouse
    val warehouseLocation = new File("spark-warehouse").getAbsolutePath
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("xx")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._
    import spark.sql
    val personFile = spark.sparkContext.textFile("data\\sql\\test1\\person")
    val personRDD = personFile.map(x => x.split(",")).map(p => Person(p(0), p(1).toLong))

    personRDD.filter(p => {
      if (p.age > 11) {
        true
      } else {
        false
      }
    })

    //  RDD 转 dateframe
    val peopleDF = personRDD.toDF()
    peopleDF.createOrReplaceTempView("peopleDF")
    spark.sql("select * from peopleDF")

    // RDD 转 dataset
    personRDD.toDS()

    // dataframe 转 dataset
    // dataSet[Row] = dataframe 弱类型
    // DataSet[Person] = dataset 强类型
    val personDS: Dataset[Person] = peopleDF.as[Person]
    personDS.createOrReplaceTempView("personDS")
    spark.sql("select * from personDS").show()


    spark.stop()
  }
}
