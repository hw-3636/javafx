package com.itgroup.application;

import com.itgroup.utility.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = Utility.FXML_PATH + "LoginTest.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));

        Parent container = fxmlLoader.load();  //승급
        Scene scene = new Scene(container);

        String myStyle = getClass().getResource(Utility.CSS_PATH + "LoginTest.css").toString();
        //글자 크기 지정, resources - com.itgroup - css - LoginTest.css 파일에 라벨 크기 지정.
        //fxml 파일에서 직접 지정(인라인 스타일 지정)하지 않고도 외부에서도 서식을 지정(외부 CSS 스타일 지정)할 수 있음을 보여줌.
        //인라인 스타일 지정 방식은 같은 속성을 여러 번 정의해야 하지만 외부 CSS 스타일 지정 방식은 한 번만 정의해둔다면 앞으로 언제든지 불러와 같은 서식을 재사용할 수 있음.
        scene.getStylesheets().add(myStyle);  //스타일링 파일 지정하기

        stage.setTitle("로그인 테스트 프로그래밍");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
