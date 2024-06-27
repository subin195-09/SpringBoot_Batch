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

  fun launchNextJob() {
    jobList.forEach { jobName ->
      val job = applicationContext.getBean(jobName, Job::class.java)
      val jobParameters = JobParametersBuilder()
        .addLong("time", System.currentTimeMillis())
        .toJobParameters()

      val lastJobExecution = jobRepository.getLastJobExecution(jobName, jobParameters)
      if (lastJobExecution !== null && lastJobExecution.isRunning) {
        logger.error("$jobName 이미 실행중입니다")
      } else {
        jobLauncher.run(job, jobParameters)
      }
      logger.info("모든 job이 완료되었습니다")
    }
  }

}