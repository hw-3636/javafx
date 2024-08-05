package com.itgroup.controller;

import com.itgroup.bean.MyObject;
import com.itgroup.utility.Utility;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewControlController01 implements Initializable {
    @FXML private ListView listView;
    @FXML private TableView<MyObject> tableView;
    @FXML private ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> dataList = FXCollections.observableArrayList("어린이", "티아라", "동물", "연예인");
        listView.setItems(dataList);

        //테이블 뷰의 각 컬럼의 값을 MyObject 객체와 연결되도록 property 로 세팅
        //0번째 칼럼은 MyObject 클래스의 name 변수이고, 1번째 컬럼은 MyObject 클래스의 image 변수
        TableColumn tcName = tableView.getColumns().get(0);
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn tcImage = tableView.getColumns().get(1);
        tcImage.setCellValueFactory(new PropertyValueFactory<>("image"));


        //자바의 listener 은 인터페이스. 인터페이스는 객체 생성이 되지 않지만, 추상메서드를 구현한다면 가능하다.
        //(개인)자바의 listener 은 특정 이벤트가 발생했을 때 실행되는 코드를 담고 있는 객체!!
        //
        ChangeListener<Number> listListener =new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                String message = "oldValue: " + oldValue + ", newValue: " + newValue;
                System.out.println(message);

                int menu = newValue.intValue();
                System.out.println("선택한 인덱스: " + menu);

                ObservableList tableData = null;  //테이블 뷰에 넣을 데이터 정보

                switch (menu) {
                    case 0 :  //어린이
                        tableData = FXCollections.observableArrayList(
                                new MyObject("어린이01", "child01.jpg"),
                                new MyObject("어린이02", "child03.jpg"),
                                new MyObject("어린이03", "child04.jpg"),
                                new MyObject("어린이04", "child03.jpg")
                        );
                        break;
                    case 1 :  //티아라
                        tableData = FXCollections.observableArrayList(
                                new MyObject("티아라01", "tara01.jpg"),
                                new MyObject("티아라02", "tara02.jpg"),
                                new MyObject("티아라03", "tara03.jpg"),
                                new MyObject("티아라04", "tara04.jpg")
                        );
                        break;
                    case 2 :  //동물
                        tableData = FXCollections.observableArrayList(
                                new MyObject("동물01", "animal01.jpg"),
                                new MyObject("동물02", "animal02.jpg"),
                                new MyObject("동물03", "animal03.jpg"),
                                new MyObject("동물04", "animal04.jpg")
                        );
                        break;
                    case 3 :  //연예인
                        tableData = FXCollections.observableArrayList(
                                new MyObject("연예인01", "image01.jpg"),
                                new MyObject("연예인02", "image02.jpg"),
                                new MyObject("연예인03", "image03.jpg"),
                                new MyObject("연예인04", "image04.jpg"),
                                new MyObject("연예인05", "image05.jpg"),
                                new MyObject("연예인06", "image06.jpg")
                        );
                        break;
                } //end switch
                if (tableData != null) {
                    tableView.setItems(tableData);
                }
            }
        };

        //리스트 뷰의 색인 정보가 변경(Change)되었을 떄 listListener 가 동작하도록 한다
        listView.getSelectionModel().selectedIndexProperty().addListener(listListener);

        ChangeListener<MyObject> tableListener = new ChangeListener<MyObject>() {  //익멍 클래스
            @Override
            public void changed(ObservableValue<? extends MyObject> observableValue, MyObject oldValue, MyObject newValue) {
                if (newValue != null) {
                    String imageFile = Utility.IMAGE_PATH + newValue.getImage();
                    System.out.println("이미지 파일 경로: " + imageFile);

                    Image someImage = new Image(getClass().getResource(imageFile).toString());
                    imageView.setImage(someImage);
                }
            }
        };

        tableView.getSelectionModel().selectedItemProperty().addListener(tableListener);
    }

    public void handleButtonAction(ActionEvent actionEvent) {  //이벤트 핸들러(버튼이 눌렸을 때 수행될 메서드) 생성
        Object item = listView.getSelectionModel().getSelectedItem();
        MyObject bean = tableView.getSelectionModel().getSelectedItem();

        if (item == null && bean == null) {
            String[] message = {"테이블과 리스트 선택 여부", "항목 미체크", "테이블 뷰와 리스트 뷰의 항목을 모두 선택해 주세요."};
            Utility.showAlert(Alert.AlertType.INFORMATION,message);
        } else if (item ==null){
            String[] message = {"리스트 선택 여부", "항목 미체크", "리스트 뷰에서 항목을 선택해 주세요."};
            Utility.showAlert(Alert.AlertType.INFORMATION,message);
        } else if (bean == null) {
            String[] message = {"테이블 선택 여부", "항목 미체크", "테이블 뷰에서 항목을 선택해 주세요."};
            Utility.showAlert(Alert.AlertType.INFORMATION,message);
        }
    }

    public void handleCancelAction(ActionEvent actionEvent) {  //이벤트 핸들러 생성
        System.out.println("종료합니다");
        Platform.exit();
    }


}
