package com.sample.spring_batch.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

  //  등록일시
  @CreatedDate
  @Column(name = "created_datetime", columnDefinition = "TIMESTAMP", nullable = false, updatable = false)
  var createdDatetime: Instant = Instant.now()

  //  수정일시
  @LastModifiedDate
  @Column(name = "updated_datetime", columnDefinition = "TIMESTAMP")
  var updatedDatetime: Instant = Instant.now()
}