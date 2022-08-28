package de.kairenken.communicator_backend.application.message

import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.domain.message.MessageChatRefRepository
import de.kairenken.communicator_backend.domain.message.MessageRepository
import de.kairenken.communicator_backend.domain.message.dto.MessageCreationDto
import org.springframework.stereotype.Service

@Service
class MessageCreation(
    val messageChatRefRepository: MessageChatRefRepository,
    val messageRepository: MessageRepository
) {

    fun createMessage(messageCreationDto: MessageCreationDto) = messageCreationDto
        .validateAndCreateMessage()
        .storeMessage()

    private fun MessageCreationDto.validateAndCreateMessage(): Result {
        val chatRefId = Message.ChatRefId(this.chatRefId)

        if (!messageChatRefRepository.chatExists(chatRefId)) {
            return ChatNotFound(chatRefId)
        }

        return MessageCreated(
            Message(
                chatRefId = chatRefId,
                content = Message.Content(this.content)
            )
        )
    }

    private fun Result.storeMessage(): Result {
        if (this is MessageCreated) {
            messageRepository.storeMessage(this.message)
        }

        return this
    }
}

sealed class Result

class MessageCreated(val message: Message) : Result()

class ChatNotFound(val chatRefId: Message.ChatRefId) : Result()