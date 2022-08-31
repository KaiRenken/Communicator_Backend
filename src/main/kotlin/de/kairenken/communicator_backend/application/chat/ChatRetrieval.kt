package de.kairenken.communicator_backend.application.chat

import de.kairenken.communicator_backend.domain.chat.ChatRepository
import org.springframework.stereotype.Service

@Service
class ChatRetrieval(private val chatRepository: ChatRepository) {

    fun retrieveAllChats() = chatRepository.findAllChats()
}