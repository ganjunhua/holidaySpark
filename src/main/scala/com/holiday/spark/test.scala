package com.holiday.spark

object Test {
  def sayHello(name: String, age: Int): Unit = {

  }

  def f: PartialFunction[String, Int] = {
    case "a" => 1
    case _ => 2
  }
// 加入了一个implicit隐士的关键字
  def k2(x: Int)(implicit y: Int = 10) = x + y

  def main(args: Array[String]): Unit = {
    val array = Array(1)
    val map = Map("a" -> 2, "b" -> 1)
    print(f("a"))
  }
}
