package com.itgroup.controller;

import com.itgroup.utility.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckBoxTextControllerPractice implements Initializable {
    @FXML private CheckBox childCheckBox, appleCheckBox, chocolateCakeCheckBox;
    @FXML private ImageView childImage, appleImage, chocolateCakeImage;

    private String url = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 초기 설정: 체크박스를 기본으로 선택한 상태로 설정
        childCheckBox.setSelected(true);
        appleCheckBox.setSelected(true);
        chocolateCakeCheckBox.setSelected(true);

        // 초기 이미지 설정
        childImage.setImage(getNewImage("child01.jpg"));
        appleImage.setImage(getNewImage("apple.jpg"));
        chocolateCakeImage.setImage(getNewImage("chocolate_cake_01.png"));
    }

    public void CheckImageShow(ActionEvent actionEvent) {
        CheckBox checkBox = (CheckBox) actionEvent.getSource();  //강등

//        String defaultImage = "apple.jpg";  //디폴트 이미지를 따로 지정할 때에 사용하면 됨. 아래 코딩은 디폴트 이미지 없이 빈 화면을 출력하기 위해 null 을 부여
        String imageName = null;

        if (checkBox.getText().equals("웃는아이")) {
            imageName = checkBox.isSelected() ? "child01.jpg" : null;
            childImage.setImage(getNewImage(imageName));
        } else if (checkBox.getText().equals("사과")) {
            imageName = checkBox.isSelected() ? "apple.jpg" : null;
            appleImage.setImage(getNewImage(imageName));
        } else if (checkBox.getText().equals("초코케이크")) {
            imageName = checkBox.isSelected() ? "chocolate_cake_01.png" : null;
            chocolateCakeImage.setImage(getNewImage(imageName));
        }
    }

    private Image getNewImage(String imageName) {
        //문자열 이용해 이미지 객체를 구해주는 메서드
        if (imageName == null) {
            return null;  // 이미지가 없는 경우 null 반환
        } else {
            imageName = Utility.IMAGE_PATH + imageName;
            url = getClass().getResource(imageName).toString();
            return new Image(url);
        }
    }
}
