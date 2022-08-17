package de.kairenken.communicator_backend.domain.message

interface MessageRepository {

    fun storeMessage(message: Message): Message
}