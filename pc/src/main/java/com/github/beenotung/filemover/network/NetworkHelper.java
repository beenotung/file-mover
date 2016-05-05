package com.github.beenotung.filemover.network;

import com.github.beenotung.utils.Lang;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Created by beenotung on 5/5/16.
 */
public class NetworkHelper {
  private static Socket lastSocket;
  private static ObjectInputStream in;
  private static ObjectOutputStream out;

  public static boolean send(NetworkObject o) {
    try {
      out.writeObject(o);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  private static HashMap<Class<? extends NetworkObject>, Lang.Consumer<NetworkObject>> listeners = new HashMap<>();

  public static void register(Class<? extends NetworkObject> c, Lang.Consumer<NetworkObject> f) {
    listeners.put(c, f);
  }

  public static boolean handle(Socket socket) {
    remove(lastSocket);
    lastSocket = socket;
    try {
      in = new ObjectInputStream(socket.getInputStream());
      out = new ObjectOutputStream(socket.getOutputStream());
      new Thread(new Runnable() {
        @Override
        public void run() {
          while (true) {
            try {
              Object o = in.readObject();
              NetworkObject no = (NetworkObject) o;
              Class<? extends NetworkObject> c = no.getClass();
              Lang.Consumer<NetworkObject> f = listeners.get(c);
              f.Apply(no);
            } catch (IOException | ClassNotFoundException | ClassCastException e) {
              e.printStackTrace();
              remove(socket);
              break;
            }
          }
        }
      }).start();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      remove(socket);
      return false;
    }
  }

  private static void remove(Socket socket) {
    if (socket == null) return;
    try {
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
