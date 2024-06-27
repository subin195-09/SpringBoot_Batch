plugins {
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("plugin.jpa") version "1.9.24"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	kotlin("kapt") version "1.9.22"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.9.20"
	id("org.hibernate.orm") version "6.4.1.Final"
}

hibernate {
	enhancement {
		enableLazyInitialization = true
		enableDirtyTracking = true
		enableAssociationManagement = true
		enableExtendedEnhancement = false
	}
}

// allOpen 설정: 특정 애노테이션이 붙은 클래스를 컴파일 타임에 open 클래스로 만듦. 이는 JPA를 사용할 때 프록시 객체 생성을 위해 필요함
allOpen {
	// JPA 엔티티 클래스에 대해 open 클래스로 만들도록 설정
	annotation("jakarta.persistence.Entity")
	// JPA 상속 계층에서 사용되는 클래스에 대해 open 클래스로 만들도록 설정
	annotation("jakarta.persistence.MappedSuperclass")
	// JPA 내장 타입에 대해 open 클래스로 만들도록 설정
	annotation("jakarta.persistence.Embeddable")
}


group = "com.sample"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-batch")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.batch:spring-batch-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	// Postgresql: PostgreSQL 데이터베이스 드라이버
	runtimeOnly("org.postgresql:postgresql")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
