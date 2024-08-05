package com.itgroup.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class CaptionRotateController implements Initializable {
    @FXML private TextArea textArea;
    @FXML private Button myButton01;
    @FXML private Button myButton02;
    @FXML private Button myButton03;
    @FXML private Button clearAndRotation;

    public void myButtonAction(ActionEvent actionEvent) { //actionEvent 는 이벤트를 발생시킨 객체
        Button clickdeButton = (Button) actionEvent.getSource();
        //getSource 이벤트를 발생시킨 출처(어떤 버튼인지)를 가져오는 메서드, 반환타입 Object 이므로 강등 필요
        textArea.appendText(clickdeButton.getText() + "\n");  //기존 내용에 추가하기
    }

    public void clearAndRotationText() {
        textArea.clear();
        //textArea.setText(""); 으로 해도 됨
        String swap = myButton01.getText();
        myButton01.setText(myButton02.getText());
        myButton02.setText(myButton03.getText());
        myButton03.setText(swap);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myButton01.setOnAction(event -> myButtonAction(event));
        myButton02.setOnAction(event -> myButtonAction(event));
        myButton03.setOnAction(event -> myButtonAction(event));
    }
}
