package com.challenger.modules.account;

import com.challenger.infra.mail.EmailService;

import com.challenger.modules.account.form.SignUpForm;
import com.challenger.modules.account.validator.SignUpFormValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;


import javax.validation.ConstraintViolation;
import javax.validation.ValidatorFactory;

import java.util.List;
import java.util.Set;

import static com.mysema.commons.lang.Assert.assertThat;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Transactional
class AccountControllerTest {

    @Autowired AccountRepository accountRepository;
    @Autowired AccountService accountService;
    @Autowired EmailService emailService;
    @Autowired SignUpFormValidator signUpFormValidator;


    @DisplayName("이메일 중복으로 에러 발생")
    @Test
    void 이메일_중복() {

        // given
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname("hee");
        signUpForm.setEmail("hee@gmail.com");
        signUpForm.setPassword("sdfh2347fdj");

        SignUpForm signUpForm2 = new SignUpForm();
        signUpForm2.setNickname("hee2");
        signUpForm2.setEmail("hee@gmail.com");
        signUpForm2.setPassword("sdfh2347fdhj89");

        // when
        accountService.processNewAccount(signUpForm);
        signUpFormValidator.validate(signUpForm2, errors);

        // then
        assertTrue(errors.hasFieldErrors());

    }


    @DisplayName("회원가입 정상")
    @Test
    public void 회원가입_정상 () throws Exception {
        //given
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setNickname("hee");
        signUpForm.setEmail("hee@gmail.com");
        signUpForm.setPassword("sdfh2347fdj");


        //when
        Account account = accountService.processNewAccount(signUpForm);
        accountService.login(account);

        //then
        assertEquals(account.getNickname(), "hee");
        assertNotEquals(account.getPassword(), "sdfh2347fdj");
        assertNotNull(account.getEmailCheckToken());

        }

    Errors errors = new Errors() {
        @Override
        public String getObjectName() {
            return null;
        }

        @Override
        public void setNestedPath(String nestedPath) {

        }

        @Override
        public String getNestedPath() {
            return null;
        }

        @Override
        public void pushNestedPath(String subPath) {

        }

        @Override
        public void popNestedPath() throws IllegalStateException {

        }

        @Override
        public void reject(String errorCode) {

        }

        @Override
        public void reject(String errorCode, String defaultMessage) {

        }

        @Override
        public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {

        }

        @Override
        public void rejectValue(String field, String errorCode) {

        }

        @Override
        public void rejectValue(String field, String errorCode, String defaultMessage) {

        }

        @Override
        public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {

        }

        @Override
        public void addAllErrors(Errors errors) {

        }

        @Override
        public boolean hasErrors() {
            return false;
        }

        @Override
        public int getErrorCount() {
            return 0;
        }

        @Override
        public List<ObjectError> getAllErrors() {
            return null;
        }

        @Override
        public boolean hasGlobalErrors() {
            return false;
        }

        @Override
        public int getGlobalErrorCount() {
            return 0;
        }

        @Override
        public List<ObjectError> getGlobalErrors() {
            return null;
        }

        @Override
        public ObjectError getGlobalError() {
            return null;
        }

        @Override
        public boolean hasFieldErrors() {
            return false;
        }

        @Override
        public int getFieldErrorCount() {
            return 0;
        }

        @Override
        public List<FieldError> getFieldErrors() {
            return null;
        }

        @Override
        public FieldError getFieldError() {
            return null;
        }

        @Override
        public boolean hasFieldErrors(String field) {
            return false;
        }

        @Override
        public int getFieldErrorCount(String field) {
            return 0;
        }

        @Override
        public List<FieldError> getFieldErrors(String field) {
            return null;
        }

        @Override
        public FieldError getFieldError(String field) {
            return null;
        }

        @Override
        public Object getFieldValue(String field) {
            return null;
        }

        @Override
        public Class<?> getFieldType(String field) {
            return null;
        }
    };

}

