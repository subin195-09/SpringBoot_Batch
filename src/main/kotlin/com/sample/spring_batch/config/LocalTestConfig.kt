package com.sample.spring_batch.config

import com.sample.spring_batch.entity.Keyword
import com.sample.spring_batch.repository.KeywordRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("local")
class LocalTestConfig(
  private val keywordRepository: KeywordRepository
) {

  @Bean
  fun dataSourceInitializer(): CommandLineRunner {
    return CommandLineRunner {
      for (i in 0 until 10) {
        keywordRepository.save(Keyword())
      }
      // name 이 null 인 데이터 추가
    }
  }
}