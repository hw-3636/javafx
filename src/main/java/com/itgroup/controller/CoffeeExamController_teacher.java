package com.itgroup.controller;

/*
import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;
import com.itgroup.utility.Paging;
import com.itgroup.utility.Utility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CoffeeExamController implements Initializable {
    private ProductDao dao = null ;

    @FXML private ImageView imageView ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dao = new ProductDao() ;

        setTableColumns();
        setPagination(0); // 최초 시작시 1페이지를 눌러 주세요.

        // 테이블 뷰의 1행을 클릭하면, 우측에 이미지를 보여 줍니다.
        ChangeListener<Product> tableListener = new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observableValue, Product oldValue, Product newValue) {
                if(newValue != null){
                    System.out.println("상품 정보");
                    System.out.println(newValue);

                    String imageFile = ""; // 해당 이미지의 fullPath + 이미지 이름

                    if(newValue.getImage01() != null){
                        imageFile = Utility.IMAGE_PATH + newValue.getImage01().trim() ;
                    }else{
                        imageFile = Utility.IMAGE_PATH + "noimage.jpg" ;
                    }

                    Image someImage = null ; // 이미지 객체
                    if(getClass().getResource(imageFile) == null){
                        imageView.setImage(null);
                    }else{
                        someImage = new Image(getClass().getResource(imageFile).toString());
                        System.out.println(imageView == null);
                        imageView.setImage(someImage);
                    }
                }
            }
        } ;

        productTable.getSelectionModel().selectedItemProperty().addListener(tableListener);
    }

    @FXML private Pagination pagination ;

    private void setPagination(int pageIndex) {
        // 페이징 관련 설정을 합니다.
        pagination.setCurrentPageIndex(pageIndex);
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(Integer pageIndex) {
        // 각 페이지의 Pagination을 동적으로 생성해주는 공장(Factory) 역할을 합니다.
        // mode 변수는 필드 검색시 사용하는 변수입니다.
        String mode = null ; // null이면 전체 모드로 보기

        int totalCount = 0 ;
        totalCount = dao.getTotalCount(mode) ;

        Paging pageInfo = new Paging(String.valueOf(pageIndex+1), "10", totalCount, null, mode, null);

        pagination.setPageCount(pageInfo.getTotalPage());

        fillTableData(pageInfo);

        VBox vbox = new VBox(productTable);

        return vbox ;
    }

    @FXML private Label pageStatus ;

    private void fillTableData(Paging pageInfo) {
        // 테이블 뷰에 목록을 채워 줍니다.
        ObservableList<Product> dataList = null ;

        List<Product> productList = dao.getPaginationData(pageInfo);

        dataList = FXCollections.observableArrayList(productList);

        productTable.setItems(dataList);
        pageStatus.setText(pageInfo.getPagingStatus());

    }

    @FXML private TableView<Product> productTable ; // 테이블 목록을 보여 주는 뷰

    private void setTableColumns() {
        // Product 빈 클래스에서 보여 주고자 하는 컬럼 정보들을 연결합니다.
        // 열거 되지 않은 목록들은 항목의 상세 보기 페이지에서 구현할 수 있습니다.
        String[] fields = {"pnum", "name", "company", "category", "inputdate"};
        String[] colNames = {"상품번호", "이름", "제조회사", "카테고리", "입고일자"};

        TableColumn tableColumn = null ;

        for (int i = 0; i < fields.length; i++) {
            tableColumn = productTable.getColumns().get(i);
            tableColumn.setText(colNames[i]); // 컬럼을 한글 이름으로 변경

            // Product 빈 클래스의 인스턴스 변수 이름을 셋팅하면 데이터가 자동으로 바인딩됩니다.
            tableColumn.setCellValueFactory(new PropertyValueFactory<>(fields[i]));

            tableColumn.setStyle("-fx-alignment:center;"); // 모든 셀 데이터를 가운데 정렬하기
        }
    }

    public void onInsert(ActionEvent event) throws Exception {
        // 상품을 등록합니다.
        // fxml 파일 로딩
        String fxmlFile = Utility.FXML_PATH + "ProductInsert.fxml";

        // import java.net.URL ;
        URL url = getClass().getResource(fxmlFile);

        FXMLLoader fxmlLoader = new FXMLLoader(url);

        Parent container = fxmlLoader.load(); // fxml의 최상위 컨테이너 객체
        
        Scene scene = new Scene(container); // 씬에 담기
        Stage stage = new Stage() ;
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); // 씬을 무대에 담기
        stage.setResizable(false);
        stage.setTitle("상품 등록하기");
        stage.showAndWait(); // 창 띄우고 대기
        
        setPagination(0); // 화면 갱신
    }

    public void onUpdate(ActionEvent event) throws Exception {
        // 선택된 항목(Product Bean)에 대한 수정 작업을 합니다.
        int idx = productTable.getSelectionModel().getSelectedIndex();

        if(idx >= 0){
            System.out.println("선택된 색인 번호 : " + idx);

            String fxmlFile = Utility.FXML_PATH + "ProductUpdate.fxml";
            URL url = getClass().getResource(fxmlFile);
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent container = fxmlLoader.load();

            // update 기능에서 추가된 내용 시작
            // 현재 내가 선택한 상품(Product) 정보와 색인 정보(idx)를 해당 컨트롤러에게 메소드를 통하여 전달해 줍니다.
            Product bean = productTable.getSelectionModel().getSelectedItem() ;

            ProductUpdateController controller = fxmlLoader.getController();

            controller.setBean(bean);

            // update 기능에서 추가된 내용 끝

            Scene scene = new Scene(container);
            Stage stage = new Stage() ;

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("상품 수정 하기");
            stage.showAndWait();
            setPagination(0);

        }else{
            String[] message = new String[]{"상품 선택 확인", "상품 미선택", "수정하고자 하는 상품을 선택해 주세요."};
            Utility.showAlert(Alert.AlertType.ERROR, message);
        }
    }

    public void onDelete(ActionEvent event) {
    }

    public void onSaveFile(ActionEvent event) {
    }

    public void onClosing(ActionEvent event) {
    }

    public void choiceSelect(ActionEvent event) {
    }

}
*/
