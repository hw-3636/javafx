package com.itgroup.controller;

import com.itgroup.utility.Utility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CheckBoxRadioButtonController implements Initializable {

    @FXML
    private CheckBox changeImage01, changeImage02;  //@FXML 어노테이션: FXML 문서에 changeImage01 라는 이름으로 지정한 CheckBox 있으면 new 할 필요 없이 자동 객체 생성을 해준다.
    @FXML
    private ImageView checkImageView;
    @FXML
    private ToggleGroup breadGroup;
//    @FXML
//    private RadioButton breadRadio01, breadRadio02, breadRadio03;
    @FXML
    private ImageView radioImageView;
    @FXML
    private Slider mySlider;
    @FXML
    private Button fxmlButtonExit;

    public void handleChkAction(ActionEvent actionEvent) {
        //체크박스의 선택 여부에 따라서 다른 이미지로 교체
        String name = "";

        if (changeImage01.isSelected() && changeImage02.isSelected()) {
            name = Utility.IMAGE_PATH + "geek-glasses-hair.gif";

        } else if (changeImage01.isSelected()) {
            name = Utility.IMAGE_PATH + "geek-glasses.gif";

        } else if (changeImage02.isSelected()) {
            name = Utility.IMAGE_PATH + "geek-hair.gif";

        } else {
            name = Utility.IMAGE_PATH + "geek.gif";
        }


        System.out.println("이미지 파일 이름 : ");
        System.out.println(name);

        Image image = new Image(getClass().getResource(name).toString());
        checkImageView.setImage(image);
    }

    public void handleButtonExit(ActionEvent event) {
        //종료 버튼 클릭 시 종료 여부를 확인받고, 종료 및 종료 취소
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("종료 확인");

        alert.setHeaderText("지금 종료합니까?");
        alert.setContentText("프로그램을 종료하시겠습니까?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("OK 버튼을 눌러서 프로그램을 종료합니다.");
        }else{
            System.out.println("프로그램 종료 Cancel");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {  //생성자처럼 무언가를 초기화하는 메서드
        //슬라이더의 값을 변경, 글자의 크기도 변경

        //속성감지
        ChangeListener<Number> sliderListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNumber, Number newNumber) {
                //observableValue: 값 변경된 객체
                //oldNumber: 이전 값
                //newNumber: 새로운 값
                String message = "이전 값: %.3f, 새로운 값: &.3f\n";
                System.out.printf(message, oldNumber,newNumber);
//                System.out.println("이전 값: " + oldNumber + ", 새로운 값:" + newNumber);

                fxmlButtonExit.setFont(new Font(newNumber.doubleValue()));
            }
        };
        mySlider.valueProperty().addListener(sliderListener);


        //라디오 버튼이 토글되었을 때 반응하는 리스너입니다.
        //속성 감지
        ChangeListener<Toggle> radioListener = new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
                //newValue: 내가 선택한 라디오 버튼 객체
                if (newValue != null) {
                    String imageFile = Utility.IMAGE_PATH + newValue.getUserData().toString();
                    System.out.println("이미지 파일 이름: " + imageFile);

                    //FXML 파일의 userData 속성을 참조해라
                    String newImage = getClass().getResource(imageFile).toString();
                    Image targetImage = new Image(newImage);
                    radioImageView.setImage(targetImage);
                }
            }
        };
        breadGroup.selectedToggleProperty().addListener(radioListener);
    }
}
