package com.sample.spring_batch.listener

import com.sample.spring_batch.config.Logging
import com.sample.spring_batch.job.CustomJobLauncher
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class JobCompletionNotificationListener(
  private val customJobLauncher: CustomJobLauncher
) : JobExecutionListener, Logging {

  override fun beforeJob(jobExecution: JobExecution) {
    logger.info("[${jobExecution.jobInstance.jobName}] Started At ${Instant.now()}")
  }

  override fun afterJob(jobExecution: JobExecution) {
    logger.info("[${jobExecution.jobInstance.jobName}] Ended At ${Instant.now()}")

    // 스킵된 항목의 수를 계산
    val totalSkips = jobExecution.stepExecutions.sumOf { it.skipCount }
    val skipMessage = if (totalSkips > 0) " with $totalSkips skipped items." else ""

    logger.warn("[${jobExecution.jobInstance.jobName}] $skipMessage")

    // 해당되는 jobLauncher 의 nextJob 실행
    customJobLauncher.launchNextJob()
  }
}