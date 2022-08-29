package de.kairenken.communicator_backend.application.chat

import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.domain.chat.ChatRepository
import org.springframework.stereotype.Service

@Service
class ChatCreation(private val chatRepository: ChatRepository) {

    fun createChat() : Chat {
        val createdChat = Chat()

        chatRepository.storeChat(createdChat)

        return createdChat
    }
}