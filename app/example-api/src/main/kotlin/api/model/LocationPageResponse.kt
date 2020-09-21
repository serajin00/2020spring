package com.example.api.model

import org.springframework.data.domain.Page

data class LocationPageResponse(
    val page: Page<LocationResponse>
)