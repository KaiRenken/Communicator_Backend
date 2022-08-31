package de.kairenken.communicator_backend.application.message

import de.kairenken.communicator_backend.application.common.UseCase
import de.kairenken.communicator_backend.application.message.dto.MessageCreationDto
import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.domain.message.MessageChatRefRepository
import de.kairenken.communicator_backend.domain.message.MessageRepository

@UseCase
class MessageCreation(
    private val messageChatRefRepository: MessageChatRefRepository,
    private val messageRepository: MessageRepository
) {
    fun create(messageCreationDto: MessageCreationDto): Result {
        if (!messageCreationDto.chatExists()) {
            return Error(Message.ChatRefId(messageCreationDto.chatRefId))
        }

        return messageCreationDto
            .createMessage()
            .storeMessage()
            .wrapInResult()
    }

    private fun MessageCreationDto.chatExists() = messageChatRefRepository.chatExists(Message.ChatRefId(this.chatRefId))

    private fun MessageCreationDto.createMessage() = Message(
        chatRefId = Message.ChatRefId(this.chatRefId),
        content = Message.Content(this.content)
    )

    private fun Message.storeMessage(): Message {
        messageRepository.storeMessage(this)
        return this
    }

    private fun Message.wrapInResult() = Success(this)

    sealed class Result
    class Success(val message: Message) : Result()
    class Error(val chatRefId: Message.ChatRefId) : Result()
}
