package socket

trait RemoteMessage extends Serializable {

}
case  class RegisterMsg(username:String,password:String) extends RemoteMessage
case  class ResultMsg(id:Int,context:String) extends RemoteMessage
case  class HearBeat(id:Int,context:String) extends RemoteMessage
