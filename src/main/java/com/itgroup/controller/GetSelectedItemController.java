package com.itgroup.controller;

import com.itgroup.bean.Item;
import com.itgroup.utility.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GetSelectedItemController implements Initializable {

    @FXML public ComboBox leftCombo, rightCombo;
    @FXML public ImageView imageView;

    //품목들을 저장할 리스트 컬렉션
    private List<Item> comboList = new ArrayList<>();

    //카테고리 목록을 저장할 Set 컬렉션
    private Set<String> categorySet = new HashSet<String>();

    //상품 이름과 이미지 정보를 저장하고 있는 Map 컬렉션
    private Map<String, String> imageMap = new HashMap<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDataFromFile();  //application 이 실행되자마자 fillDataFromFile() 메서드를 수행하시오.
        ObservableList<String> dataList = FXCollections.observableArrayList(categorySet);  //컬렉션(List, Set 등)과 배열을 fxml 에서 읽어들일 수 있도록 하는 클래스
        leftCombo.setItems(dataList);  //setItems - 배열, 리스트, 세트 등 모두 들어갈 수 있음
    }

    private void fillDataFromFile() {
        //특정 텍스트 파일에서 관련 정보들을 읽어들이기
        String pathName = System.getProperty("user.dir");  //현재 작업 중인 디렉토리 정보
        pathName += Utility.DATA_PATH;

        System.out.println(pathName);
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(new File(pathName, "list.txt")));

            String oneLine = null;
            while ((oneLine = br.readLine()) != null){
                System.out.println(oneLine);
                String[] myItem = oneLine.split("/");
                comboList.add(new Item(myItem[0], myItem[1], myItem[2]));
                categorySet.add(myItem[0]);
            }
            System.out.println("카테고리 목록: ");
            System.out.println(categorySet);
            System.out.println("리스트 품목: ");
            System.out.println(comboList);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(br!=null){br.close();}
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void selectLeft(ActionEvent actionEvent) {
        //좌측 콤보 박스를 선택하였습니다.
        //선택된 카테고리의 하위 품목들을 오른쪽 콤보 박스에 추가합니다.
        String selectedCategory = (String) leftCombo.getSelectionModel().getSelectedItem();
        System.out.println("선택된 카테고리: " + selectedCategory);

        List<Item> filterItems = comboList.stream().filter(item -> selectedCategory.equals(item.getCategory())).collect(Collectors.toList());

        System.out.println(filterItems);

        //우측 콤보 박스 rightCombo 에 항목을 추가합니다.
        rightCombo.getItems().clear();
        for (Item one : filterItems) {
            rightCombo.getItems().add(one.getName());

            //해당 상품 이름에 해당하는 이미지 정보 추가
            imageMap.put(one.getName(), one.getImage());

//            if(filterItem.getName().equals("브리오슈")) {
//                imageMap.put(filterItem.getName(), "brioche_01.png");
//            }else if(filterItem.getName().equals("치아바타")){
//                imageMap.put(filterItem.getName(), "ciabatta_01.png");
//            }else if(filterItem.getName().equals("크로와상")){
//                imageMap.put(filterItem.getName(), "croissant_02.png");
//            }else if(filterItem.getName().equals("아메리카노")){
//                imageMap.put(filterItem.getName(), "americano01.png");
//            }else if(filterItem.getName().equals("카푸치노")){
//                imageMap.put(filterItem.getName(), "cappuccino01.png");
//            }else if(filterItem.getName().equals("바닐라라떼")){
//                imageMap.put(filterItem.getName(), "vanilla_latte_01.png");
//            }else if(filterItem.getName().equals("레드와인")){
//                imageMap.put(filterItem.getName(), "redwine01.png");
//            }else if(filterItem.getName().equals("화이트와인")) {
//                imageMap.put(filterItem.getName(), "whitewine01.png");
//            }
        }

        //우측 콤보 박스에서 사용할 이벤트
        rightCombo.setOnAction(actionEvent1 -> {
            String selectedItem = (String) rightCombo.getSelectionModel().getSelectedItem();
            String imageName = imageMap.get(selectedItem);
            System.out.println(imageMap);

            System.out.println("selected Item: " + selectedItem);
            System.out.println("Image: " + imageName);

            //해당 이미지를 보여주세요.
            if (imageName != null) {
                String imageFile = Utility.IMAGE_PATH + imageName;
                String myUrl = getClass().getResource(imageFile).toString();
                Image image = new Image(myUrl);
                imageView.setImage(image);
            }else{
                imageView.setImage(null);
            }

        });
    }
}
