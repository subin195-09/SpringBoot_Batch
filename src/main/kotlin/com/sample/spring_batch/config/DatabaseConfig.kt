package com.sample.spring_batch.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import org.springframework.core.io.ClassPathResource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.init.DataSourceInitializer
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import java.util.HashMap
import javax.sql.DataSource

@Configuration
class DatabaseConfig(
  private val env: Environment
) {
  // DataSource configuration
  @Bean
  fun postgresqlDataSource(): DataSource {
    val hikariConfig = HikariConfig().apply {
      jdbcUrl = env.getRequiredProperty("spring.datasource.url")
      username = env.getRequiredProperty("spring.datasource.username")
      password = env.getRequiredProperty("spring.datasource.password")
      driverClassName = env.getRequiredProperty("spring.datasource.driverClassName")

      maximumPoolSize = env.getProperty("spring.datasource.hikari.maximumPoolSize", Int::class.java, 5)
      minimumIdle = env.getProperty("spring.datasource.hikari.minimumIdle", Int::class.java, 5)
      idleTimeout = env.getProperty("spring.datasource.hikari.idleTimeout", Long::class.java, 600000)
      connectionTimeout =
        env.getProperty("spring.datasource.hikari.connectionTimeout", Long::class.java, 20000)
    }

    return HikariDataSource(hikariConfig)
  }

  // EntityManagerFactory
  @Bean
  @Primary
  fun postgresqlEntityManagerFactory(
    builder: EntityManagerFactoryBuilder,
    @Qualifier("postgresqlDataSource") dataSource: DataSource
  ): LocalContainerEntityManagerFactoryBean {
    val properties = HashMap<String, Any>()
    properties["hibernate.dialect"] = "org.hibernate.dialect.PostgreSQLDialect"

    return builder
      .dataSource(dataSource)
      .packages("com.sample.spring_batch.entity")
      .persistenceUnit("postgresql")
      .properties(properties)
      .build()
  }

  @Bean
  @Primary
  fun jpaTransactionManager(
    @Qualifier("postgresqlEntityManagerFactory") entityManagerFactory: LocalContainerEntityManagerFactoryBean
  ): PlatformTransactionManager {
    return JpaTransactionManager(entityManagerFactory.`object`!!)
  }

  @Bean
  fun batchTransactionManager(
    @Qualifier("postgresqlDataSource") dataSource: DataSource
  ): PlatformTransactionManager {
    return DataSourceTransactionManager(dataSource)
  }

  // Spring Batch 에서 사용할 데이터베이스 테이블이 존재하지 않을 경우
  // PostgreSQL 초기화 스크립트를 실행하여 필요한 테이블을 생성한다.
  @Bean
  fun batchDataSourceInitializer(
    @Qualifier("postgresqlDataSource") dataSource: DataSource
  ): DataSourceInitializer {
    val initializer = DataSourceInitializer()
    initializer.setDataSource(dataSource)

    val populator = ResourceDatabasePopulator().apply {
      addScript(ClassPathResource("org/springframework/batch/core/schema-postgresql.sql"))
    }

    initializer.setDatabasePopulator { connection ->
      val rs = connection.metaData.getTables(null, null, "batch_job_instance", null)
      if (!rs.next()) {
        populator.populate(connection)
      }
    }

    return initializer
  }
}

@Configuration
@EnableJpaRepositories(
  basePackages = ["com.sample.spring_batch.repository"],
  entityManagerFactoryRef = "postgresqlEntityManagerFactory",
  transactionManagerRef = "postgresqlTransactionManager"
)
class PostgresqlRepositoryConfig