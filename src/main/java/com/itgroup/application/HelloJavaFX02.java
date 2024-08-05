package com.itgroup.application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelloJavaFX02 extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        VBox container = new VBox();  //수직박스
//        container.setPrefWidth(350);  //가로
//        container.setPrefHeight(150);  //세로
        container.setPrefSize(350,150);  //가로, 세로 사이즈 지정
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);

        Label label = new Label();  //라벨 생성
        label.setText("Hello! java FX");
        label.setFont(new Font(30));

        Button button = new Button();  //버튼 생성
        button.setText("확인");
        //버튼에 람다식을 이용한 이벤트 처리
        button.setOnAction((event)->{
            //event 객체: 이벤트를 발생시킨 객체
            System.out.println(event.toString());
            String text = label.getText();
            System.out.println(text + "호호호");
            Platform.exit();  //애플리케이션 종료
        });

        container.getChildren().add(label);  //라벨을 컨테이너(보여줄 창)에 담기
        container.getChildren().add(button);  //버튼을 컨테이너(보여줄 창)에 담기

        Scene scene = new Scene(container);  //VBox 객체가 Scene 객체로 승급(Scene - VBox 상속 관계)
        stage.setScene(scene);
        stage.setTitle("First Application");  //타이틀 변경
        stage.show();
    }

    public static void main(String[] args) {
        //launch 메서드 호출 시 자동으로 start 메서드를 실행시켜줌
        launch(args);
    }


}
