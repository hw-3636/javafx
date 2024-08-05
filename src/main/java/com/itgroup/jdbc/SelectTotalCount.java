package com.itgroup.jdbc;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;

import java.util.List;
import java.util.Scanner;

public class SelectTotalCount {
    public static void main(String[] args) {
        //전체 또는 카테고리별 상품의 갯수를 반환하기
        Scanner scanner = new Scanner(System.in);
        System.out.print("all, bread, beverage, macaron, cake 중 택1 입력: ");
        String category = scanner.next();

        ProductDao dao = new ProductDao();
        int totalCount = dao.getTotalCount(category);

        if (category.equals("all")) {
            System.out.println("전체 상품 개수: " + totalCount + "개");
        } else {
            System.out.println(category + " 카테고리의 전체 상품 개수: " + totalCount + "개");
        }
    }
}
