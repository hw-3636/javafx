package com.itgroup.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ButtonEventController implements Initializable {

    @FXML private TextArea textArea;  //fxml 에서 지정한 fx:id 와 같은 이름으로 지정해야 함(ex: <TextArea fx:id="textArea">)
    @FXML private Button btnOK;
    //fxml 에서 지정한 fx:id 와 같은 이름으로 지정해야 함(ex: <Button fx:id="btnOK">)
    @FXML private Button btnCancel;
    //fxml 에서 지정한 fx:id 와 같은 이름으로 지정해야 함(ex: <Button fx:id="btnCancel">)

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnOKAction (ActionEvent event) {
        String value = btnOK.getText();
//        textArea.setText(value);  //기존 내용 덮어쓰기
        textArea.appendText(value + "\n");  //기존 내용에 추가하기
    }

    public void btnCancelAction(ActionEvent event) {
        String value = btnCancel.getText();
//        textArea.setText(value);  //기존 내용 덮어쓰기
        textArea.appendText(value + "\n");  //기존 내용에 추가하기

    }
}
