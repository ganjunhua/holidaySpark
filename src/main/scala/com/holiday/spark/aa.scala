package com.holiday.spark

class Man() {}

class SuperMan() {
  def run = print("xxxxxxxxxx")
}

object aa {
  implicit def max2(man: Man): SuperMan = new SuperMan

  def main(args: Array[String]): Unit = {
    new Man().run
  }

}
