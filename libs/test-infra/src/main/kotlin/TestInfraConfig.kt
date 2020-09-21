package com.example.infra

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.data.r2dbc.connectionfactory.init.ConnectionFactoryInitializer
import org.springframework.data.r2dbc.connectionfactory.init.ResourceDatabasePopulator

@Configuration
@Profile("test")
class TestInfraConfig {

    // https://docs.spring.io/spring-data/r2dbc/docs/1.1.3.RELEASE/reference/html/#r2dbc.init
    @Bean
    fun testDbInitializer(
        connectionFactory: ConnectionFactory,
        resourceLoader: ResourceLoader
    ): ConnectionFactoryInitializer {
        fun loadResource(dir: String, filename: String): Resource {
            return resourceLoader.getResource("classpath:$dir$filename")
        }

        val schemaFiles = listOf(
            "locations_schema.sql",
            "reservations_schema.sql"
        )
            .map { loadResource("/test/db/schema/", it) }

        val dataFiles = listOf(
            "locations_data.sql",
            "reservations_data.sql"
        )
            .map { loadResource("/test/db/data/", it) }

        val populator = ResourceDatabasePopulator(
            *(schemaFiles + dataFiles).toTypedArray()
        )

        return ConnectionFactoryInitializer()
            .apply {
                setConnectionFactory(connectionFactory)
                setDatabasePopulator(populator)
            }
    }
}