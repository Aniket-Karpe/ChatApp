package com.astech.chatapp.models

import com.google.firebase.database.PropertyName

data class ChatMessage(
    @PropertyName("message") val message: String = "",
    @PropertyName("sentByUser") val sentByUser: Boolean = false,
    @PropertyName("senderUid") val senderUid: String = ""
) {
    constructor() : this("", false, "")
}
