package com.holiday.spark


class Person(val id: Int, var name: String) {
  var age: Int = 23

  def this(id: Int, name: String, age: Int) {
    this(id, name) //辅助构造函数第一行代码必须 调用主构造函数或其他辅助构造函数
    this.age=age
    print("aaa")
  }
}


object Person {
  def main(args: Array[String]): Unit = {
    val aa = new Person(1, "1")

  }
}
