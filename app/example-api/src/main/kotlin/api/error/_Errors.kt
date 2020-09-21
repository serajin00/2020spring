package com.example.api.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class Error400(message: String? = null) : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class Error404(message: String? = null) : RuntimeException(message)
