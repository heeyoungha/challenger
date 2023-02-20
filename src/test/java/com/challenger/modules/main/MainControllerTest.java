package com.challenger.modules.main;

import com.challenger.modules.account.Account;
import com.challenger.modules.account.AccountRepository;
import com.challenger.modules.account.AccountService;
import com.challenger.modules.account.form.SignUpForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;


@SpringBootTest
class MainControllerTest {

    @Autowired AccountService accountService;
    @Autowired AccountRepository accountRepository;

    @BeforeEach
    void beforeEach() {
        Account account = new Account();
        account.setNickname("hee");
        account.setEmail("hee@email.com");
        account.setPassword("asdflks123");
        accountService.sendSignUpConfirmEmail(account);
    }

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @DisplayName("이메일로 로그인 성공")
    @Test
    void login_with_email() throws Exception {
        //given

        //when


        //then


    }

    @DisplayName("닉네임으로 로그인 성공")
    @Test
    void login_with_nickName() throws Exception {
        //given

        //when

        //then

    }

    @DisplayName("로그인 실패")
    @Test
    void login_fail() throws Exception {
        //given

        //when

        //then

    }

    @DisplayName("로그아웃")
    @Test
    void logout() throws Exception {
        //given

        //when

        //then

    }

}
