package com.itgroup.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ConfirmCounterController implements Initializable {

    @FXML private Button buttonOK;
    @FXML private TextArea textArea;

    int count;
    DecimalFormat df = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        df = new DecimalFormat("00");
    }

    public void btnOKCountAction(ActionEvent event) {
        String value = buttonOK.getText();
        count++;
        String message = value + " 버튼" + df.format(count) + "번 눌러짐";
        textArea.appendText(message+"\n");  //기존 내용에 추가하기


    }
}
