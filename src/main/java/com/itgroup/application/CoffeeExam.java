package com.itgroup.application;

import com.itgroup.utility.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CoffeeExam extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = Utility.FXML_PATH + "CoffeeExam.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));

        Parent container = fxmlLoader.load();  //승급
        Scene scene = new Scene(container);

        String myStyle = getClass().getResource(Utility.CSS_PATH + "coffeeStyle.css").toString();
        scene.getStylesheets().add(myStyle); //스타일링 파일 지정

        stage.setTitle("coffee Exam");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
