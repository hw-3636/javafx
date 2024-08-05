package com.itgroup.controller;

import com.itgroup.bean.People;
import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;
import com.itgroup.utility.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ProductInsertController implements Initializable {
    @FXML
    private TextField fxmlName, fxmlCompany, fxmlImage01, fxmlImage02, fxmlImage03, fxmlStock, fxmlPrice, fxmlContents, fxmlPoint;
    @FXML
    private ComboBox<String> fxmlCategory;
    @FXML
    private DatePicker fxmlInputDate;

    private ProductDao dao = new ProductDao();
    Product bean = null ; //데이터베이스에 추가될 Bean 객체


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        fxmlCategory.getItems().setAll(dao.selectCategory());
        fxmlCategory.getItems().setAll(Utility.getCategoryKorName());
        //최초 시작 시 콤보 박스의 0번째 항목 선택하기
        //fxmlCategory.getSelectionModel().select(0);
    }

    public void onProductInsert(ActionEvent actionEvent) {
        //기입한 상품 목록을 데이터베이스에 추가
        //actionEvent 객체는 해당 이벤트를 발생시킨 객체
        System.out.println(actionEvent);
        boolean validation = false;
        validation = validationCheck();
        if (!validation) {
            System.out.println("유효성 검사 오류 - 등록 실패");
        }else{
            insertDatabase();
        }

    }

    private int insertDatabase() {
        //1건의 데이터인 bean 을 데이터베이스에 추가
        int cnt = -1 ; // 작업 실패
        cnt = dao.insertData(this.bean);
        if(cnt == -1){
            String[] message = new String[]{"상품 등록 오류", "상품 등록 실패", "상품 등록을 실패하였습니다."} ;
            Utility.showAlert(Alert.AlertType.ERROR, message);
        }
        return cnt;
    }

    private boolean validationCheck() {
        //유효성 검사를 통과하면 true
        String[] message = null;

        String name = fxmlName.getText().trim();
        if (name.length() <= 2 || name.length() >= 11) {
            message = new String[]{"유효성 검사: 상품명", "길이 제한 위배", "이름은 3글자 이상 10글자 이하로 설정해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        String company = fxmlCompany.getText().trim();
        if (company.length() <= 2 || company.length() >= 16) {
            message = new String[]{"유효성 검사: 제조 회사", "길이 제한 위배", "제조 회사명은 3글자 이상 15글자 이하로 설정해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        String image01 = fxmlImage01.getText().trim();
        if (image01 == null || image01.length() < 5) {
            message = new String[]{"유효성 검사: 이미지01", "필수 이미지 누락", "이미지01은 무조건 입력해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }
        boolean bool = false;
        bool = image01.endsWith(".jpg") || image01.endsWith(".png"); // startsWith() 와 endsWIth()
        if (!bool) {
            message = new String[]{"유효성 검사: 이미지01", "확장자 오류", "이미지의 확장자는 ',jpg', 또는 '.jpg' 로 설정해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }


        String image02 = null ;
        if(fxmlImage02.getText() != null && fxmlImage02.getText().isEmpty()){
            image02 = fxmlImage02.getText().trim();

            bool = image02.endsWith(".jpg") || image02.endsWith(".png") ;
            if(!bool){
                message = new String[]{"유효성 검사 : 이미지03", "확장자 점검", "이미지의 확장자가 '.jpg' 또는 '.png' 인지 확인해 주세요"} ;
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        }

        String image03 = null ;
        if(fxmlImage03.getText() != null && fxmlImage03.getText().isEmpty()){
            image03 = fxmlImage03.getText().trim();

            bool = image03.endsWith(".jpg") || image03.endsWith(".png") ;
            if(!bool){
                message = new String[]{"유효성 검사 : 이미지03", "확장자 점검", "이미지의 확장자가 '.jpg' 또는 '.png' 인지 확인해 주세요"} ;
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        }

        //유효성 검사(재고)
        int stock = 0;
        try{
            String _stock = fxmlStock.getText().trim();
            stock = Integer.valueOf(_stock);

            if(stock < 10  || stock > 100){
                message = new String[]{"유효성 검사: 재고", "허용 숫자 범위 위반", "재고는 10개 이상 100개 이하로 설정해 주세요."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        }catch(NumberFormatException e){
            e.printStackTrace();

            message = new String[]{"유효성 검사: 재고", "유효하지 않은 숫자 형식", "단가는 숫자 형식으로 입력해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        //유효성 검사(단가)
        int price=0;
        try{
            String _price = fxmlPrice.getText().trim();
            price = Integer.valueOf(_price);

            if(price < 1000  || price > 10000){
                message = new String[]{"유효성 검사: 단가", "허용 숫자 범위 위반", "단가는 1,000원 이상 10,000원 이하로 설정해 주세요."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        }catch(NumberFormatException e){
            e.printStackTrace();

            message = new String[]{"유효성 검사: 단가", "유효하지 않은 숫자 형식", "단가는 숫자 형식으로 입력해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        String category = fxmlCategory.getSelectionModel().getSelectedItem();
//        int selectedIndex = fxmlCategory.getSelectionModel().getSelectedIndex();
//        String category = null;
//        if (selectedIndex == 0) {
//            message = new String[]{"유효성 검사: 카테고리", "카테고리 미선택", "원하는 카테고리를 반드시 선택해 주세요"};
//            Utility.showAlert(Alert.AlertType.WARNING, message);
//            return false;
//        }else{
//            category = fxmlCategory.getSelectionModel().getSelectedItem();
//            System.out.println("선택된 항목");
//            System.out.println(category);
//        }
        //초기 값을 fxml 에서 넣지 않았기 때문에(개인적으로 자바에서 카테고리 목록 받아옴) 유효성 검사 다른 방식으로 해야 함

        //유효성 검사(상세 설명)
        String contents = fxmlContents.getText().trim();
        if (contents.length() <= 4 || contents.length() >= 31) {
            message = new String[]{"유효성 검사: 상세 설명", "길이 제한 위배", "상품 설명은 5글자 이상 30글자 이하로 설정해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        //유효성 검사(포인트)
        int point = 0;
        try{
            String _point = fxmlPoint.getText().trim();
            point = Integer.valueOf(_point);

            if(point < 3  || point > 5){
                message = new String[]{"유효성 검사: 포인트", "허용 숫자 범위 위반", "포인트는 3점 이상 5점 이하로 설정해 주세요."};
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        }catch(NumberFormatException e){
            e.printStackTrace();

            message = new String[]{"유효성 검사: 단가", "유효하지 않은 숫자 형식", "단가는 숫자 형식으로 입력해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        //유효성 검사(입고 일자)
        LocalDate _inputDate = fxmlInputDate.getValue();
        String inputDate = null ;
        if(_inputDate == null){
            message = new String[]{"유효성 검사 : 입고일자", "무효한 날짜 형식", "올바른 입고 일자 형식을 입력해 주세요."} ;
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;

        }else{
            inputDate = _inputDate.toString() ;
            inputDate = inputDate.replace("-", "/");
        }

        // 유효성 검사가 통과되면 비로소 객체 생성합니다.
        this.bean = new Product() ;
        // bean.setPnum(0); // 시퀀스가 처리해줌
        bean.setName(name);
        bean.setCompany(company);
        bean.setImage01(image01);
        bean.setImage02(image02);
        bean.setImage03(image03);
        bean.setStock(stock);
        bean.setPrice(price);
        bean.setCategory(Utility.getCategoryEngName(category));
        bean.setContents(contents);
        bean.setPoint(point);
        bean.setInputDate(inputDate);

        return true;
    }

    public void validationCheckCondition(int startNumber, int endNumber, String warningMessage) {

    }
}

