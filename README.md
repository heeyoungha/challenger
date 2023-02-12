# 📌 challenger 소개
-  강의자료에 나온 스터디자료를 바탕으로 클론코딩한 프로젝트 입니다.
> challenger는 '관심사를 기반으로 한 모임 및 챌린지를 이용하는 서비스'입니다.
> 타 유저들과 함께 이용하고 싶은 모임을 생성하고, 모임 안에서 여러 챌린지들을 함께 수행하는 서비스를 제공합니다.

- 🎥 [챌린저 시연 영상](https://youtu.be/R9UEh0GK5l4)

### 서비스 흐름도
<img width="581" alt="서비스흐름도" src="https://user-images.githubusercontent.com/73115727/218314831-0c378a9a-970a-47db-bea2-f788a793b811.png">
### DB설계
<img width="885" alt="DB설계" src="https://user-images.githubusercontent.com/73115727/218314824-df04dbdf-90ab-4a73-b9d6-28f86cf76f6d.png">

</br>

## 🛠 개발환경

### protocol Tool
- 인텔리J IDEA

### 개발언어/framework
- JAVA, Spring/Spring boot/Spring Security, Jquery, BootStrap, JPA, QueryDSL

### 데이터베이스
- PostgreSQL

### 개발 서버환경
- linux

## 🔍 서비스 소개
### 핵심 서비스
- 유저 프로필에서 알림받고 싶은 관심사 설정
- 동일한 관심사가 설정된 모임 생성 시 웹/이메일로 알림받기
- 관심사로 검색하여 모임과 챌린지 조회
- 챌린지 참여 시 출석체크 기능 제공
- 관리중인 모임, 참여할 챌린지 조회 기능

<br>

<details>
<summary><b>핵심 기능 개발 설명 펼치기</b></summary>
<div markdown="1">

### 1️⃣ 회원가입

__`1. 패스워드 암호화`__
- **PasswordEncoder 사용** :pushpin: [코드 확인](Url)
    - PasswordEncoderFactories.createDelegatingPasswordEncoder() 활용

__`2. 이메일로 회원가입 인증`__
- **Gmail SMTP, MailSender 활용** :pushpin: [코드 확인](Url)
    - 목적
        - 존재하지 않는 가상의 이메일로 가입하는 것을 방지하기 위함
        - 서비스를 사용중인 진짜 유저수를 확보 가능
    - 서비스 흐름
        1. 유저가 회원가입을 완료하면, 유저 이메일로 링크가 발송.
        2. 유저가 이메일에서 링크를 클릭
        3. 서버에서 토큰과 이메일 값에 대한 요청을 처리.
            - 이메일과 토큰이 정확한 경우 가입 완료 처리
    - 구글 Gmail을 SMTP 서버로 사용하여 이메일 보내기
    - 스프링 MailSender 인터페이스 사용
        - JavaMailSender의 MimeMessage 사용

__`3. 알림 설정`__
- **부트스트랩 Froms의 Switches 활용** :pushpin: [코드 확인](Url)
    - '스터디 생성, 참가 신청 결과, 참여중인 스터디'에 대한 정보 변경 시 알림 받기
    - 알림을 이메일로 받을지, 웹 알림 메시지로 받을지 선택.

### 1️⃣ 관심사 설정

__`1. CSRF 토큰 설정`__
- 타임리프 자바스크립트 템플릿으로 Ajax 호출시 CSRF 토큰 설정

__`2. 스터디 조회`__
- **EntityGraph 활용**

### 1️⃣ 알림 설정

__`1. 알림 인프라 설정`__
- **ApplicationEventPublisher와 스프링 @Async 기능**
    - ApplicationEventPublisher와 스프링 @Async 기능을 사용해서 비동기 이벤트 기반으로 알림 처리.
    - 주요 로직 응답 시간에 영향을 주지 않기.
    - 코드를 최대한 주요 로직에 집중하고 알림 처리 로직은 분리.


__`2. 스터디 개설 알림`__
- 스터디를 만들때가 아니라 공개할 때 알림
- QueryDSL 설정
    - 타입 세이프하게 JPA 쿼리를 작성할 수 있다.
- 스프링 데이터 JPA와 QueryDSL 연동
    - QuerydslPredicateExecutor 인터페이스 추가
    - Predicate 사용하기

### 1️⃣ 검색 기능

__`1. 검색 기능`__
- **페이징 적용**
    - 스프링 데이터 JPA가 제공하는 Pageable 사용


<br>

</div>
</details>

<br>
