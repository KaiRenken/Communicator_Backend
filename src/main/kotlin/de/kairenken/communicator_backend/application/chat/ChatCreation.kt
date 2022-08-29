package de.kairenken.communicator_backend.application.chat

import de.kairenken.communicator_backend.application.chat.dto.ChatCreationDto
import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.domain.chat.ChatRepository
import org.springframework.stereotype.Service

@Service
class ChatCreation(private val chatRepository: ChatRepository) {

    fun createChat(chatCreationDto: ChatCreationDto) : Chat {
        val createdChat = Chat(name = Chat.Name(chatCreationDto.name))

        chatRepository.storeChat(createdChat)

        return createdChat
    }
}