package de.kairenken.communicator_backend.application.message

import de.kairenken.communicator_backend.application.message.dto.MessageCreationDto
import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.domain.message.MessageChatRefRepository
import de.kairenken.communicator_backend.domain.message.MessageRepository
import org.springframework.stereotype.Service

@Service
class MessageCreation(
    private val messageChatRefRepository: MessageChatRefRepository,
    private val messageRepository: MessageRepository
) {

    fun createMessage(messageCreationDto: MessageCreationDto) = messageCreationDto
        .validateAndCreateMessage()
        .storeMessage()

    private fun MessageCreationDto.validateAndCreateMessage(): MessageCreationResult {
        val chatRefId = Message.ChatRefId(this.chatRefId)

        when (messageChatRefRepository.chatExists(chatRefId)) {
            true -> return MessageCreationResult.Success(
                Message(
                    chatRefId = chatRefId,
                    content = Message.Content(this.content)
                )
            )
            false -> return MessageCreationResult.Error(chatRefId)
        }
    }

    private fun MessageCreationResult.storeMessage(): MessageCreationResult {
        if (this is MessageCreationResult.Success) {
            messageRepository.storeMessage(this.message)
        }

        return this
    }
}

sealed class MessageCreationResult {

    class Success(val message: Message) : MessageCreationResult()

    class Error(val chatRefId: Message.ChatRefId) : MessageCreationResult()
}
