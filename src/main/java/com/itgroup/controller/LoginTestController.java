package com.itgroup.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginTestController implements Initializable {
    @FXML
    private TextField fxmlId;
    @FXML
    private PasswordField fxmlPassword;
    @FXML
    private Label fxmlBinding;
    @FXML
    private Button btnLogin, btnReset;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnLogin.setOnAction((event) -> loginAction(event));
        // fxml 파일에서 onAction="#loginAction" 을 btnLogin Button 문장에 추가하는 것과 같은 역할
        btnReset.setOnAction((event) -> resetAction(event));
        // fxml 파일에서 onAction="#resetAction" 을 btnReset Button 문장에 추가하는 것과 같은 역할

        //속성 바인딩 기능: 특정 컨트롤의 속성 값이 변경되면, 자동적으로 다른 컨트롤의 속성 값이 변경됨
        //참고: Bindings 클래스
        fxmlBinding.textProperty().bind(fxmlId.textProperty());
        //(개인)textProperty(): 객체의 텍스트 속성을 나타냄. 즉 Button 이면 Button 반환, TextField 라면  TextField 반환. 아마도?
    }

    private void resetAction(ActionEvent event) {  //초기화 버튼을 누르면 아이디와 비밀번호 창 초기화
        fxmlId.clear();
        fxmlPassword.clear();
        fxmlId.requestFocus();  //아이디 입력 란에 포커스 주기. 초기화를 하면 자동적으로 아이디 입력 창에 커서가 깜빡인다.
    }

    private void loginAction(ActionEvent event) {  //로그인 버튼을 누르면 로그인
        boolean isCheck = this.checkInputState();

        if (isCheck) {  //입력 조건(isCheck)에 충족
            boolean loginCheck = this.getAccountCheck();
            if (loginCheck) {
                System.out.println("로그인 성공");
            }else{
                System.out.println("로그인 실패");
            }
        }else{  //입력 조건에 맞지 않음
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("데이터 확인");
            alert.setHeaderText("입력 데이터 누락");
            alert.setContentText("아이디 또는 비밀번호가 누락되었습니다.");
            alert.showAndWait();
        }
    }

    private boolean getAccountCheck() {
        //입력한 내용과 실제 계정 정보가 동일한지 체크
        String id = "hong", password = "1234";
        if (fxmlId.getText().equals(id) && fxmlPassword.getText().equals(password)) {
            return true;
        }else{
            return false;
        }

    }

    private boolean checkInputState() {
        //유효성 검사(validation check): 입력되어야 할 데이터들이 제대로 입력이 되었는지를 확인하는 절차
        //사용처: 로그인, 회원가입, 게시물 등록, 상품 등록 등
        //입력 양식에 관한 유효성 검사 수행

        if(fxmlId.getText().isEmpty() ||
                fxmlId == null ||
                fxmlPassword.getText().isEmpty() ||
                fxmlPassword == null){
            return false;

        }else{
            return true;
        }
    }


}
