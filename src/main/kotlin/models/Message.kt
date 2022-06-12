package models

data class Message(
    val commandId: Int,
    val userId: Int,
    val data: Any
)
