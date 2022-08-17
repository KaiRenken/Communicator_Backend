package de.kairenken.communicator_backend.domain.message

interface MessageChatRefRepository {

    fun chatExists(messageChatRefId: Message.ChatRefId): Boolean
}