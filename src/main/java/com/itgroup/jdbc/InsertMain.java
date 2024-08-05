package com.itgroup.jdbc;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class InsertMain {
    public static void main(String[] args) {
        //관리자가 상품 1개를 등록합니다.
        ProductDao dao = new ProductDao();
        Product bean = new Product();

        Scanner scanner = new Scanner(System.in);
        System.out.print("상품 이름: ");
        String name = scanner.next();

        //bean.setProductNum(0);  //시퀀스로 대체 예정
        bean.setName(name);
        bean.setCompany("AB 식품");
        bean.setImage01("xx.png");
        bean.setImage02("yy.png");
        bean.setImage03("zz.png");
        bean.setStock(1234);
        bean.setPrice(5678);
        bean.setCategory("bread");
        bean.setContents("맛있습니다");
        //bean.setPoint(0);  //포인트는 기본 값 사용 예정
        //bean.setInputDate(null);  //입고 일자도 기본 값 사용 예정

        int cnt = -1 ;  //-1을 insertData 에 실패한 경우라고 가정
        cnt = dao.insertData(bean);

        if (cnt == -1) {
            System.out.println("상품 등록에 실패하였습니다.");
        }else{
            System.out.println(bean.getName() + " 상품 등록에 성공하였습니다.");
        }
    }
}
