package com.itgroup.dao;

import com.itgroup.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDao extends SuperDao {
    //Person 목록을 저장할 리스트 컬렉션
    //실제 프로그램에서는 Database 에서 읽어 들이기
    private List<Person> personList = null;
    private int totalCount = 0;  //컬렉션의 전체 개수

    public PersonDao() {  //인스턴스 변수를 초기화 하기
        personList = new ArrayList<>();
        this.fillData();
    }

    private void fillData() {
        //원칙은 Database 로부터 읽어와야 함
        for (int i = 0; i <= 12; i++) {
            personList.add(new Person(""+(3*i-2), "윤성"+i, "장"));
            personList.add(new Person(""+(3*i-1), "윤건"+i, "서"));
            personList.add(new Person(""+(3*i-0), "무현"+i, "박"));
        }
        totalCount = personList.size();
        System.out.println("totalCount: " + totalCount);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<Person> getAllData(int beginRow, int endRow) {
        return personList.subList(beginRow, endRow);  //beginRow 이상 endRow 미만
        //전체 리스트에서 특정 부분만 추출할 때 subList 활용하면 된다.

    }
}
