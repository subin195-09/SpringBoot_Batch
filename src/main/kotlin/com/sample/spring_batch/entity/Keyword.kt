package com.sample.spring_batch.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "keyword")
class Keyword : BaseEntity() {

  @Id
  @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
  val id: UUID = UUID.randomUUID()

  // 이름
  @Column(name = "email", columnDefinition = "VARCHAR(255)", nullable = true)
  var name: String? = null
}