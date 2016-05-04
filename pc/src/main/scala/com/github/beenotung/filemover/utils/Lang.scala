package com.github.beenotung.filemover.utils

import scala.language.implicitConversions

/**
  * Created by beenotung on 5/4/16.
  */
object Lang {
  def fork(f: () => Unit): Thread = {
    val t = new Thread(f)
    t.start()
    t
  }

  implicit def runnable(f: () => Unit): Runnable = new Runnable {
    override def run(): Unit = f()
  }
}
case object Error
