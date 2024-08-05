package com.itgroup.dao;

import com.itgroup.bean.Product;
import com.itgroup.utility.Paging;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
페이징 처리시 사용할 코드

String sql = " select pnum, name, company, image01, image02, image03, stock, price, category, contents, point, inputdate ";
sql += " from (";
sql += " select pnum, name, company, image01, image02, image03, stock, price, category, contents, point, inputdate, ";
sql += " rank() over(order by pnum desc) as ranking ";
sql += " from products ";
sql += " ) ";
sql += " where ranking between ? and ?";

*/

public class ProductDao extends SuperDao {

    public ProductDao() {
        super();
    }

    public List<Product> selectAll() {  //모든 상품 조회하기
        Connection conn = null;
        String sql = "select * from products order by pnum desc";  //상품번호 역순 정렬
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Product> allData = new ArrayList<>();  //리턴 값 받는 컬렉션

        try {
            conn = super.getConnection();  //2. 커넥션 객체 생성(슈퍼클래스에서 받아오기)
            pstmt = conn.prepareStatement(sql);  //3. PreparedStatement 객체 생성
            rs = pstmt.executeQuery();  //4-1. DQL 처리 ResultSet 구하기

            while (rs.next()) {
                Product bean = this.makeBean(rs);
                allData.add(bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs!=null){rs.close();}  //5. 작업 닫기
                if(pstmt!=null){pstmt.close();}  //5. 작업 닫기
                if(conn!=null){conn.close();}  //5. 작업 닫기
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return allData;
    }

    private Product makeBean(ResultSet rs) {
        //bean 데이터를 만들어주는 메서드(한줄 데이터를 생성해주는 메서드)
        //여러군데에서 호출이 되므로 별도로 생성
        Product bean = new Product();
        try{
            bean.setProductNum(rs.getInt("pnum"));
            bean.setName(rs.getString("name"));
            bean.setCompany(rs.getString("company"));
            bean.setImage01(rs.getString("image01"));
            bean.setImage02(rs.getString("image02"));
            bean.setImage03(rs.getString("image03"));
            bean.setStock(rs.getInt("stock"));
            bean.setPrice(rs.getInt("price"));
            bean.setCategory(rs.getString("category"));
            bean.setContents(rs.getString("contents"));
            bean.setPoint(rs.getInt("point"));
            bean.setInputDate(String.valueOf(rs.getDate("inputdate")));  //날짜로 가져와서 문자화 시켜서 반환,
                //bean.setInputDate 에 시간데이터까지 포함되어 있기 때문에 rs.getString 으로 할 경우 시, 분, 초 단위까지 전부 출력된다.
        }catch(Exception e){
            e.printStackTrace();
        }
        return bean;
    }

    public List<Product> selectByCategory(String category) {
        Connection conn = null;
        String sql = " select * from products";  //앞 한 칸 띄어쓰기
//        String sql = " select distinct category from products";  //앞 한 칸 띄어쓰기

        boolean bool =category == null || category.equals("all");
        if (!bool) {
            sql += " where category = ? "; //앞 한 칸 띄어쓰기, 따옴표 치지 않아도 문장 객체(PreparedStatement)가 알아서 치환받아줌
        }

        PreparedStatement pstmt = null;  //문장 객체
        ResultSet rs = null;

        List<Product> allData = new ArrayList<>();  //리턴 값 받는 컬렉션

        try {
            conn = super.getConnection();  //2. 커넥션 객체 생성(슈퍼클래스에서 받아오기)
            pstmt = conn.prepareStatement(sql);  //3. PreparedStatement 객체 생성

            if (!bool) {
                pstmt.setString(1, category);  //0부터가 아니라 1부터 시작함, 세팅은 무조건 exe ... 단어 앞에, 실행되기 전에.
            }
            rs = pstmt.executeQuery();  //4-1. DQL 처리 ResultSet 구하기

            while (rs.next()) {
                Product bean = this.makeBean(rs);
                allData.add(bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs!=null){rs.close();}  //5. 작업 닫기
                if(pstmt!=null){pstmt.close();}  //5. 작업 닫기
                if(conn!=null){conn.close();}  //5. 작업 닫기
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return allData;
    }
    public List<String> selectCategory() {
        Connection conn = null;
        String sql = " select distinct category from products";

        PreparedStatement pstmt = null;  //문장 객체
        ResultSet rs = null;

        List<String> categoryList = new ArrayList<>();

        try {
            conn = super.getConnection();  //2. 커넥션 객체 생성(슈퍼클래스에서 받아오기)
            pstmt = conn.prepareStatement(sql);  //3. PreparedStatement 객체 생성
            rs = pstmt.executeQuery();  //4-1. DQL 처리 ResultSet 구하기

            while (rs.next()) {
                categoryList.add(rs.getString("category"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs!=null){rs.close();}  //5. 작업 닫기
                if(pstmt!=null){pstmt.close();}  //5. 작업 닫기
                if(conn!=null){conn.close();}  //5. 작업 닫기
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return categoryList;
    }

    public Product selectByPK(int pnum) {
        Connection conn = null ;
        String sql = " select * from products" ;
        sql += " where pnum = ? ";
        PreparedStatement pstmt = null ;
        ResultSet rs = null ;

        Product bean = null;
        try{
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pnum);  //1부터 시작, ? 에 치환되는 값
            rs = pstmt.executeQuery();

            if(rs.next()){
                bean = this.makeBean(rs); //바뀐 문장
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(rs!=null){rs.close();}
                if(pstmt!=null){pstmt.close();}
                if(conn!=null){conn.close();}
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return bean;
    }

    public int insertData(Product bean) {
        System.out.println(bean);
        int cnt = -1;  //-1을 insert data 에 실패한 경우라고 가정

        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " INSERT into products(pnum, name, company, image01, image02, image03, stock, price, category, contents, point, inputdate)";
        sql += " values(seqproduct.nextval,?,?,?,?,?,?,?,?,?,?,?)";  //pnum 은 시퀀스 번호로, 포인트와 날짜는 default 값이 있음 //칼럼 개수만큼 ? 추가

        try{
            conn = super.getConnection();
            conn.setAutoCommit(false);  //데이터베이스 연결 객체(Connection 객체)에서 트랜잭션의 자동 커밋 설정을 변경(자동 커밋을 하지 않고 사용자가 직접 커밋하도록)
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getCompany());
            pstmt.setString(3, bean.getImage01());
            pstmt.setString(4, bean.getImage02());
            pstmt.setString(5, bean.getImage03());
            pstmt.setInt(6, bean.getStock());
            pstmt.setInt(7, bean.getPrice());
            pstmt.setString(8, bean.getCategory());
            pstmt.setString(9, bean.getContents());
            pstmt.setInt(10, bean.getPoint());
            pstmt.setString(11, bean.getInputDate());

            cnt = pstmt.executeUpdate();  //insert, update, delete 모두 executeUpdate() 사용.
                                        // 참고로 스프링에서는 그대로 insert, update, delete 사용.
            conn.commit();  //커넥션 객체에 commit 과 rollback 이 존재.
                            // executeUpdate() 로 insert 수행하였으니 저장 혹은 롤백 시켜주어야 함.


        }catch(Exception e){
            e.printStackTrace();
            try {
                conn.rollback();  // executeUpdate() 로 insert 수행하였으니 저장 혹은 롤백 시켜주어야 함. 실패했을 땐 에러 메세지와 함께 롤백 수행.
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }finally{
            try{
                if(pstmt!=null){pstmt.close();}
                if(conn!=null){conn.close();}
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return cnt;
    }

    public int updateData(Product bean) {
        System.out.println(bean);
        int cnt = -1;  //-1을 insert data 에 실패한 경우라고 가정

        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " update products set name = ?, company = ?, image01 = ?, image02 = ?, image03 = ?, stock = ?, price = ?, category = ?, contents = ?, point = ?, inputdate = ?";
        sql += " where pnum = ?";

        try{
            conn = super.getConnection();
            conn.setAutoCommit(false);  //데이터베이스 연결 객체(Connection 객체)에서 트랜잭션의 자동 커밋 설정을 변경하는 역할
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, bean.getName());
            pstmt.setString(2, bean.getCompany());
            pstmt.setString(3, bean.getImage01());
            pstmt.setString(4, bean.getImage02());
            pstmt.setString(5, bean.getImage03());
            pstmt.setInt(6, bean.getStock());
            pstmt.setInt(7, bean.getPrice());
            pstmt.setString(8, bean.getCategory());
            pstmt.setString(9, bean.getContents());
            pstmt.setInt(10, bean.getPoint());
            pstmt.setString(11, bean.getInputDate());
            pstmt.setInt(12, bean.getProductNum());

            cnt = pstmt.executeUpdate();  //insert, update, delete 모두 executeUpdate() 사용.
            // 참고로 스프링에서는 그대로 insert, update, delete 사용.
            conn.commit();  //커넥션 객체에 commit 과 rollback 이 존재.
            // executeUpdate() 로 insert 수행하였으니 저장 혹은 롤백 시켜주어야 함.


        }catch(Exception e){
            e.printStackTrace();
            try {
                conn.rollback();  // executeUpdate() 로 insert 수행하였으니 저장 혹은 롤백 시켜주어야 함. 실패했을 땐 에러 메세지와 함께 롤백 수행.
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }finally{
            try{
                if(pstmt!=null){pstmt.close();}
                if(conn!=null){conn.close();}
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return cnt;
    }

    public int deleteData(int deletePnum) {
        System.out.println("삭제할 기본 키: " + deletePnum);
        int cnt = -1;  //-1을 insert data 에 실패한 경우라고 가정

        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " delete from products";
        sql += " where pnum = ?";

        try{
            conn = super.getConnection();
            conn.setAutoCommit(false);  //데이터베이스 연결 객체(Connection 객체)에서 트랜잭션의 자동 커밋 설정을 변경하는 역할
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, deletePnum);  //삭제할 프라이머리 키

            cnt = pstmt.executeUpdate();  //insert, update, delete 모두 executeUpdate() 사용.
            // 참고로 스프링에서는 그대로 insert, update, delete 사용.
            conn.commit();  //커넥션 객체에 commit 과 rollback 이 존재.
            // executeUpdate() 로 insert 수행하였으니 저장 혹은 롤백 시켜주어야 함.

        }catch(Exception e){
            e.printStackTrace();
            try {
                conn.rollback();  // executeUpdate() 로 insert 수행하였으니 저장 혹은 롤백 시켜주어야 함. 실패했을 땐 에러 메세지와 함께 롤백 수행.
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }finally{
            try{
                if(pstmt!=null){pstmt.close();}
                if(conn!=null){conn.close();}
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return cnt;
    }

    public int getTotalCount(String category)
    {
        int totalCount = 0;

        String sql = " select count(*) as mycnt from products";  //count(*): 행 수를 보여주는 것
        // count(*) 에는 무조건 alias 이름(별칭, mycnt) 붙여주어야 한다.
        // why??? >> 파생칼럼이기때문에. 파생칼럼은 이름을 무조건 지정해주어야 자바가 읽어올 수 있다.

        boolean bool = category == null || category.equals("all");
        if (!bool) {  //all 을 제외한 카테고리가 입력되었을 때
            sql += " where category = ?";
        }

        Connection conn = null ;
        PreparedStatement pstmt = null ;
        ResultSet rs = null ;

        try{
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);

            if (!bool) {  //all 을 제외한 카테고리가 입력되었을 때
                pstmt.setString(1, category);  //1부터 시작, ? 에 치환되는 값
            }

            rs = pstmt.executeQuery();

            if(rs.next()){
                totalCount = rs.getInt("mycnt"); //alias 이름이 들어와야 하며,
                // totalCount 에는 행 수가 들어가는 것이기 때문에 DB의 count(*) 함수로 받은 행 수가 필요,
                // 위의 String sql 에서 파생칼럼인 count(*) 의 별칭은 mycnt 로 정의했기 때문에 mycnt 가 들어간다.
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(rs!=null){rs.close();}
                if(pstmt!=null){pstmt.close();}
                if(conn!=null){conn.close();}
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return totalCount;
    }

    public List<Product> getPaginationDataOffset(Paging pageInfo) {  //(개인) 연습!
        Connection conn = null ;
        String sql = " SELECT * from (";
        sql += "    SELECT a.*, ROWNUM rnum";
        sql += "    from (";
        sql += "        SELECT *";
        sql += "        from products ";

        String mode = pageInfo.getMode();
        boolean bool = mode == null || mode.equals("null") || mode.isEmpty() || mode.equals("all");
        if (!bool) {
            sql += " where category = ?";
        }

        sql += "        ORDER BY pnum desc";
        sql += "    ) a";
        sql += "    WHERE ROWNUM <= :? + :?";
        sql += " )";
        sql += " WHERE rnum > :? ;";

        PreparedStatement pstmt = null ;
        ResultSet rs = null ;

        List<Product> allData = new ArrayList<>();
        try{
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);

            if (!bool) {
                pstmt.setString(1, mode);
                pstmt.setInt(2, pageInfo.getBeginRow());
                pstmt.setInt(3, pageInfo.getPageSize());
                pstmt.setInt(4, pageInfo.getBeginRow());
            }else {
                pstmt.setInt(1, pageInfo.getBeginRow());
                pstmt.setInt(2, pageInfo.getPageSize());
                pstmt.setInt(3, pageInfo.getBeginRow());

            }

            rs = pstmt.executeQuery() ;

            while(rs.next()){
                Product bean = this.makeBean(rs);
                allData.add(bean);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(rs!=null){rs.close();}
                if(pstmt!=null){pstmt.close();}
                if(conn!=null){conn.close();}
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return allData ;
    }

    public List<Product> getPaginationData(Paging pageInfo) {
        Connection conn = null ;
        String sql = " select pnum, name, company, image01, image02, image03, stock, price, category, contents, point, inputdate ";
        sql += " from (";
        sql += " select pnum, name, company, image01, image02, image03, stock, price, category, contents, point, inputdate, ";
        sql += " rank() over(order by pnum desc) as ranking ";
        sql += " from products ";

        //mode 가 'all'이 아니면 where 절 추가 필요
        String mode = pageInfo.getMode();
        boolean bool = mode == null || mode.equals("null") || mode.isEmpty() || mode.equals("all");
        if (!bool) {
            sql += " where category = ?";
        }
        sql += " ) ";
        sql += " where ranking between ? and ? ";
        PreparedStatement pstmt = null ;
        ResultSet rs = null ;

        List<Product> allData = new ArrayList<>();
        try{
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);

            if (!bool) {
                pstmt.setString(1, mode);
                pstmt.setInt(2, pageInfo.getBeginRow());
                pstmt.setInt(3, pageInfo.getEndRow());
            }else {
                pstmt.setInt(1, pageInfo.getBeginRow());
                pstmt.setInt(2, pageInfo.getEndRow());
            }

            rs = pstmt.executeQuery() ;

            while(rs.next()){
                Product bean = this.makeBean(rs);
                allData.add(bean);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(rs!=null){rs.close();}
                if(pstmt!=null){pstmt.close();}
                if(conn!=null){conn.close();}
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return allData ;
    }
}

