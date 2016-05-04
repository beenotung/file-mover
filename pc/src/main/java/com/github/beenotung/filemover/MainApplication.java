package com.github.beenotung.filemover;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by beenotung on 5/4/16.
 */
public class MainApplication extends Application {
  public static Stage stage;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    stage = primaryStage;
    Parent root = FXMLLoader.load(getClass().getResource("layout_main.fxml"));
    Scene scene = new Scene(root);
    stage.setTitle("File Mover");
    stage.setScene(scene);
    stage.show();
  }
}
