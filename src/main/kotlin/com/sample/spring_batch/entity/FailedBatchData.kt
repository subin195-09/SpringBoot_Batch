package com.sample.spring_batch.entity

import com.sample.spring_batch.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "batch_fail_data")
class FailedBatchData : BaseEntity() {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "batch_fail_data_seq")
  @Column(name = "id", updatable = false, nullable = false)
  val id: Long = 0

  @Column(name = "email", columnDefinition = "VARCHAR(255)", nullable = true)
  var detail: String? = null
}