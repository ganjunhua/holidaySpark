package actor

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class Master extends Actor {
  //Actor生命周期的方法，这个方法类似一个死循环
  //一直在运行
  //作用：用来接收数据
  //这个方法 是一个偏函数  PartialFunction[Any, Unit]
  //用来做模式匹配的 Any，代表的是输入参数类型，Unit，返回值类型，也就是说这是没有返回值的

  override def receive = {
    case "hpello" => {
      println("我是master，接收到hello")
      //这句代码代表的就是master给work发送了一个Hi的消息
      //sender() 谁发送过来的消息，sender()就是谁
      sender() ! "hi"
    }
  }
}

object Master {
  def main(args: Array[String]): Unit = {
    val host = "localhost"
    val port = 8888
    val confString =
      s"""
         |akka.actor.provider="akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname="${host}"
         |akka.remote.netty.tcp.port="${port}"
      """.stripMargin
    val conf = ConfigFactory.parseString(confString)
    //创建actorSystem
    val actorSystem = ActorSystem("masterActorName", conf)
    //创建actor
    actorSystem.actorOf(Props(new Master), "master")

  }
}
