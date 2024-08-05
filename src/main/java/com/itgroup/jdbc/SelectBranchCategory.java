package com.itgroup.jdbc;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;

import java.util.List;
import java.util.Scanner;

public class SelectBranchCategory {
    public static void main(String[] args) {
        //모든 상품 또는 특정 카테고리만 조회하기
        Scanner scanner = new Scanner(System.in);
        System.out.print("all, bread, beverage, macaron, cake 중 택1 입력: ");

        String category = scanner.next();

        ProductDao dao = new ProductDao();
        List<Product> allProduct = dao.selectByCategory(category);
        System.out.println("상품 개수 : " + allProduct.size());
        System.out.println("--------------------------------");

        for (Product bean : allProduct) {
            ShowData.printBean(bean);
        }

    }
}
