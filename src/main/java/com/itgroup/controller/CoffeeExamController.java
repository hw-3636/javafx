package com.itgroup.controller;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;
import com.itgroup.utility.Paging;
import com.itgroup.utility.Utility;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CoffeeExamController implements Initializable {

    @FXML
    private Button buttonSaveFile;
    @FXML
    private ComboBox<String> fieldSearch;
    @FXML
    private Label labelChoice, pageStatus;
    @FXML
    private TableView<Product> productTable;  //테이블 목록을 보여주는 뷰
    @FXML
    private ImageView imageView;
    @FXML
    private Pagination pagination;

    private ObservableList<Product> dataList = null;
    private ProductDao dao = null;
    String mode = null;  //전체를 가져오도록 임의로 null 값으로 지정.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {  //프로그램 실행 시 초기 설정을 저장하는 메서드
        this.dao = new ProductDao();

        setTableColumns();  //테이블 뷰 컬럼 제목을 받아오며 정렬 지정하기
        setPagination(0); //최초 시작 시 1페이지를 누르게끔 하는 메서드
        fieldSearch.getItems().setAll(Utility.getCategoryKorName());

        //테이블 뷰의 1행을 클릭하면 우측에 이미지를 보여주기
        ChangeListener<Product> tableListener = new ChangeListener<Product>() {  //Listener 는 대부분이 인터페이스.
            @Override
            public void changed(ObservableValue<? extends Product> observableValue, Product oldValue, Product newValue) {
                if(newValue != null){
                    System.out.println("상품 정보");
                    System.out.println(newValue);

                    String imageFile = "";  //해당 이미지의 fullPath + 이미지이름

                    if (newValue.getImage01() != null) {
                        imageFile = Utility.IMAGE_PATH + newValue.getImage01().trim();
                    }else{
                        imageFile = Utility.IMAGE_PATH + "noimage.jpg";
                    }

                    Image image = null;  //이미지 객체
                    if(getClass().getResource(imageFile) == null){
                        imageView.setImage(image);
                    }else {
                        image = new Image(getClass().getResource(imageFile).toString());
                        imageView.setImage(image);
                    }
                }
            }
        };

        productTable.getSelectionModel().selectedItemProperty().addListener(tableListener);
        //테이블의 아이템을 선택하면 리스너를 생성한다.
        //productTable 이 바뀌면 위의 changed 함수가 자동으로 실행된다.
        //getSelectionModel() view, comboBox 등 목록을 담을 수 있는 클래스에 모두 정의되어 있음.

        setContextMenu();
    }

    private void setContextMenu() {
        //테이블 뷰에 대하여 컨텍스트 메뉴를 구성
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem01 = new MenuItem("단가 수직 막대");
        MenuItem menuItem02 = new MenuItem("단가 파이 그래프");
        MenuItem menuItem03 = new MenuItem("단가/재고 막대");

        //자바에서 ...은 가변 매개 변수
        //매개 변수를 무제한으로 넣을 수 있으며, addAll() 메서드가 가변 매개 변수 형태로 입력이 가능하기에 이론상 무제한으로 입력할 수 있음
        contextMenu.getItems().addAll(menuItem01, menuItem02, menuItem03);
        productTable.setContextMenu(contextMenu);

        //menuItem01(단가 수직 막대) 을 선택(event)하면 중괄호 내의 코드가 실행된다.
        menuItem01.setOnAction((event->{
            try {
                makeBarChart();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        menuItem02.setOnAction((event->{
            try {
                makePieChart();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        menuItem03.setOnAction((event->{
            try {
                makeBarChartAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
    }

    //fxml 파일명 받아오기
    private FXMLLoader getFxmlLoader(String fxmlFileName) throws Exception {
        Parent parent = null;

        String fileName = Utility.FXML_PATH + fxmlFileName;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));

        return fxmlLoader;
    }

    //scene 나타내기
    private void showModal(Parent parent) {
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    //menuItem01(단가 수직 막대) 그래프 그리기
    private void makeBarChart() throws Exception{
        FXMLLoader fxmlLoader =this.getFxmlLoader("BarChart.fxml");
        Parent parent = fxmlLoader.load();

        BarChartController controller = fxmlLoader.getController();

        //해당 담당 컨트롤러에게 그리고자 하는 정보를 넘겨주기
        controller.makeChart(productTable.getItems());

        this.showModal(parent);
    }

    //menuItem02(단가 파이 막대) 그래프 그리기
    private void makePieChart() throws Exception{
        FXMLLoader fxmlLoader =this.getFxmlLoader("pieChart.fxml");
        Parent parent = fxmlLoader.load();

        PieChartController controller = fxmlLoader.getController();
        controller.makePieChart(productTable.getItems());

        showModal(parent);
    }

    //menuItem03(단가/재고 막대) 그래프 그리기
    private void makeBarChartAll() throws Exception{
        FXMLLoader fxmlLoader =this.getFxmlLoader("BarChart.fxml");
        Parent parent = fxmlLoader.load();

        BarChartController controller = fxmlLoader.getController();
        controller.makeChartALL(productTable.getItems());

        this.showModal(parent);
    }


    private void setPagination(int pageIndex) {
        // pagination의 현재 페이지 인덱스 설정
        pagination.setCurrentPageIndex(pageIndex);

        // pagination의 currentPageIndexProperty에 ChangeListener 추가
        pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            // newIndex는 변경된 페이지의 인덱스

            // TableView의 데이터를 새로운 페이지 데이터로 설정
            ObservableList<Product> pageDataList = createPage(newIndex.intValue());
            productTable.setItems(pageDataList);
        });

        // 초기 페이지 설정
        ObservableList<Product> initialPageData = createPage(pageIndex);
        productTable.setItems(initialPageData);

        // 화면 갱신 시 이미지 뷰 정보 초기화
        imageView.setImage(null); // 디폴트 이미지 설정
    }

    private ObservableList<Product> createPage(int pageIndex) {
        // 페이지 생성 및 데이터 가져오기
        int totalCount = dao.getTotalCount(this.mode);

        Paging pageInfo = new Paging(String.valueOf(pageIndex + 1), "10", totalCount, null, this.mode, null);
        List<Product> productList = dao.getPaginationData(pageInfo);
        ObservableList<Product> pageDataList = FXCollections.observableArrayList(productList);

        // 페이지네이션 설정
        pagination.setPageCount(pageInfo.getTotalPage());
        pageStatus.setText(pageInfo.getPagingStatus());

        return pageDataList;
    }

    /*private void setPagination(int pageIndex) {
        //페이징 관련 설정
        pagination.setCurrentPageIndex(pageIndex);

        pagination.setPageFactory(this::createPage);

        //화면 갱신 시 이미지 뷰 정보도 없애기
        imageView.setImage(null);; //디폴트 이미지(noImage) 변경도 고려하기
        // -> setImage 로 delete 에만... 만들 순 없나?
    }

    private Node createPage(Integer pageIndex) {
        //각 페이지의 pagination 을 동적으로 생성해주는 공장(Factory) 역할
        //mode 변수는 필드 검색 시 사용하는 변수

        int totalCount = 0;
        totalCount = dao.getTotalCount(this.mode);  //getTotalCount() 메서드가 all or null 일 때 전체 출력이기 떄문에 일단 임의로 null 값을 입력한 것.

        Paging pageInfo = new Paging(String.valueOf(pageIndex+1), "10", totalCount, null, this.mode, null);

        pagination.setPageCount(pageInfo.getTotalPage());  //토탈 페이지를 계산하고 1페이지부터 토탈 페이지까지 페이지네이션에 세팅

        fillTableData(pageInfo);  //fillTableData 메서드는 productTable 에 데이터를 채워 넣는 메서드

        VBox vBox = new VBox(productTable);  //테이블 목록을 보여주는 productTable 을 vBox 에 할당

        return vBox;  //vBox(자식) 와 Node(부모) 는 상속 관계
    }

    private void fillTableData(Paging pageInfo) {
        //테이블 뷰에 목록을 채우기
        List<Product> productList = dao.getPaginationData(pageInfo);
        this.dataList = FXCollections.observableArrayList(productList);
        productTable.setItems(this.dataList);  //setItems 는 ComboBox, TableView, ListView 등에 사용됨
        pageStatus.setText(pageInfo.getPagingStatus());
//        productTable.getItems().clear();
//        productTable.getItems().addAll(dataList);
    }*/


    private void setTableColumns() {
        //Product bean 클래스에서 보여주고자 하는 컬럼 정보를 연결
        //열거되지 않은 목록들은 항목의 상세 보기 페이지에서 구현할 수 있음
        String[] fields = {"productNum", "name", "company", "category", "inputDate"};  //Product bean 클래스의 인스턴스 변수 이름 -> 오타 나면 안됨!
        String[] columnNames = {"상품번호", "이름", "제조회사", "카테고리", "입고일자"};

        //String[] columStyle = {""-fx-alignment:center;", "-fx-alignment:left;", "-fx-alignment:left;", "-fx-alignment:left;", "-fx-alignment:left;"}
        //스타일 세팅할 때 배열로 지정 가능~ >> 한번 해보세요~의 개념인듯

        TableColumn tableColumn = null;
        for (int i = 0; i < fields.length; i++) {
            tableColumn = productTable.getColumns().get(i);
            tableColumn.setText(columnNames[i]);  //컬럼을 한글 이름으로 변경

            //Product bean 클래스의 인스턴스 변수 이름을 세팅하면 데이터가 자동으로 바인딩 됨
            tableColumn.setCellValueFactory(new PropertyValueFactory<>(fields[i]));
            tableColumn.setStyle("-fx-alignment:center;");  //모든 셀 데이터를 가운데 정렬
        }
    }

    public void onInsert(ActionEvent event) throws IOException{  //등록 버튼
        //상품 등록(ProductInsert)
        //fxml 파일 로딩
        //메인 메서드와 같은 내용 Start
        String fxmlFile = Utility.FXML_PATH + "ProductInsert.fxml";
        //URL : improt java.net.URL;
        URL url = getClass().getResource(fxmlFile);
        FXMLLoader fxmlLoader = new FXMLLoader(url);

        Parent container = fxmlLoader.load();  //fxml 의 최상위 컨테이너 객체인 Parent 에 FXMLLoader 이 승급됨
        Scene scene = new Scene(container);  //씬에 담기
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);  //무대를 모달 창으로 띄웠기 때문에 모달 창이 닫히지 않으면 다른 뒤의 창에 접근이 불가. 해당 창을 닫아야 다른 작업 가능
        stage.setScene(scene);  //씬을 무대에 담기
        stage.setResizable(false);
        stage.setTitle("상품 등록");
        stage.showAndWait();  //창 띄우고 대기
        //메인 메서드와 같은 내용 End
        setPagination(0);  //화면 갱신
    }

    public void onUpdate(ActionEvent event) throws IOException {  //수정 버튼
        //선택된 항목(Product bean)에 관한 수정 작업
        //상품 수정(ProductUpdate)
        int index = productTable.getSelectionModel().getSelectedIndex();  //색인 번호
//        System.out.println(index);

        if (index < 0) {
            System.out.println("선택된 색인 번호: " + index);
            String[] message = new String[]{"상품 선택 확인", "수정 상품 미선택", "수정 대상 상품을 선택해 주세요"};
            Utility.showAlert(Alert.AlertType.ERROR, message);
            return;
        }
        String fxmlFile = Utility.FXML_PATH + "ProductUpdate.fxml";
        URL url = getClass().getResource(fxmlFile);
        FXMLLoader fxmlLoader = new FXMLLoader(url);

        Parent container = fxmlLoader.load();

        //update 기능에서 추가된 내용 시작
        //현재 선택한 상품(Product bean) 정보와 색인 정보(index)를 해당 컨트롤러(ProductUpdateController)에 전달
        Product bean = productTable.getSelectionModel().getSelectedItem();
        ProductUpdateController controller = fxmlLoader.getController();
        controller.setBean(bean);

        //update 기능에서 추가된 내용 끝

        Scene scene = new Scene(container);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("상품 수정");
        stage.showAndWait();
        setPagination(0);
    }

    public void onDelete(ActionEvent event) {//삭제 버튼
        int index = productTable.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("삭제 여부 확인");
            alert.setHeaderText("삭제 목록 여부");
            alert.setContentText("선택한 항목을 삭제하시겠습니까?");
            Optional<ButtonType> response = alert.showAndWait();

            if (response.get() == ButtonType.OK) {
                int productNum = productTable.getSelectionModel().getSelectedItem().getProductNum();
                int cnt = -1;
                cnt = dao.deleteData(productNum);
                if (cnt != -1) {
                    System.out.println("삭제 성공");
                    setPagination(0);
                }else {
                    System.out.println("삭제 실패");
                }
            }else{
                System.out.println("삭제를 취소하였습니다.");
            }
        }else{
            String[] message = new String[] {"삭제할 목록 확인", "삭제할 대상 미선택", "삭제할 행을 선택해 주세요",};
            Utility.showAlert(Alert.AlertType.WARNING, message);
        }
    }

    public void onSaveFile(ActionEvent event) {  //파일로 저장 버튼
        //현재 보여지는 테이블 뷰 목록을 텍스트 형식의 파일로 저장
        FileChooser fileChooser = new FileChooser();
        Button myButton = (Button)event.getSource();  //Object 는 Button 의 부모
        Window window = myButton.getScene().getWindow(); //Scene 은 Window 의 부모
        File savedFile = fileChooser.showSaveDialog(window);

        if (savedFile != null) {
            FileWriter fw = null;
            BufferedWriter bw = null;

            this.dataList = this.productTable.getItems();

            try{
                fw = new FileWriter(savedFile);
                bw = new BufferedWriter(fw);  //fw 의 승급

                for (Product bean : dataList) {
                    bw.write(bean.toString());
                    bw.newLine(); //엔터키
                }

                System.out.println("파일 저장 성공");

            }catch (Exception e) {
                e.printStackTrace();
            }finally{
                try{
                    if(bw!=null){bw.close();}
                    if(fw!=null){fw.close();}
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("파일 저장 취소");
        }
    }

    public void onClosing(ActionEvent event) {  //종료 버튼
        System.out.println("프로그램을 종료합니다.");
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("종료 여부 확인");
        alert.setHeaderText("종료 버튼을 클릭하셨습니다");
        alert.setContentText("프로그램을 종료하시겠습니까?");
        Optional<ButtonType> response = alert.showAndWait();

        if (response.get() == ButtonType.OK) {
            System.out.println("프로그램 종료");
            Platform.exit();
        }else{
            System.out.println("프로그램 종료를 취소하셨습니다.");
        }
    }

    public void choiceSelect(ActionEvent event) {  //출력 모드 선택의 콤보박스
        String _category = fieldSearch.getSelectionModel().getSelectedItem();
        this.mode = Utility.getCategoryEngName(_category);
        setPagination(0);
    }
}
