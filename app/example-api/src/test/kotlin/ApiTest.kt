package com.example

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.test.web.reactive.server.WebTestClient

@SpringJUnitConfig
@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
annotation class ApiTest {
    @Configuration
    class Config {
        @Bean
        fun webTestClient(context: ApplicationContext): WebTestClient {
            return WebTestClient.bindToApplicationContext(context).build()
        }
    }
}