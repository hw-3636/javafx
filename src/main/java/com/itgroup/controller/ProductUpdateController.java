package com.itgroup.controller;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;
import com.itgroup.utility.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ProductUpdateController implements Initializable {

    @FXML
    private TextField fxmlName, fxmlCompany, fxmlImage01, fxmlImage02, fxmlImage03, fxmlStock, fxmlPrice, fxmlContents, fxmlPoint, fxmlProductNumber;
    @FXML
    private ComboBox<String> fxmlCategory;
    @FXML
    private DatePicker fxmlInputDate;

    private Product oldBean = null;  //수정될 행의 정보
    private ProductDao dao = new ProductDao();
    Product newBean = null ; //데이터베이스에 수정할 Bean 객체

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fxmlCategory.getItems().setAll(Utility.getCategoryKorName());
    }

    public void setBean(Product bean) {
        this.oldBean = bean;
        fillPreviousData();
        //데이터베이스의 primary key 에 해당하는 목록 지우기
        //fxmlProductNumber.setVisible(false);
    }

    private void fillPreviousData() {
        //텍스트 필드에는 숫자가 들어갈 수 없기 때문에 숫자 필드(fxmlProductNumber, fxmlStock, fxmlPrice, fxmlPoint)는 문자열로 치환
        fxmlProductNumber.setText(String.valueOf(this.oldBean.getProductNum()));
        fxmlName.setText(this.oldBean.getName());
        fxmlCompany.setText(this.oldBean.getCompany());
        fxmlImage01.setText(this.oldBean.getImage01());
        fxmlImage02.setText(this.oldBean.getImage02());
        fxmlImage03.setText(this.oldBean.getImage03());
        fxmlStock.setText(String.valueOf(this.oldBean.getStock()));
        fxmlPrice.setText(String.valueOf(this.oldBean.getPrice()));

        //카테고리 데이터
        String category = this.oldBean.getCategory();  //영문으로 되어있음
        //DB에서 읽어온 영문 카테고리 이름을 한글로 변경
        fxmlCategory.setValue(Utility.getCategoryName(category));

        fxmlContents.setText(this.oldBean.getContents());
        fxmlPoint.setText(String.valueOf(this.oldBean.getPoint()));

        //날짜 데이터
        String inputDate = this.oldBean.getInputDate();
        if (inputDate == null || inputDate.equals("null") || inputDate.isEmpty()) {
            String[] message = new String[]{"상품 수정: 입고 일자", "일자를 선택해주세요.", "상품 수정 실패"} ;
            Utility.showAlert(Alert.AlertType.ERROR, message);
        }
        fxmlInputDate.setValue(Utility.getDatePicker(inputDate));
    }

//    public void fillData(Product bean) {
//        //텍스트 필드에는 숫자가 들어갈 수 없기 때문에 숫자 필드(fxmlProductNumber, fxmlStock, fxmlPrice, fxmlPoint)는 문자열로 치환
//        fxmlProductNumber.setEditable(false);
//
//        fxmlProductNumber.setText(String.valueOf(bean.getProductNum()));
//        fxmlName.setText(bean.getName());
//        fxmlCompany.setText(bean.getCompany());
//        fxmlImage01.setText(bean.getImage01());
//        fxmlImage02.setText(bean.getImage02());
//        fxmlImage03.setText(bean.getImage03());
//        fxmlStock.setText(String.valueOf(bean.getStock()));
//        fxmlPrice.setText(String.valueOf(bean.getPrice()));
//
//        //카테고리 데이터
//        String category = bean.getCategory();  //영문으로 되어있음
//        //DB에서 읽어온 영문 카테고리 이름을 한글로 변경
//        fxmlCategory.setValue(Utility.getCategoryName(category));
//
//        fxmlContents.setText(String.valueOf(bean.getContents()));
//        fxmlPoint.setText(String.valueOf(bean.getPoint()));
//
//        //날짜 데이터
//        String inputDate = bean.getInputDate();
//        if (inputDate == null || inputDate.equals("null") || inputDate.equals("")) {
//            String[] message = new String[]{"상품 수정: 입고 일자", "일자를 선택해주세요.", "상품 수정 실패"} ;
//            Utility.showAlert(Alert.AlertType.ERROR, message);
//        }
//        fxmlInputDate.setValue(Utility.getDatePicker(inputDate));
//    }

    public void onProductUpdate(ActionEvent actionEvent) {
        //입력된 데이터의 유효성 검사 진행
        boolean validation = validationCheck();

        //사용자가 변경한 내역을 데이터베이스에 업데이트
        if (validation) {
            int cnt = -1 ; // 작업 실패
            cnt = dao.updateData(this.newBean);
            if(cnt == -1){
                String[] message = new String[]{"상품 수정 오류", "상품 수정 실패", "상품 수정을 실패하였습니다."} ;
                Utility.showAlert(Alert.AlertType.ERROR, message);
            }else{
                Node source = (Node)actionEvent.getSource();  //Object 클래스의 자식인 Node 클래스로 강등
                Stage stage = (Stage)source.getScene().getWindow();  //Window 클래스의 자식인 Stage 클래스로 강등
                stage.close();
                System.out.println("업데이트 성공");
            }
        }else{
            System.out.println("유효성 검사 오류 - 수정 실패");
        }
    }

    private boolean validationCheck() {
        //유효성 검사를 통과하면 true

        String[] message = null;

        int productNumber =Integer.valueOf(fxmlProductNumber.getText().trim());

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
                message = new String[]{"유효성 검사 : 이미지02", "확장자 점검", "이미지의 확장자는 '.jpg' 또는 '.png' 이하이어야 합니다."} ;
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        }

        String image03 = null ;
        if(fxmlImage03.getText() != null && fxmlImage03.getText().isEmpty()){
            image03 = fxmlImage03.getText().trim();

            bool = image03.endsWith(".jpg") || image03.endsWith(".png") ;
            if(!bool){
                message = new String[]{"유효성 검사 : 이미지03", "확장자 점검", "이미지의 확장자는 '.jpg' 또는 '.png' 이하이어야 합니다."} ;
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
//        if (selectedIndex == 0) {
//            message = new String[]{"유효성 검사: 카테고리", "카테고리 미선택", "원하는 카테고리를 반드시 선택해 주세요"};
//            Utility.showAlert(Alert.AlertType.WARNING, message);
//            return false;
//        }else{
//            category = fxmlCategory.getSelectionModel().getSelectedItem();
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
        this.newBean = new Product() ;
        newBean.setProductNum(productNumber); // primary key - 이 상품 번호를 근거로 수정됨
        newBean.setName(name);
        newBean.setCompany(company);
        newBean.setImage01(image01);
        newBean.setImage02(image02);
        newBean.setImage03(image03);
        newBean.setStock(stock);
        newBean.setPrice(price);
        newBean.setCategory(Utility.getCategoryEngName(category));
        newBean.setContents(contents);
        newBean.setPoint(point);
        newBean.setInputDate(inputDate);

        return true;
    }
}
