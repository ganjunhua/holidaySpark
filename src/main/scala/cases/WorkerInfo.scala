package cases

class WorkerInfo(val workerID: String, val cpu: Int, val memory: Int) {
  var lastHeartbeatTime: Long = _
}
