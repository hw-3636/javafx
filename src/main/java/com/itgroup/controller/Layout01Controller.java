package com.itgroup.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Layout01Controller implements Initializable {

    public void printEnterLine(ActionEvent event) {
        System.out.println("확인 버튼을 눌러 시스템을 종료합니다.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
