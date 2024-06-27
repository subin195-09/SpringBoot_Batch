package com.sample.spring_batch.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 공통 로깅 인터페이스 정의
 *
 * - `Logging`: 클래스 로그를 위한 `logger` 속성을 제공합니다. `LoggerFactory`를 사용해 클래스명으로 로거를 생성합니다.
 *
 * 사용예시
 *
 * 사용 클래스에서 하단처럼 상속받아서 사용합니다
 *
 * @Service
 * class ExternalSyncService(
 *   private val webClient: WebClient,
 *   private val globalEnvService: GlobalEnvService,
 * ) : Logging {
 */
interface Logging {
  val logger: Logger get() = LoggerFactory.getLogger(this::class.java)
}