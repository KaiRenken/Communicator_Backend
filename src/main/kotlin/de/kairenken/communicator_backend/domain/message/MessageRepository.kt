package de.kairenken.communicator_backend.domain.message

interface MessageRepository {

    fun storeMessage(message: Message)

    fun findAllByChatRefId(chatRefId: Message.ChatRefId): List<Message>
}