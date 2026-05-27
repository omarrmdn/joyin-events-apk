package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val eventId: Int, // Refers to the event ID for the context of conversation
    val senderName: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isFromMe: Boolean,
    val isSpecialLink: Boolean = false,
    val linkUrl: String = ""
)
