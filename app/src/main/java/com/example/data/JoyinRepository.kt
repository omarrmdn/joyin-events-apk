package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class JoyinRepository(private val database: JoyinDatabase) {
    private val eventDao = database.eventDao()
    private val messageDao = database.messageDao()

    val allEvents: Flow<List<Event>> = eventDao.getAllEvents()
    val joinedEvents: Flow<List<Event>> = eventDao.getJoinedEvents()

    fun getMessagesForEvent(eventId: Int): Flow<List<Message>> {
        return messageDao.getMessagesForEvent(eventId)
    }

    suspend fun getEventById(eventId: Int): Event? {
        return eventDao.getEventById(eventId)
    }

    suspend fun createEvent(event: Event): Long {
        return eventDao.insertEvent(event)
    }

    suspend fun updateEvent(event: Event) {
        eventDao.updateEvent(event)
    }

    suspend fun sendMessage(message: Message): Long {
        return messageDao.insertMessage(message)
    }

    suspend fun initDatabaseIfEmpty() {
        try {
            eventDao.deleteMockEvents()
        } catch (e: Exception) {
            // Safe fallback
        }
    }
}
