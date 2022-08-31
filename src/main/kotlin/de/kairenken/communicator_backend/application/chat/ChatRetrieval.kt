package de.kairenken.communicator_backend.application.chat

import de.kairenken.communicator_backend.application.common.UseCase
import de.kairenken.communicator_backend.domain.chat.ChatRepository

@UseCase
class ChatRetrieval(private val chatRepository: ChatRepository) {

    fun retrieveAllChats() = chatRepository.findAllChats()
}