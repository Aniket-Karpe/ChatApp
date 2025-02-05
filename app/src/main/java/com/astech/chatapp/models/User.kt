package com.astech.chatapp.models

data class User(
    val userId: String? = "",
    val name: String = "",
    val email: String = "",
    val imageUrl: String = ""
)