package com.itgroup.controller;

import com.itgroup.bean.Person;
import com.itgroup.dao.PersonDao;
import com.itgroup.utility.Paging;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PaginationTestController implements Initializable {

    @FXML
    private Label pageStatus;
    @FXML
    private VBox vBox;
    @FXML
    private Pagination pagination;
    @FXML
    private TableView<Person> tableView;

    PersonDao dao = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //프로그램 실행 후 아무런 동작이 없는 초기 페이지의 기능을 설정
        this.dao = new PersonDao();
        setTableColumns();
        setPagination(0);
    }

    private void setPagination(int pageIndex) {
        System.out.println("pageIndex: " + pageIndex);
        //주의 제로 베이스(0 base)
        pagination.setCurrentPageIndex(pageIndex);

        //createPage 메서드를 사용하여 페이지네이션을 만듦
        pagination.setPageFactory(this::createPage);
    }

    private Node createPage(int pageNumber) {
        //String _pageNumber, String _pageSize, int totalCount, String url, String mode, String keyword
        int totalCount = dao.getTotalCount();
        Paging pageInfo = new Paging(String.valueOf(pageNumber+1), "10", totalCount, null, null, null);

        pageInfo.displayInformation();  //페이지 정보를 출력하는 메서드
        pagination.setPageCount(pageInfo.getTotalPage());

        fillTableData(pageInfo);
        vBox = new VBox(tableView);

        return vBox;
    }

    ObservableList<Person> dataList = null;
    //ObservableList : javaFx의 List Collection 과 비슷한 개념이라고 생각하기
    private void fillTableData(Paging pageInfo) {
        //tableView 에 데이터를 채우기
        List<Person> personList = dao.getAllData(pageInfo.getBeginRow() - 1, pageInfo.getEndRow());
        //getAllData 는 dao 클래스에 저장된 데이터베이스 정보를 일정 부분만 받아오는 메서드

        dataList = FXCollections.observableArrayList(personList);
        //List<Person> dataList = new ArrayList<>(); 개념과 비슷하다고 생각하면 된다.

        tableView.setItems(dataList);
        //setItems 는 ObservableList 를 변수로 받아 tableView 에 추가하기 때문에 personList 에서 dataList 로 건네받은 이후에 tableView 에 추가할 수 있다.

        pageStatus.setText(pageInfo.getPagingStatus());  //라벨에 현재 페이지 위치를 표시하는 것
    }

    private void setTableColumns() {
        //테이블 내 컬럼 작업
        //tableView 객체가 Person 타입의 정보를 가지고 있으므로 다음 배열 요소의 값은 Person 클래스 변수 이름과 동일해야 한다.
        String[] field = {"number", "name", "lastName"};  //Person 에 들어있는 칼럼 요소. Person 클래스 변수 이름과 동일한 이름.
        TableColumn tableColumn = null;

        for (int i = 0; i < field.length; i++) {
            tableColumn = tableView.getColumns().get(i);
            tableColumn.setCellValueFactory(new PropertyValueFactory<>(field[i]));
            tableColumn.setStyle("-fx-alignment:center;");  //가운데 정렬
        }
    }
}
