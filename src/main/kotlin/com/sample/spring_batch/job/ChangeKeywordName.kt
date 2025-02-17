package com.sample.spring_batch.job

import com.sample.spring_batch.common.CommonConstant.Companion.BATCH_SIZE
import com.sample.spring_batch.common.CommonConstant.Companion.SKIP_LIMIT
import com.sample.spring_batch.config.Logging
import com.sample.spring_batch.entity.Keyword
import com.sample.spring_batch.listener.JobCompletionNotificationListener
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.Instant

@Configuration
@EnableBatchProcessing(dataSourceRef = "postgresqlDataSource", transactionManagerRef = "postgresqlTransactionManager")
class ChangeKeywordName(
  private val jobCompletionNotificationListener: JobCompletionNotificationListener
) : Logging {

  @Bean
  fun changeKeywordNameJob(
    jobRepository: JobRepository,
    @Qualifier("postgresqlTransactionManager") transactionManager: PlatformTransactionManager,
    reader: JpaCursorItemReader<Keyword>,
    processor: ItemProcessor<Keyword, Keyword>,
    writer: JpaItemWriter<Keyword>
  ): Job {
    return JobBuilder("changeKeywordNameJob", jobRepository)
      .start(changeKeywordNameStep(jobRepository, transactionManager, reader, processor, writer))
      .listener(jobCompletionNotificationListener)
      .build()
  }

  @Bean
  fun changeKeywordNameStep(
    jobRepository: JobRepository,
    @Qualifier("postgresqlTransactionManager") transactionManager: PlatformTransactionManager,
    reader: JpaCursorItemReader<Keyword>,
    processor: ItemProcessor<Keyword, Keyword>,
    writer: JpaItemWriter<Keyword>
  ): Step {
    return StepBuilder("changeKeywordNameStep", jobRepository)
      .chunk<Keyword, Keyword>(BATCH_SIZE, transactionManager)
      .reader(reader)
      .processor(processor)
      .writer(writer)
      .faultTolerant()
      .skip(Exception::class.java)
      .skipLimit(SKIP_LIMIT)
      .transactionManager(transactionManager)
      .build()

  }

  @Bean
  fun changeKeywordNameReader(
    @Qualifier("postgresqlEntityManagerFactory") entityManagerFactory: EntityManagerFactory
  ): JpaCursorItemReader<Keyword> {
    val jpqlQuery = """
      SELECT k FROM Keyword k
      WHERE k.name IS NULL
    """.trimIndent()

    return JpaCursorItemReaderBuilder<Keyword>()
      .name("changeKeywordNameReader")
      .entityManagerFactory(entityManagerFactory)
      .queryString(jpqlQuery)
      .build()
  }

  @Bean
  fun changeKeywordNameProcessor(): ItemProcessor<Keyword, Keyword> {
    return ItemProcessor { keyword ->
      keyword.apply {
        name = "Random Name : ${Instant.now()}"
      }
    }
  }

  @Bean
  fun changeKeywordNameWriter(
    @Qualifier("postgresqlEntityManagerFactory") entityManagerFactory: EntityManagerFactory
  ): JpaItemWriter<Keyword> {
    return JpaItemWriterBuilder<Keyword>()
      .entityManagerFactory(entityManagerFactory)
      .build()
  }
}