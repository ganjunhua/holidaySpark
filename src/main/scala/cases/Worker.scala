package cases

import java.util.UUID

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._


class Worker(masterHostname: String, port: Int, cpu: Int, memory: Int) extends Actor {
  var masterRef: ActorSelection = _
  var workerID: String = _

  override def preStart(): Unit = {
    //固定写法akka.tcp://xxx@xx:xx/user/masterName
    val masterinfo = s"akka.tcp://${Masterc.masterActorName}@localhost:8888/user/${Masterc.master}"
    // 获得master的信息
    masterRef = context.actorSelection(masterinfo)
    // 发送消息
    workerID = UUID.randomUUID().toString
    masterRef ! RegisterWorker(workerID = workerID, cpu, memory)
  }

  override def receive = {
    case RegisteredWorker(masterURL) => {
      println("我是worker，我接收到了Master发过来的消息" + masterURL)
      //发送心跳
      /**定时器
        * initialDelay: FiniteDuration, 多久以后执行代码
        * interval:     FiniteDuration, 每隔多长时间执行一次
        * receiver:     ActorRef,   谁执行这个任务
        * message:      Any  干什么
        */
      import context.dispatcher
      context.system.scheduler.schedule(0 second, 3 second, self, sedHeartBeat)
    }
    case sedHeartBeat => {
      // 发送消息
      masterRef ! HeartBeat(workerID)
    }

  }
}

object Worker {

  val workSystemname = "workSystem"
  val workname = "work"

  def main(args: Array[String]): Unit = {

    val host = "localhost" // work的主机名
    val masterHostname = "localhost" //master的主机名
    val port = 881 //master的端口号
    val cpu = 123 //配置 的
    val memory = 123 //配置的

    val confString = //
      s"""
         |akka.actor.provider="akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname="${host}"
         |akka.remote.netty.tcp.port="${port}"
      """.stripMargin

    val config = ConfigFactory.parseString(confString)
    val actorsystem = ActorSystem(workSystemname, config = config)
    actorsystem.actorOf(Props(new Worker(masterHostname, port.toInt, cpu.toInt, memory.toInt)), workname)

  }
}
