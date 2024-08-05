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

public class CheckBoxTestController implements Initializable {
//    @FXML private CheckBox fxmlCheckBeverage01, fxmlCheckBeverage02, fxmlCheckBeverage03;
    @FXML private ImageView fxmlImageView01, fxmlImageView02, fxmlImageView03;

    private String url = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void checkBoxAction(ActionEvent event){
        System.out.println(event);
        CheckBox checkBox = (CheckBox)event.getSource(); // 강등

        String defaultImage = "apple.jpg"; // 디폴트 이미지
        String imageName = null ;

        if(checkBox.getText().equals("브리오슈")){
            imageName = checkBox.isSelected() ? "brioche_01.png" : defaultImage;
            Image image = getNewImage(imageName);
            fxmlImageView01.setImage(image);

        }else if(checkBox.getText().equals("치아바타")){
            imageName = checkBox.isSelected() ? "ciabatta_01.png" : defaultImage;
            Image image = getNewImage(imageName);
            fxmlImageView02.setImage(image);

        }else if(checkBox.getText().equals("크로아상")){
            imageName = checkBox.isSelected() ? "croissant_01.png" : defaultImage;
            Image image = getNewImage(imageName);
            fxmlImageView03.setImage(image);
        }else{

        }
    }

    private Image getNewImage(String imageName) {
        // 문자열을 이용하여 이미지 객체를 구해주는 메소드입니다.
        imageName = Utility.IMAGE_PATH + imageName;
        url = getClass().getResource(imageName).toString();
        return new Image(url);
    }
}
