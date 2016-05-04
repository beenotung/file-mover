package com.github.beenotung.filemover

import javafx.event.ActionEvent


/**
  * Created by beenotung on 5/4/16.
  */
class MainController extends MainControllerSkeleton {
  override def customInit(): Unit = {}

  override def enter_mobile_address(actionEvent: ActionEvent): Unit = {
    println(mobile_address_textfield getText())
  }
}
