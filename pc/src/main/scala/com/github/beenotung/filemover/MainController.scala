package com.github.beenotung.filemover

import java.net.{ServerSocket, Socket}
import javafx.event.ActionEvent

import com.github.beenotung.filemover.utils.Lang._
import javafx.application.Platform.{runLater => runOnUIThread}
import javafx.scene.control.Alert
import javafx.stage.{DirectoryChooser, FileChooser}

import com.github.beenotung.filemover.network.NetworkHelper
import com.github.beenotung.filemover.utils.Lang

/**
  * Created by beenotung on 5/4/16.
  */
class MainController extends MainControllerSkeleton {
  val NO_PATH = "set path"

  def start_server(): Unit = {
    fork(() => {
      val server = new ServerSocket(0)
      utils.Network.LocalIP() match {
        case Some(s) => runOnUIThread(() => pcaddress_label.setText(s + ":" + server.getLocalPort))
        case None => runOnUIThread(() => pcaddress_label.setText("not connected"))
      }
    })
  }

  override def customInit(): Unit = {
    setpath_pc_btn.setText(NO_PATH)
    setpath_mobile_btn.setText(NO_PATH)
    start_server()
  }

  def isAddrValid(s: String): Lang.Error = {
    val arr = s.split(":")
    if (arr.isEmpty) return new Error("port is missing")
    if (arr.length > 2) return new Error("extra colon is found")
    val host = arr(0)
    val hs = host.split("\\.")
    if (hs.length != 4) return new Error("the ip should be the format of a.b.c.d")
    val errors: Array[Error] = hs.map(s => {
      try {
        val a = s.toInt
        if (a < 0 || a > 255)
          return new Error("each number in ip should be 0..255")
        return Nil
      } catch {
        case e: NumberFormatException => return new Error("the ip should consist of number")
      }
    })
    val es = errors.filter(x => x != Nil)
    if (!es.isEmpty) {
      return es.head
    }
    val port = arr(1)
    try {
      val p = port.toInt
      if (p <= 0) return new Error("the port should be positive")
    } catch {
      case e: NumberFormatException => return new Error("the port should be a number")
    }
    Nil
  }

  override def enter_mobile_address(actionEvent: ActionEvent): Unit = {
    val addr = mobile_address_textfield getText()
    println(addr)
    val e = isAddrValid(addr)
    if (e != Nil) {
      println(e)
      val alert = new Alert(Alert.AlertType.WARNING)
      alert.setTitle("Invalid Address")
      alert.setContentText(s"$addr is not a valid address : ${e.text}\ncorrect format is ip:port")
      alert.show()
      return
    }
    val xs = addr.split(":")
    fork(() => {
      println("connecting")
      val host = xs(0)
      val port = xs(1).toInt
      val socket = new Socket(host, port)
      if (!NetworkHelper.handle(socket)) {
        val alert = new Alert(Alert.AlertType.ERROR)
        alert.setTitle("Connection Error")
        alert.setContentText("failed to connect to mobile")
        alert.show()
      } else {
        println("connected")
      }
    })
  }

  override def setpath_pc(actionEvent: ActionEvent): Unit = {
    val chooser = new DirectoryChooser()
    chooser.setTitle("Directory on PC")
    val dir = chooser.showDialog(MainApplication.stage)
    if (dir == null) return
    setpath_pc_btn.setText(dir.getPath)
  }

  override def setpath_mobile(actionEvent: ActionEvent): Unit = {
    ???
  }
}
