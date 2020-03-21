package actor

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

class Worker extends Actor {

  override def preStart(): Unit = {
    //固定写法akka.tcp://xxx@xx:xx/user/masterName
    val masterinfo = "akka.tcp://masterActorName@localhost:8888/user/master"
    // 获得master的信息
    val masterRef = context.actorSelection(masterinfo)
    masterRef ! "hpello"
  }

  override def receive = {
    case "hi" => {
      println("我是worker，我接收到了Master发过来的消息")
    }
  }
}

object Worker {
  def main(args: Array[String]): Unit = {

    val host = "localhost"
    val port = 8888
    val confString =
      s"""
         |akka.actor.provider="akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname="${host}"
      """.stripMargin

    val config = ConfigFactory.parseString(confString)
    val actorsystem = ActorSystem("workSystem", config = config)
    actorsystem.actorOf(Props(new Worker()), "work")

  }
}
