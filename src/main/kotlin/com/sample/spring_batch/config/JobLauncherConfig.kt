package com.sample.spring_batch.config

import com.sample.spring_batch.job.CustomJobLauncher
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class JobLauncherConfig(
  private val customJobLauncher: CustomJobLauncher
) : Logging {
  @Scheduled(cron = "0 */30 * * * *")
  fun jobScheduler() {
    customJobLauncher.launchNextJob()
  }

}