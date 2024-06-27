package com.sample.spring_batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBatchApplication

fun main(args: Array<String>) {
	runApplication<SpringBatchApplication>(*args)
}
