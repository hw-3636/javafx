package com.itgroup.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class SuperDao {
    private String driver; //드라이버 문자열
    private String url = null;  //데이터베이스 출처
    private String id = null;  //사용자 아이디
    private String password = null;  //사용자 비밀번호

    public SuperDao() {
        this.driver = "oracle.jdbc.driver.OracleDriver";  //다른 피씨 접속 시 바꿔야 함
        this.url = "jdbc:oracle:thin:@localhost:1521:xe";  //다른 피씨 접속 시 바꿔야 함
        this.id = "oraman";  //플젝 시 바꿔주어야 한다.
        this.password = "oracle";  //플젝 시 바꿔주어야 한다.
        try {
            Class.forName(driver);  //1. 동적 객체 생성(드라이버 로딩)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Connection getConnection() {  //2. 커넥션 객체 생성
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, id, password);  //2-1. 로딩한 정보를 이용하여 커넥션 객체 생성
            if (conn != null) {
//                System.out.println("접속 성공");
            } else {
                System.out.println("접속 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
