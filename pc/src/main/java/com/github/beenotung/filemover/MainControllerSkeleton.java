package com.github.beenotung.filemover; /**
 * Sample Skeleton for 'layout_main.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public abstract class MainControllerSkeleton {
  @FXML // ResourceBundle that was given to the FXMLLoader
  protected ResourceBundle resources;

  @FXML // URL location of the FXML file that was given to the FXMLLoader
  protected URL location;

  @FXML // fx:id="mobile_address_textfield"
  protected TextField mobile_address_textfield; // Value injected by FXMLLoader

  @FXML
  void enter_mobile_address(ActionEvent event) {

  }

  @FXML
    // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert mobile_address_textfield != null : "fx:id=\"mobile_address_textfield\" was not injected: check your FXML file 'layout_main.fxml'.";
    customInit();
  }

  abstract void customInit();
}
