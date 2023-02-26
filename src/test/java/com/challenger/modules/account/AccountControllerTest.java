package com.challenger.modules.account;

import com.challenger.infra.config.AppConfig;
import com.challenger.infra.mail.EmailService;

import com.challenger.modules.account.form.SignUpForm;
import com.challenger.modules.account.validator.SignUpFormValidator;
import lombok.Builder;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Transactional
class AccountControllerTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    EmailService emailService;
    @Autowired
    SignUpFormValidator signUpFormValidator;

    @Autowired
    PasswordEncoder passwordEncoder;

    @DisplayName("회원가입 - 이메일 중복으로 에러 발생")
    @Test
    void 이메일_중복() {

        // given
        //회원가입1 양식 생성
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname("hee");
        signUpForm.setEmail("hee@gmail.com");
        signUpForm.setPassword("sdfh2347fdj");

        //회원1 회원가입
        Account account = accountService.processNewAccount(signUpForm);
        System.out.println(signUpForm.getPassword());
        // 유효성 검사
        Errors errors = new BeanPropertyBindingResult(signUpForm, "signUpForm");
        signUpFormValidator.validate(signUpForm, errors);

        //회원가입1 제대로 Insert되었는지 확인
        assertEquals(account.getEmail(), "hee@gmail.com");
        assertEquals(accountRepository.existsByEmail(signUpForm.getEmail()), true);


        //when
        //회원가입2 양식 생성
        SignUpForm signUpForm2 = new SignUpForm();
        signUpForm2.setNickname("hee2");
        signUpForm2.setEmail("hee@gmail.com");
        signUpForm2.setPassword("sdfh2347fdhj89");

        System.out.println("회원1계정 : " + accountRepository.findByNickname("hee"));

        // 유효성 검사
        Errors errors2 = new BeanPropertyBindingResult(signUpForm2, "signUpForm2");
        signUpFormValidator.validate(signUpForm2, errors2);


        // then
        assertTrue(errors.hasFieldErrors());

    }


    @DisplayName("회원가입 정상")
    @Test
    public void 회원가입_정상() throws Exception {
        //given
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname("hee");
        signUpForm.setEmail("hee@gmail.com");
        signUpForm.setPassword("sdfh2347fdj");


        //when
        Account account = accountService.processNewAccount(signUpForm);

        //then - 패스워드 암호화 여부 확인
        assertEquals(account.getNickname(), "hee");
        assertNotNull(account.getEmailCheckToken());
        passwordEncoder.matches("sdfh2347fdj", passwordEncoder.encode("sdfh2347fdj"));


    }

    @DisplayName("회원 가입 - forEach 활용")
    @Builder
    @Test
    public void test1() throws Exception {

        //회원목록 관리는 HashMap으로 적용 - Key는 String, Data는 객체(SignUpForm) - 이때 Key는 유니크한 값
        //HashMap을 조회하여 회원별 이메일 중복체크를 진행하고, 회원가입을 진행한다.
        //이메일 중복확인을 마친 회원들은 MySQL 또는 MariaDB를 사용해 Table에 등록시킨다.

        HashMap<String, Account> accountMap = new HashMap<>();


        // 순서성 보장되는 리스트 선언
        List<String> nickList = new ArrayList<>(Arrays.asList("test1", "test2", "test3"));
        List<String> emailList = new ArrayList<>(Arrays.asList("test1@gmail.com", "test1@gmail.com", "test2@gmail.com"));
        List<String> passwordList = new ArrayList<>(Arrays.asList("pass1", "pass2", "pass3"));

        // config 호출
//        AppConfig appConfig = new AnnotationConfigApplicationContext(AppConfig.class).getBean(AppConfig.class);
        //인덱스
        AtomicInteger counter = new AtomicInteger(0);
        // 닉네임 리스트개수만큼 for문 돌림
        nickList.forEach((s) -> {

            //회원가입
            SignUpForm signUpForm = new SignUpForm();
            signUpForm.setNickname(s);
            signUpForm.setEmail(emailList.get(counter.get()));
            signUpForm.setPassword(passwordList.get(counter.get()));

            //유효성 검증
            Errors errors = new BeanPropertyBindingResult(signUpForm, "signUpForm");
            signUpFormValidator.validate(signUpForm, errors);

//            PasswordEncoder encoder = appConfig.passwordEncoder();
            String pw = passwordList.get(counter.get());

            if (!errors.hasFieldErrors()) {
                if (passwordEncoder.matches(pw, passwordEncoder.encode(pw))) {
                    Account account = accountService.processNewAccount(signUpForm);
                    accountMap.put(s, account);
                }
            }
            counter.getAndIncrement();

        });

        accountMap.forEach((key, value) -> {
            System.out.println("닉네임: " + key + " 이메일: " + value.getEmail() + " 패스워드: " + value.getPassword());
        });

    }


}

