package socket


import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.ServerSocket

object MyRPCServer {
  def handlerResiter(username: String, password: String): Unit = {
    println("注册成功")
    ResultMsg(1, "注册成功")
  }

  def main(args: Array[String]): Unit = {
    val serverSocket: ServerSocket = new ServerSocket(8888)

    val client = serverSocket.accept()
    // 获取客户端传过来的流
    val stream = new ObjectInputStream(client.getInputStream)
    while(true){
    val clientMsg = stream.readObject()

    var unit = clientMsg match {
      case RegisterMsg(username, password) => handlerResiter(username, password)
      case HearBeat(hostname, state) => println(hostname + state)   ;"xxxx"
    }

    //获取客户端的输出流对象
    val stream1: ObjectOutputStream = new ObjectOutputStream(client.getOutputStream)
    stream1.writeObject(unit)
    stream1.flush()

  }}
}
