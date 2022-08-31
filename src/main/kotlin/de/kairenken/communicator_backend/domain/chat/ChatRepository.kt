package de.kairenken.communicator_backend.domain.chat

interface ChatRepository {

    fun storeChat(chat: Chat)
    fun doesChatExist(id: Chat.Id): Boolean

    fun findAllChats(): List<Chat>
}