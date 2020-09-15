package com.example.domain.model

data class Comment(
    val id: Long?,
    val postId: Long,
    val author: String
)
