package com.github.beenotung.utils;


import java.io.*;
import java.util.Arrays;

/**
 * Created by beenotung on 5/5/16.
 */
public class Log {
  public static boolean info = true;
  public static boolean debug = true;
  public static boolean error = true;
  public static boolean log = true;
  private static final PrintWriter out;

  static {
    String name = "log-" + System.currentTimeMillis() + ".txt";
    PrintWriter p;
    try {
      p = new PrintWriter(name);
    } catch (FileNotFoundException e) {
      p = null;
      e.printStackTrace();
    }
    out = p;
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override
      public void run() {
        out.close();
      }
    }));
  }


  public static void Log(String... xs) {
    if (out != null && log) {
      out.write(Arrays.toString(xs));
    }
  }

  public static void Info(String... xs) {
    if (info) {
      System.out.println(Arrays.toString(xs));
    }
    Log(xs);
  }

  public static void Debug(String... xs) {
    if (debug) {
      System.out.println(Arrays.toString(xs));
    }
    Log(xs);
  }

  public static void Error(String... xs) {
    if (error) {
      System.out.println(Arrays.toString(xs));
    }
    Log(xs);
  }
}
