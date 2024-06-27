<h1>Spring Boot - Batch </h1>
<i align="center">해당 Repo는 Spring Boot, Spring Batch 로 만들어진 스케줄러에 대한 학습을 위해 만들어졌습니다. </i>


<h4 align="center">
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=Kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white" />
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=PostgreSQL&logoColor=white" />
  <img src="https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=Redis&logoColor=white" />
  <img src="https://img.shields.io/badge/ApacheKafka-231F20?style=flat-square&logo=Apache Kafka&logoColor=white" />
  <img src="https://img.shields.io/badge/Sentry-362D59?style=flat-square&logo=Sentry&logoColor=white" />
  <img src="https://img.shields.io/badge/Datadog-632CA6?style=flat-square&logo=Datadog&logoColor=white" />
</h4>


## 🛫 사전 세팅

- PostgreSQL (with Docker, same as Spring_MVC)
    - Spring_MVC 의 docker-compose.yaml로 docker구동 필요
    - ([Spring_MVC - github](https://github.com/subin195-09/SpringBoot_MVC))
- (IntelliJ 기준) Edit Configuration 에서 Active profile 을 `local` 로 설정

## 🗂️ **Structure**
```bash
└─ src
	└── main
		├── kotlin/com/sample/spring_batch․․․# 소스 코드 루트 디렉토리
		│ ├── common․․․․․․․․․․․․․․․․․․․․․․# 공통 코드
		│ ├── config․․․․․․․․․․․․․․․․․․․․․․# 설정 관련 코드 (DB 연결, 스케줄러,JPA, Redis)
		│ ├── entity․․․․․․․․․․․․․․․․․․․․․․# Spring_MVC DB와 매팅되는 엔티티
		│ ├── job․․․․․․․․․․․․․․․․․․․․․․․․․# Job 정의 코드
		│ ├── listener․․․․․․․․․․․․․․․․․․․․# Batch Job 리스너
		│ ├── repository․․․․․․․․․․․․․․․․․․# JPA 레포지토리
		│ ├── service․․․․․․․․․․․․․․․․․․․․․# 서비스 클래스 (Batch 에서 사용하는 비즈니스 로직)
		│ └──(SpringBatchApplication․kt)․․# 애플리케이션 시작 파일 (main)
		└── resources․․․․․․․․․․․․․․․․․․․․․․․․# 애플리케이션 리소스
		└── config․․․․․․․․․․․․․․․․․․․․․․․․․․․# 애플리케이션 설정
	└─(build․gradle․kts)․․․․․․․․․․․․․․․․․․․․․# Gradle 의 Kotlin DSL 을 사용하여 작성된 빌드 구성 스크립트
```
