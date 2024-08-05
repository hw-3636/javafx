package com.itgroup.application;

import com.itgroup.utility.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GetSelectedItem extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = Utility.FXML_PATH + "GetSelectedItem.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));

        Parent container = fxmlLoader.load();  //승급
        Scene scene = new Scene(container);

        String myStyle = getClass().getResource(Utility.CSS_PATH + "LoginTest.css").toString();
        scene.getStylesheets().add(myStyle);

        stage.setTitle("콤보박스 테스트");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
