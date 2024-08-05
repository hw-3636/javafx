package com.itgroup.jdbc;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;

import java.util.Scanner;

public class UpdateMain {
    public static void main(String[] args) {
        //특정 상품에 관한 정보를 수정합니다.
        ProductDao dao = new ProductDao();
        Product bean = new Product();

        Scanner scanner = new Scanner(System.in);
        System.out.print("상품 번호: ");  //상품 번호에 해당하는 상품의 이름을
        int productNumber = scanner.nextInt();
        System.out.print("상품 이름: ");  //입력받은 이름으로 변경해라
        String name = scanner.next();

        bean.setProductNum(productNumber);
        bean.setName(name);
        bean.setCompany("AB 식품");
        bean.setImage01("xx.png");
        bean.setImage02("yy.png");
        bean.setImage03("zz.png");
        bean.setStock(9999);
        bean.setPrice(1111);
        bean.setCategory("bread");
        bean.setContents("촉촉합니다.");
        bean.setPoint(15);
        bean.setInputDate("2024/07/17");

        int cnt = -1 ;  //-1을 insertData 에 실패한 경우라고 가정
        cnt = dao.updateData(bean);

        if (cnt == -1) {
            System.out.println("상품 수정에 실패하였습니다.");
        }else{
            System.out.println(bean.getProductNum() + "번 상품 이름을 '" + bean.getName() +"'으로 수정하였습니다.");
        }
    }
}
