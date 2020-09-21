package com.example.infra.db

import io.r2dbc.spi.Clob
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableR2dbcRepositories
@EnableTransactionManagement
class DbConfig {

    // h2 does not auto convert Clob to String
    @Bean
    fun r2dbcCustomConversions(): R2dbcCustomConversions {
        return R2dbcCustomConversions(
            listOf(
                ClobToStringConverter()
            )
        )
    }

    class ClobToStringConverter : Converter<Clob, String> {
        override fun convert(source: Clob): String {
            @Suppress("BlockingMethodInNonBlockingContext")
            return runBlocking {
                source.stream().asFlow()
                    .fold(StringBuilder()) { a, e ->
                        a.append(e)
                    }
                    .toString()
            }
        }
    }
}