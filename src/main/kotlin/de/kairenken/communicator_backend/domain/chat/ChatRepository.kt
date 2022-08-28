package de.kairenken.communicator_backend.domain.chat

interface ChatRepository {

    fun storeChat(chat: Chat): Chat
    fun doesChatExist(id: Chat.Id): Boolean
}