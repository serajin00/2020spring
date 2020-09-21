package com.example.api

import com.example.api.handler.LocationHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RoutesConfig(
    val locationHandler: LocationHandler
) {

    @Bean
    fun routerBean() = coRouter {
        "/api/v1/locations".nest {
            GET("", locationHandler::list)
            HEAD("", locationHandler::list)
            POST("", locationHandler::create)

            "/{id}".nest {
                GET("", locationHandler::get)
                HEAD("", locationHandler::get)
                PUT("", locationHandler::update)
                PATCH("", locationHandler::patch)
            }
        }
    }
}