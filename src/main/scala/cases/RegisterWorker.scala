package cases

//注册消息
case class RegisterWorker(val workerID: String, val cpu: Int, val memory: Int) extends Serializable

//注册成功消息
case class RegisteredWorker(val masterURL: String) extends Serializable

//心跳消息
case class HeartBeat(val workerID: String) extends Serializable

case object sedHeartBeat

case object CheckTimeOut

