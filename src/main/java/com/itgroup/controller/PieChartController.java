package com.itgroup.controller;

import com.itgroup.bean.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PieChartController implements Initializable {
    //fxml 파이 차트 그래프 컨트롤러

    @FXML private PieChart pieChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void makePieChart(ObservableList<Product> dataList) {
        List<PieChart.Data> pieChartData = new ArrayList<>();
        for (Product product : dataList) {
            pieChartData.add(new PieChart.Data((product.getName()), product.getPrice()));
        }
        pieChart.setData(FXCollections.observableArrayList(pieChartData));
        pieChart.setTitle("파이 그래프 - 단가");
    }
}
