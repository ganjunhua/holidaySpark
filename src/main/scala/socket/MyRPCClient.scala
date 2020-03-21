package socket

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.Socket

object MyRPCClient {
  def main(args: Array[String]): Unit = {

    val socker = new Socket("localhost", 8888)

    //给服务端发消息
    val oos = new ObjectOutputStream(socker.getOutputStream)
    oos.writeObject(RegisterMsg("xxx", "123456"))
    oos.flush()

    //接收服务端发过来的消息
    val ois = new ObjectInputStream(socker.getInputStream)
    val msg = ois.readObject()
    println(msg)
    oos
      .writeObject(HearBeat(1, "alive"))

    oos.flush()

    val heartbeatMsg = ois.readObject()
    println(heartbeatMsg)
    oos.close()
    ois.close()
    socker.close()
  }
}
