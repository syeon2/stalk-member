## 👥 Stalk Member Service

> Stalk 프로젝트에서 회원 정보 및 인증을 처리하는 마이크로 서비스입니다.

### 🌱 핵심 기능

- 회원 가입 (Async 활용한 이메일 인증)
- 로그인 (Spring Security & JWT Token)
- 회원 정보 수정
- 회원 간 Follow 기능 (Follower, Following 조회)

---

### 🌱 DataBase

<img src="https://i.ibb.co/rZg3ZxN/Screenshot-2024-07-29-at-16-38-46.png" alt="Screenshot-2024-07-29-at-16-38-46" border="0">

---

### 🌱 Project Architecture

#### 🥕 Infra Structure

<img src="https://i.ibb.co/7kKZjFg/member-service.jpg" alt="member-service" border="0">

#### 🥕 Multi-Module Structure

```
.
├── bootstrap
├── presentation
├── application
├── infrastructure
│   ├── persistence
│   ├── redis
│   └── smtp
├── common
│   ├── encrypt
│   └── exception
├── domain
└── support
    ├── monitor
    └── msa-core
```

---

### 🌱 구현 및 트러블슈팅 사례

- [@Async을 활용한 이메일 수신 API 개선 방안](https://syeon2.github.io/devlog/tosstock-mail-sender.html)
    - 약 4s(4000ms) -> 약 300ms까지 응답 속도 개선
