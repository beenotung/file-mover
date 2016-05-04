package com.github.beenotung.filemover.utils

import java.net.NetworkInterface
import scala.collection.JavaConversions._

/**
  * Created by beenotung on 5/4/16.
  */
object Network {
  def LocalIP(): Option[String] = {
    NetworkInterface.getNetworkInterfaces.foreach {
      case iface if iface.isLoopback =>
      case iface => iface.getInetAddresses.foreach {
        case inet =>
          val s = inet.getHostAddress
          if (!s.contains(":")) {
            return Some(s)
          }
      }
    }
    None
  }
}
