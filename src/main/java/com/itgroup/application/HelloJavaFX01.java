package com.itgroup.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class HelloJavaFX01 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        HBox container = new HBox();  //수평박스

        //Insets 클래스: 컨테이너 내부의 요소와 경계 사이의 간격(여백)을 정의해주는 클래스
        container.setPadding(new Insets(40));
        //스페이싱: 버튼 사이 간격을 띄워주는 것
        container.setSpacing(10);

        //창에 색상 입히기 (CSS 문법을 따른다.)
        //-fx-background-color:blue;  백그라운드 색을 파란색으로 지정하기
        //-fx-opacity:0.3;  투명도를 0.3으로 설정하기
        String myStyle = "-fx-background-color:blue;-fx-opacity:0.3;";
        container.setStyle(myStyle);

        // 글자 입력이 가능한 한줄짜리 입력 상자
        TextField textField = new TextField();
        textField.setPrefWidth(200);

        Button button = new Button();
        button.setText("확인");
        button.setPrefWidth(60);

        button.setOnAction((event)->{
            System.out.println(event.toString());
            String text = textField.getText();
            System.out.println(text + "...hello world");
            Platform.exit();
        });


        container.getChildren().add(textField);
        container.getChildren().add(button);

        Scene scene = new Scene(container, 330, 120);

        stage.setScene(scene);
        stage.setTitle("레이아웃 01");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
