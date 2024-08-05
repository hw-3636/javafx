package com.itgroup.controller;

import com.itgroup.bean.Item;
import com.itgroup.bean.People;
import com.itgroup.utility.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class SelectedItemEventController implements Initializable {
    @FXML
    private ComboBox comboBox;
    @FXML
    private Label label;
    @FXML
    private ImageView imageView;

    //모든 텍스트파일을 저장할 리스트 컬렉션
    private List<People> ComboBoxList = new ArrayList<>();
    //사람 이름 목록을 저장할 List 컬렉션 -> 콤보박스에 사용
    private Set<String> nameSet = new HashSet<>();
    //사람 이름과 이미지 정보를 저장하고 있는 Map 컬렉션
    private Map<String, String> imageMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDataFromFile();
        ObservableList<String> fxmlNameSet = FXCollections.observableArrayList(nameSet);
        comboBox.setItems(fxmlNameSet);

    }

    private void fillDataFromFile() {
        String pathName = System.getProperty("user.dir");  //현재 작업 중인 디렉토리 정보
        pathName += Utility.DATA_PATH;

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(new File(pathName, "SelectedItemEventList")));

            String oneLine = null;
            while ((oneLine = br.readLine()) != null) {
                System.out.println(oneLine);
                String[] people = oneLine.split(",");
                ComboBoxList.add(new People(people[0], people[1]));
                nameSet.add(people[0]);
            }
            System.out.println("리스트 품목: ");
            System.out.println(ComboBoxList);
            System.out.println("사람 목록: ");
            System.out.println(nameSet);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void selectComboBox(ActionEvent actionEvent) {
        String selectedName = (String) comboBox.getSelectionModel().getSelectedItem();
        System.out.println("선택된 이름: " + selectedName);

        List<People> filterList = ComboBoxList.stream().filter(people -> selectedName.equals(people.getName())).collect(Collectors.toList());

        for (People people : filterList) {
            imageMap.put(people.getName(), people.getImage());
        }

        String imageName = imageMap.get(selectedName);
        System.out.println("이미지 이름: " + imageName);


        //해당 이미지 보여주기
        if (imageName != null) {
            String imageFile = Utility.IMAGE_PATH + imageName;
            String myUrl = getClass().getResource(imageFile).toString();
            Image image = new Image(myUrl);
            imageView.setImage(image);
        } else {
            imageView.setImage(null);
        }

        int selectedNameIndex = comboBox.getSelectionModel().getSelectedIndex();
        label.setText("선택된 글자(인덱스): " + selectedName + "(" + (selectedNameIndex+1) + ")");
        System.out.println("선택된 글자(인덱스): " + selectedName + "(" + (selectedNameIndex+1) + ")");


    }
}

