package com.itgroup.jdbc;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;

import java.util.List;
import java.util.Scanner;

public class SelectOnlyOne {
    public static void main(String[] args) {
        //상품 번호를 이용하여 특정 상품 정보 가져오기
        Scanner scanner = new Scanner(System.in);
        System.out.print("상품 번호 입력: ");

        int pnum = scanner.nextInt();

        ProductDao dao = new ProductDao();
        Product bean = dao.selectByPK(pnum);  //기본 키로 가져오면 딱 1건만 가져온다. (ex 상품 상세 보기 및 상품 정보 수정 시 사용)

        if (bean == null) {
            System.out.println("상품 번호: " + pnum + "은(는) 존재하지 않습니다.");
        }else{
            ShowData.printBean(bean);
        }




    }
}
