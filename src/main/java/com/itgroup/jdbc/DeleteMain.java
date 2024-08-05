package com.itgroup.jdbc;

import com.itgroup.dao.ProductDao;

import java.util.Scanner;

public class DeleteMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 상품 번호: ");
        int deletePnum = scanner.nextInt();

        ProductDao dao = new ProductDao();
        int cnt = -1;
        cnt = dao.deleteData(deletePnum);  //삭제는 주로 프라이머리 키를 기준으로 삭제함

        if (cnt == -1) {
            System.out.println("상품번호 " + deletePnum + "번 삭제 실패");
        }else{
            System.out.println("상품번호 " + deletePnum + "번 삭제 성공");
        }
    }
}
