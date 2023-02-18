- 프로젝트 실행 시 maven>wikihow>Lifecycle>compile 후 실행부탁드립니다.
  - Q클래스 생성 위함입니다.
# 📌 challenger 소개
-  강의자료에 나온 스터디자료를 바탕으로 클론코딩한 프로젝트 입니다.
> challenger는 '관심사를 기반으로 한 모임 및 챌린지를 이용하는 서비스'입니다.
> 타 유저들과 함께 이용하고 싶은 모임을 생성하고, 모임 안에서 여러 챌린지들을 함께 수행하는 서비스를 제공합니다.

- 🎥 [챌린저 시연 영상](https://youtu.be/R9UEh0GK5l4)

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
### 서비스 요약
- 유저 프로필에서 알림받고 싶은 관심사 설정
- 동일한 관심사가 설정된 모임 생성 시 웹/이메일로 알림받기
- 관심사로 검색하여 모임 조회
- 챌린지 참여 시 출석체크 기능 제공
- 관리중인 모임, 참여할 챌린지 조회 기능


### 핵심 기능

### 0️⃣ 회원가입

__`1. 패스워드 암호화`__
- **PasswordEncoder 사용** :pushpin: [코드 확인]( https://github.com/hy-HA/challenger/commit/53a5b62717be4080904331f362da6c0bf5f0467d)
    - PasswordEncoderFactories.createDelegatingPasswordEncoder() 활용

__`2. 이메일로 회원가입 인증`__
- **Gmail SMTP, MailSender 활용** :pushpin: [코드 확인](https://github.com/hy-HA/challenger/commit/7cedab8436589944890edd3dad17cc3106a19cef)
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

__`3. 회원가입 유효성 검사`__ : pushpin: [코드 확인](https://github.com/hy-HA/challenger/commit/f34c6118db533fd9d54ec8eff0ff5bd069f1bb04)
- **JSR303, 커스텀검증 활용**
    - JSR303을 활용하여 @Length(min=4, max=10) @Valid 등으로 검증
    - Validator 스프링 인터페이스를 구현하여 커스텀 검증

__`4. 로그인 / 로그아웃`__ : pushpin: [코드 확인](https://github.com/hy-HA/challenger/commit/778ebbaeb6167c24a2e8d69e2051f208cff6c0bb)
- 스프링 시큐리티로 로그인, 로그아웃 설정

### 1️⃣ 프로필

__`1. 소유자 여부에 따라 화면 다르게 노출`__
- 타 유저
    - account의 속성 연동하여 보여주기
- 유저 자신의 프로필
    - 수정 버튼 보이기

__`2. 수정완료 메세지 팝업 노출`__
- **RedirectAttribute 클래스 사용**

__`3. 프로필 알람 설정`__
- **부트스트랩 Froms의 Switches 활용** :pushpin: [코드 확인](Url)
    - '스터디 생성, 참가 신청 결과, 참여중인 스터디'에 대한 정보 변경 시 알림 받기
    - 알림을 이메일로 받을지, 웹 알림 메시지로 받을지 선택.

### 2️⃣ 관심사 기능

__`1. CSRF 토큰 설정`__
- **Tagify, ajax**활용
    - 태그 형식으로 등록, 조회, 삭제

__`2. CSRF 토큰 설정`__
- 타임리프 자바스크립트 템플릿으로 Ajax 호출시 CSRF 토큰 설정

### 3️⃣ 모임 기능

__`1. 모임 개설`__
- **summernote** 활용

__`2. 모임 조회`__
- **EntityGraph 활용**
    - 쿼리 개수를 줄이고 join을 해서 가져오기
    -  Left outer join으로 연관 데이터를 한번에 조회

### 4️⃣ 챌린지 기능

__`1. 챌린지 삭제 기능`__
- Html의 form에서 delete 기능 사용
- HiddenHttpMethodFilter의 "_method" 사용

### 5️⃣ 알림 기능

__`1. 알림 인프라 설정`__
- **ApplicationEventPublisher와 스프링 @Async 기능**
    - ApplicationEventPublisher와 스프링 @Async 기능을 사용해서 비동기 이벤트 기반으로 알림 처리.
    - 주요 로직 응답 시간에 영향을 주지 않기.
    - 코드를 최대한 주요 로직에 집중하고 알림 처리 로직은 분리.

__`2. 모임 개설 알림`__
- 모임을 만들때가 아니라 공개할 때 알림
- QueryDSL 설정
    - 타입 세이프하게 JPA 쿼리를 작성할 수 있다.
- 스프링 데이터 JPA와 QueryDSL 연동
    - QuerydslPredicateExecutor 인터페이스 추가
    - Predicate 사용하기

### 6️⃣ 검색 기능

__`1. 검색 기능`__
- **페이징 적용**
    - 스프링 데이터 JPA가 제공하는 Pageable 사용



## 서비스 흐름도
<img width="581" alt="서비스흐름도" src="https://user-images.githubusercontent.com/73115727/218314831-0c378a9a-970a-47db-bea2-f788a793b811.png">

## DB설계
<img width="885" alt="DB설계" src="https://user-images.githubusercontent.com/73115727/218314824-df04dbdf-90ab-4a73-b9d6-28f86cf76f6d.png">

<br>

<details>
<summary><b> ✅ 프로젝트 소개 펼치기</b></summary>
<div markdown="1">
<img width="80%" alt="1" src="https://user-images.githubusercontent.com/73115727/218322015-f73e374e-eaa4-4d92-b393-ffa529b81933.png">
<img width="80%" alt="2" src="https://user-images.githubusercontent.com/73115727/218322023-e197609c-2637-44e7-ac97-f8b86b34a3fc.png">
<img width="80%" alt="3" src="https://user-images.githubusercontent.com/73115727/218322027-fd20b95b-4378-4f21-97cd-7c4d944bb186.png">
<img width="799" alt="4" src="https://user-images.githubusercontent.com/73115727/218323595-022e1a42-c731-45ed-a0fb-8c6e81d27f07.png">
<img width="789" alt="5" src="https://user-images.githubusercontent.com/73115727/218323593-2eeb875a-1dc7-41c0-aef1-ff241419cb58.png">
<img width="80%" alt="6" src="https://user-images.githubusercontent.com/73115727/218322036-b5e37bcd-5a31-48d0-992e-928bbab7862d.png">
<img width="80%" alt="7" src="https://user-images.githubusercontent.com/73115727/218322037-5204fcfa-787c-4a52-8c8c-8a1c4ed75f03.png">
<img width="80%" alt="8" src="https://user-images.githubusercontent.com/73115727/218322039-5088a20b-3162-4a6e-81ca-dcd7888376d6.png">
<img width="80%" alt="9" src="https://user-images.githubusercontent.com/73115727/218322044-3bebfc17-1973-42be-b96f-ab003643c28e.png">
<img width="80%" alt="10" src="https://user-images.githubusercontent.com/73115727/218322045-162744cf-a19c-4e77-b9ed-6eb2378108d0.png">

![12](https://user-images.githubusercontent.com/73115727/218322631-1f70111d-ce2b-4624-8ba8-9970785a341d.png)
![13](https://user-images.githubusercontent.com/73115727/218322632-5ce666ac-4eb5-4d24-97af-230bcee43f8d.png)
![14](https://user-images.githubusercontent.com/73115727/218322634-1db2c0f7-16d1-49e7-991b-1c07a795ae27.png)
![15](https://user-images.githubusercontent.com/73115727/218322635-98805c9d-ab8f-47c1-9db1-6e4aed600aba.png)
![16](https://user-images.githubusercontent.com/73115727/218322639-d6a128c6-f1b1-4bd4-af28-8f765c81402c.png)
![17](https://user-images.githubusercontent.com/73115727/218322640-c3dddf6d-bc16-4500-a58c-7327b0ea9f54.png)
![18](https://user-images.githubusercontent.com/73115727/218322642-c91da12a-1a4d-442c-877c-ffeade6e9fe0.png)
</div>
</details>
