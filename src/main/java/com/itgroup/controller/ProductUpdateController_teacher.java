package com.itgroup.controller;

/*import com.itgroup.bean.Product;
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
import java.util.ResourceBundle;

public class ProductUpdateController implements Initializable {
    @FXML private TextField fxmlPnum; // 상품 번호인 이 항목은 숨김 처리 예정입니다.
    @FXML private TextField fxmlName;
    @FXML private TextField fxmlCompany;
    @FXML private TextField fxmlImage01;
    @FXML private TextField fxmlImage02;
    @FXML private TextField fxmlImage03;
    @FXML private TextField fxmlStock;
    @FXML private TextField fxmlPrice;
    @FXML private ComboBox<String> fxmlCategory;
    @FXML private TextField fxmlContents;
    @FXML private TextField fxmlPoint;
    @FXML private DatePicker fxmlInputdate;

    private Product oldBean = null ; // 수정될 행의 정보
    private Product newBean = null ; // 데이터 베이스에 수정할 Bean 객체

    public void setBean(Product bean) {
        this.oldBean = bean ;
        fillPreviousData();

        // 데이터 베이스의 primary key에 해당하는 상품 번호를 숨겨줍니다.
        fxmlPnum.setVisible(false);

    }

    private void fillPreviousData() {
        // 과거에 내가 등록했던 정보들을 해당 컨트롤에 다시 입력해 줍니다.
        fxmlPnum.setText(String.valueOf(this.oldBean.getPnum()));
        fxmlName.setText(this.oldBean.getName());
        fxmlCompany.setText(this.oldBean.getCompany());
        fxmlImage01.setText(this.oldBean.getImage01());
        fxmlImage02.setText(this.oldBean.getImage02());
        fxmlImage03.setText(this.oldBean.getImage03());
        fxmlStock.setText(String.valueOf(this.oldBean.getStock()));
        fxmlPrice.setText(String.valueOf(this.oldBean.getPrice()));

        // DB에서 읽어온 영문 카테고리 이름을 한글로 변경해 줍니다.
        String category = this.oldBean.getCategory(); // 영문으로 되어 있습니다.
        fxmlCategory.setValue(Utility.getCategoryName(category, "key"));

        fxmlContents.setText(this.oldBean.getContents());
        fxmlPoint.setText(String.valueOf(this.oldBean.getPoint()));

        // 입고 일자는 문자열을 LocalDate 타입으로 변환하고, setValue() 메소드로 적용시킵니다.
        String inputDate = this.oldBean.getInputdate() ;
        if(inputDate == null || inputDate.equals("null")){

        }else{
            fxmlInputdate.setValue(Utility.getDatePicker(inputDate));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("수정 할꺼야");
    }

    public void onProductUpdate(ActionEvent event) {
        // 먼저, 유효성 검사를 진행합니다.
        boolean bool = validationCheck();

        // 사용자가 변경한 내역을 데이터베이스에 업데이트 시킵니다.
        if(bool == true){
            ProductDao dao = new ProductDao() ;
            int cnt = -1 ; // -1이면 실패
            cnt = dao.updateData(this.newBean);

            if(cnt == -1){
                System.out.println("수정 실패");

            }else{ // 수정이 되었으므로 창을 닫습니다.
                Node source = (Node)event.getSource();
                Stage stage = (Stage)source.getScene().getWindow();
                stage.close();
            }
        }else{
            System.out.println("유효성 검사를 통과하지 못했습니다.");
        }
    }

    private boolean validationCheck() {
        // 유효성 검사를 통과하면 true가 됩니다.

        // 수정을 위한 핵심 키(primary key)
        int pnum = Integer.valueOf(fxmlPnum.getText().trim());

        String[] message = null ;

        String name = fxmlName.getText().trim();
        if(name.length() <= 2 || name.length() >= 11){
            message = new String[]{"유효성 검사 : 이름", "길이 제한 위배", "이름은 3글자 이상 10글자 이하이어야 합니다."} ;
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        String company = fxmlCompany.getText().trim();
        if(company.length() <= 2 || company.length() >= 16){
            message = new String[]{"유효성 검사 : 제조 회사", "길이 제한 위배", "제조 회사명은 3글자 이상 15글자 이하이어야 합니다."} ;
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        String image01 = fxmlImage01.getText().trim() ;

        if(image01 == null || image01.length() < 5){
            message = new String[]{"유효성 검사 : 이미지01", "필수 입력 체크", "1번 이미지는 필수 입력 사항니다."} ;
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        boolean bool = false;
        // startsWith()와 endsWith()
        bool = image01.endsWith(".jpg") || image01.endsWith(".png") ;
        if(!bool){
            message = new String[]{"유효성 검사 : 이미지01", "확장자 점검", "이미지의 확장자는 '.jpg' 또는 '.png' 이하이어야 합니다."} ;
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        // 주의) 입력 상자 객체는 not null이지만, 데이터 입력이 되어 있지 않는 경우 getText() 메소드는 null을 반환합니다.
//        System.out.println("fxmlImage02 == null");
//        System.out.println(fxmlImage02 == null);
//
//        System.out.println("fxmlImage02.getText() == null");
//        System.out.println(fxmlImage02.getText() == null);

        String image02 = null ;
        if(fxmlImage02.getText() == null){
            image02 = "";
        }

        if(image02.length()==0){ // 사용자가 2번 이미지를 입력하지 않는 경우
            image02 = null ;
        }

        if(image02 != null){
            bool = image02.endsWith(".jpg") || image02.endsWith(".png") ;
            if(!bool){
                message = new String[]{"유효성 검사 : 이미지02", "확장자 점검", "이미지의 확장자는 '.jpg' 또는 '.png' 이하이어야 합니다."} ;
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        }

        String image03 = null ;

        if(fxmlImage03.getText() == null){
            image03 = "";
        }

        if(image03.length() == 0){
            image03 = null ;
        }

        if(image03 != null){
            bool = image03.endsWith(".jpg") || image03.endsWith(".png") ;
            if(!bool){
                message = new String[]{"유효성 검사 : 이미지03", "확장자 점검", "이미지의 확장자는 '.jpg' 또는 '.png' 이하이어야 합니다."} ;
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }
        }
        int stock = 0;
        try{
            String _stock = fxmlStock.getText().trim();
            stock = Integer.valueOf(_stock);

            if(stock < 10 || stock > 100){
                message = new String[]{"유효성 검사 : 재고", "허용 숫자 위반", "재고는 10개이상 100개 이하로 입력해 주세요."} ;
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }

        }catch (NumberFormatException ex){
            ex.printStackTrace();

            message = new String[]{"유효성 검사 : 재고", "무효한 숫자 형식", "올바른 숫자 형식을 입력해 주세요."} ;
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }
        int price = 0 ;
        try{
            String _price = fxmlPrice.getText().trim();
            price = Integer.valueOf(_price);

            if(price < 1000 || price > 10000){
                message = new String[]{"유효성 검사 : 단가", "허용 숫자 위반", "단가는 1,000원이상 10,000원 이하로 입력해 주세요."} ;
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }

        }catch (NumberFormatException ex){
            ex.printStackTrace();

            message = new String[]{"유효성 검사 : 단가", "무효한 숫자 형식", "올바른 숫자 형식을 입력해 주세요."} ;
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        int selectedIndex = fxmlCategory.getSelectionModel().getSelectedIndex();
        String category = null ;

        if(selectedIndex == 0){
            message = new String[]{"유효성 검사 : 카테고리", "카테고리 미선택", "원하시는 카테고리를 반드시 선택해 주세요."} ;
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }else{
            category = fxmlCategory.getSelectionModel().getSelectedItem();
            System.out.println("선택된 항목");
            System.out.println(category);
        }

        String contents = fxmlContents.getText().trim();
        if(contents.length() <= 4 || contents.length() >= 31){
            message = new String[]{"유효성 검사 : 상품 설명", "길이 제한 위배", "상품 설명은 5글자 이상 30글자 이하이어야 합니다."} ;
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }
        int point = 0 ;
        try{
            String _point = fxmlPoint.getText().trim();
            point = Integer.valueOf(_point);

            if(point < 3 || point > 5){
                message = new String[]{"유효성 검사 : 적립포인트", "허용 숫자 위반", "적립포인트는 3점이상 5점 이하로 입력해 주세요."} ;
                Utility.showAlert(Alert.AlertType.WARNING, message);
                return false;
            }

        }catch (NumberFormatException ex){
            ex.printStackTrace();

            message = new String[]{"유효성 검사 : 적립포인트", "무효한 숫자 형식", "올바른 숫자 형식을 입력해 주세요."} ;
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        LocalDate _inputDate = fxmlInputdate.getValue();
        String inputDate = null ;

        if(_inputDate == null){
            message = new String[]{"유효성 검사 : 입고일자", "무효한 날짜 형식", "올바른 입고 일자 형식을 입력해 주세요."} ;
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;

        }else{
            inputDate = _inputDate.toString() ;
            inputDate = inputDate.replace("-", "/");
        }

        // 유효성 검사가 통과된 시점 이후 객체를 생성하도록 합니다.
        this.newBean = new Product() ;

        newBean.setPnum(pnum); // 중요)이 상품 번호를 근거로 수정이 됩니다.
        newBean.setName(name);
        newBean.setCompany(company);
        newBean.setImage01(image01);
        newBean.setImage02(image02);
        newBean.setImage03(image03);
        newBean.setStock(stock);
        newBean.setPrice(price);

        // 사용자가 입력한 key인 한글 카테고리 이름을 value인 영문으로 변환시켜 셋팅합니다.
        newBean.setCategory(Utility.getCategoryName(category, "value"));
        newBean.setContents(contents);
        newBean.setPoint(point);
        newBean.setInputdate(inputDate);

        return true ;
    }


}*/
