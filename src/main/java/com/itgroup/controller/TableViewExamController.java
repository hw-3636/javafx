package com.itgroup.controller;

import com.itgroup.bean.Article;
import com.itgroup.dao.ArticleDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TableViewExamController implements Initializable {
    @FXML private TableView<Article> tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArticleDao dao = new ArticleDao() ;
        List<Article> articleList = dao.selectData();

        ObservableList dataList = FXCollections.observableArrayList(articleList);
        tableView.setItems(dataList);

        String[] columns = {"category", "name", "image"};
        for (int i = 0; i < columns.length; i++) {
            TableColumn myColumn = tableView.getColumns().get(i);
            myColumn.setCellValueFactory(new PropertyValueFactory<>(columns[i]));
        }

        ChangeListener<Article> tableListener = new ChangeListener<Article>() {
            @Override
            public void changed(ObservableValue<? extends Article> observableValue, Article oldValue, Article newValue) {
                String message = "카테고리:" + newValue.getCategory() + ", 이름:" + newValue.getName() + ", 이미지:" + newValue.getImage() ;
                System.out.println(message);
            }
        };

        tableView.getSelectionModel().selectedItemProperty().addListener(tableListener);
    }
}
