package com.itgroup.utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Utility {
    public static final String FXML_PATH = "/com/itgroup/fxml/"; //fxml 파일이 있는 경로
    public static final String IMAGE_PATH = "/com/itgroup/images/"; //이미지 파일이 있는 경로
    public static final String CSS_PATH = "/com/itgroup/css/"; //CSS 파일이 있는 경로
    //D:\Myjava\javaFxExam\src\main\java 는 자동으로 상대참조가 된다...고 선생님이 알려줌... 이해는 안 됨
    public static final String DATA_PATH = "\\src\\main\\java\\com\\itgroup\\data\\"; //데이터베이스의 텍스트 파일이 있는 경로

    private static Map<String, String> categoryMap = new HashMap<>(){
        //사용자가 카테고리 선택 시 '한글'을 보고 선택하므로 key 에 한글 이름, value 에 영문 이름
        //맵 자료 구조는 value 를 이용하여 key 검색 불가, key 로 value 검색만 가능
        {
            put("음료수","beverage");
            put("빵","bread");
            put("마카롱","macaron");
            put("케이크","cake");
        }
    };

    public static String getCategoryEngName(String category) {
        return categoryMap.get(category);
    }

    public static Set<String> getCategoryKorName() {
        return categoryMap.keySet();
    }
    private static Map<String, String> categoryEngMap = new HashMap<>(){
        //사용자가 카테고리 선택 시 '한글'을 보고 선택하므로 key 에 한글 이름, value 에 영문 이름
        //맵 자료 구조는 value 를 이용하여 key 검색 불가, key 로 value 검색만 가능
        {
            put("beverage","음료수");
            put("bread","빵");
            put("macaron","마카롱");
            put("cake","케이크");
        }
    };

    public static String getCategoryName(String category) {
        return categoryEngMap.get(category);
    }

    public static void showAlert(Alert.AlertType alertType, String[] message) {
        //단순 메세지 박스를 보여주기 위한 유틸리티 메서드
        Alert alert =new Alert(alertType);
        alert.setTitle(message[0]);
        alert.setHeaderText(message[1]);
        alert.setContentText(message[2]);
        alert.showAndWait();
    }

    public static LocalDate getDatePicker(String inputDate) {
        //문자열을 LocalDate 타입으로 변환하여 반환
        //회원 가입 일자, 게시물 작성 일자, 상품 등록 일자 등에 사용 가능

//        int year = Integer.valueOf(inputDate.substring(0, 4));
//        int month = Integer.valueOf(inputDate.substring(5, 7));
//        int day = Integer.valueOf(inputDate.substring(8));
//        return LocalDate.of(year, month, day);

//        (개인)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(inputDate, formatter);
        return date;
//        (현지님 코딩) - 데이터베이스에 날짜 형식 저장 시 사용할 메서드
//        product.setInputdate(dpInputdate.getValue().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));


    }


    /*public static Parent getFxmlParent(String fxmlFileName) {
        //입력된 fxml 파일 이름을 이용, fxml 최상위 컨테이너 반환
        Parent parent = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getCategoryName())
        return parent;

    }*/
}
