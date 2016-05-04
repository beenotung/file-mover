package com.github.beenotung.filemover

import java.net.ServerSocket
import javafx.event.ActionEvent

import com.github.beenotung.filemover.utils.Lang._
import com.github.beenotung.filemover.utils.Network
import javafx.application.Platform.{runLater => runOnUIThread}

/**
  * Created by beenotung on 5/4/16.
  */
class MainController extends MainControllerSkeleton {

  def start_server(): Unit = {
    fork(() => {
      val server = new ServerSocket(0)
      Network.LocalIP() match {
        case Some(s) => runOnUIThread(() => pcaddress_label.setText(s + ":" + server.getLocalPort))
        case None => runOnUIThread(() => pcaddress_label.setText("not connected"))
      }
    })
  }

  override def customInit(): Unit = {
    start_server()
  }

  override def enter_mobile_address(actionEvent: ActionEvent): Unit = {
    println(mobile_address_textfield getText())
  }
}
