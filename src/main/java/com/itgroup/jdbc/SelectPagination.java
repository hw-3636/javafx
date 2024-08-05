package com.itgroup.jdbc;

import com.itgroup.bean.Product;
import com.itgroup.dao.ProductDao;
import com.itgroup.utility.Paging;

import java.util.List;
import java.util.Scanner;

public class SelectPagination {
    public static void main(String[] args) {
        // 검색 모드와 페이지네이션 기능을 구현
        Scanner scanner = new Scanner(System.in);
        System.out.print("볼 페이지: ");  //2
        String pageNumber = scanner.next();  //웹 프로그래밍 염두해두고 String(문자열)으로 처리함

        System.out.print("페이지당 출력할 건수: ");  //10
        String pageSize = scanner.next();  //웹 프로그래밍 염두해두고 String(문자열)으로 처리함

        System.out.print("all, bread, beverage, macaron, cake 중 택1 입력: ");
        String mode = scanner.next();  //검색 모드(무엇을 검색할 것인가?)

        ProductDao dao = new ProductDao();
        int totalCount = dao.getTotalCount(mode);

        String url = "prList.jsp";
        String Keyword = "";

        Paging pageInfo = new Paging(pageNumber, pageSize, totalCount, url, mode, Keyword);
        pageInfo.displayInformation();

        List<Product> productList = dao.getPaginationData(pageInfo);

        for (Product bean : productList) {
            ShowData.printBean(bean);
        }

    }
}
