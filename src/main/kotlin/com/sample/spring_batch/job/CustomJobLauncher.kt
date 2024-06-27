package com.sample.spring_batch.job

import com.sample.spring_batch.config.Logging
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class CustomJobLauncher(
  private val jobLauncher: JobLauncher,
  private val jobRepository: JobRepository,
  private val applicationContext: ApplicationContext,
) : Logging {
  private val jobList = listOf(
    "changeKeywordNameJob"
    // job 추가
  )

  private var currentJobIndex = 0

  fun launchNextJob() {
    if (currentJobIndex >= jobList.size) {
      logger.info("Job이 완료되었습니다.")
      return
    }

    val jobName = jobList[currentJobIndex++]
    val job = applicationContext.getBean(jobName, Job::class.java)
    val jobParameters = JobParametersBuilder()
      .addLong("time", System.currentTimeMillis())
      .toJobParameters()

    jobRepository.getLastJobExecution(jobName, jobParameters)?.let {
      logger.error("$jobName 이미 실행중입니다")
    } ?: run {
      jobLauncher.run(job, jobParameters)
    }
  }

}