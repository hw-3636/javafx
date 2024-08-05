package com.itgroup.controller;

import com.itgroup.bean.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BarChartController implements Initializable {
    //fxml 막대 그래프(BarChart) 컨트롤러

    @FXML BarChart<String, Integer> barChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void makeChart(ObservableList<Product> dataList) {
        //수직 막대 그래프 그리기
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("단가");

        for (Product product : dataList) {
            series.getData().add(new XYChart.Data<>(product.getName(), product.getPoint()));
        }
        barChart.getData().add(series);
        barChart.setTitle("수직 막대 그래프 - 단가");
    }

    public void makeChartALL(ObservableList<Product> dataList) {
        for (Product product : dataList) {
            List<XYChart.Data<String, Integer>> lists = new ArrayList<>();

            lists.add(new XYChart.Data<String, Integer>("단가", product.getPrice()));
            lists.add(new XYChart.Data<String, Integer>("재고", product.getStock()));

            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setData(FXCollections.observableArrayList(lists));
            barChart.getData().add(series);
            barChart.setTitle("수직 막대 그래프 - 단가/재고");
        }
    }
}
