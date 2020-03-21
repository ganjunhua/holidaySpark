package cases

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._
import scala.collection.mutable

class Master(val masterhost: String, mastport: Int) extends Actor {
  override def preStart(): Unit = {
    print("fadsf")
    import context.dispatcher
    context.system.scheduler.schedule(0 second, 10 second, self, CheckTimeOut)
  }

  val id2workerinfo = new mutable.HashMap[String, WorkerInfo]()
  val workerinfos = new mutable.HashSet[WorkerInfo]()
  //Actor生命周期的方法，这个方法类似一个死循环
  //一直在运行
  //作用：用来接收数据
  //这个方法 是一个偏函数  PartialFunction[Any, Unit]
  //用来做模式匹配的 Any，代表的是输入参数类型，Unit，返回值类型，也就是说这是没有返回值的

  override def receive = {

    //用来保存worker的心跳信息
    case RegisterWorker(workerID, cpu, memory) => {

      println("我是master，接收到hello")
      //这句代码代表的就是master给work发送了一个Hi的消息
      //sender() 谁发送过来的消息，sender()就是谁
      if (!id2workerinfo.contains(workerID)) {
        val workerinfo = new WorkerInfo(workerID, cpu, memory)
        id2workerinfo(workerID) = workerinfo
        workerinfos += workerinfo
        sender() ! RegisteredWorker(masterhost)
      }
    }

    case HeartBeat(workerID) => {
      val cutime = System.currentTimeMillis()
      var workinfo = id2workerinfo(workerID)
      workinfo.lastHeartbeatTime = cutime
      id2workerinfo(workerID) = workinfo
      workerinfos += workinfo

    }

    case CheckTimeOut => {
      // 做检测 工作
      val currentTime = System.currentTimeMillis()
      val deadWork = workerinfos.filter(x => currentTime - x.lastHeartbeatTime > 15000)

      deadWork.foreach(x => {
        id2workerinfo -= x.workerID
        workerinfos -= x
      })

      println("当前注册:" + workerinfos.size)
    }

  }
}

object Masterc {
  val masterActorName = "masterActorName"
  val master = "master"

  def main(args: Array[String]): Unit = {
    //这里面的值应该是通过配置文件解析出来的
    val host = args(0)
    val port = args(1).toInt
    val confString =
      s"""
         |akka.actor.provider="akka.remote.RemoteActorRefProvider"
         |akka.remote.netty.tcp.hostname="${host}"
         |akka.remote.netty.tcp.port="${port}"
      """.stripMargin
    val conf = ConfigFactory.parseString(confString)
    //创建actorSystem
    val actorSystem = ActorSystem(masterActorName, conf)
    //创建actor
    actorSystem.actorOf(Props(new Master(host, port)), master)

  }
}
