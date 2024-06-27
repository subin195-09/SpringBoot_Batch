package com.sample.spring_batch.repository

import com.sample.spring_batch.entity.FailedBatchData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FailedBatchDataRepository : JpaRepository<FailedBatchData, Long> {
}