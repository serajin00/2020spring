package com.example.domain.model

data class Post(
    val id: Long?,
    val title: String,
    val content: String,
    val author: String,
    val status: PostStatus
)

enum class PostStatus {
    DRAFT,
    PUBLISHED,
    DELETED
}
