package com.aubrey.jetcomic.model

data class Comic(
    val id: Long,
    val title: String,
    val author: String,
    val rating: String,
    val price: Int,
    val photoUrl: String,
    val description: String
)