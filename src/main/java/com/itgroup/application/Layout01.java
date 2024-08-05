package com.itgroup.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Layout01 extends Application {
    // GitHub test text
    // 수정 및 변동 시 특정 파일만 변경하여 푸쉬해보기 V1
    // 원본 수정하면 다운로드 받을 때 어떻게 되는가? V3
    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/com/itgroup/fxml/" + "Layout01.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));

        Parent container = fxmlLoader.load();
        Scene scene = new Scene(container);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
