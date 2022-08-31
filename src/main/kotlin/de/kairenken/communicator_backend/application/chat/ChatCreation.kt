package de.kairenken.communicator_backend.application.chat

import de.kairenken.communicator_backend.application.chat.dto.ChatCreationDto
import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.domain.chat.ChatRepository
import org.springframework.stereotype.Service

@Service
class ChatCreation(private val chatRepository: ChatRepository) {

    fun create(chatCreationDto: ChatCreationDto) = chatCreationDto
        .createChat()
        .storeAndReturnChat()

    private fun ChatCreationDto.createChat() = Chat(name = Chat.Name(this.name))

    private fun Chat.storeAndReturnChat(): Chat {
        chatRepository.storeChat(this)
        return this
    }
}
