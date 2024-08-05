package com.itgroup.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Layout02Controller implements Initializable {
    public void buttonClicked(ActionEvent event) {
        System.out.println("버튼을 누릅니다.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
