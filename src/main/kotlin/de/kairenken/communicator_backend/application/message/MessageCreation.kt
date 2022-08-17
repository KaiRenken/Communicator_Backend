package de.kairenken.communicator_backend.application.message

import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.domain.message.MessageChatRefRepository
import de.kairenken.communicator_backend.domain.message.MessageRepository
import org.springframework.stereotype.Service

@Service
class MessageCreation(
    val messageChatRefRepository: MessageChatRefRepository,
    val messageRepository: MessageRepository
) {

    fun createMessage(message: Message): Message {
        validateMessage(message)

        return messageRepository.storeMessage(message)
    }

    private fun validateMessage(message: Message) {
        if (!messageChatRefRepository.chatExists(messageChatRefId = message.chatRefId)) {
            throw IllegalArgumentException("Chat does not exist")
        }
    }
}