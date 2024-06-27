package com.sample.spring_batch.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

/**
 * JPA 구성 클래스
 *
 * - JPA 감사(Auditing) 기능을 활성화. 엔티티의 생성 및 수정 시간을 자동으로 관리
 */
@Configuration
@EnableJpaAuditing
class JpaConfig{
}