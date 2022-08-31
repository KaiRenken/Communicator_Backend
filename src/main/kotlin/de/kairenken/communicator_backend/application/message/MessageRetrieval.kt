package de.kairenken.communicator_backend.application.message

import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.domain.message.MessageChatRefRepository
import de.kairenken.communicator_backend.domain.message.MessageRepository
import org.springframework.stereotype.Service

@Service
class MessageRetrieval(
    private val messageChatRefRepository: MessageChatRefRepository,
    private val messageRepository: MessageRepository
) {
    fun retrieveAllMessagesByChatRefId(chatRefId: Message.ChatRefId): Result {
        if (!chatRefId.chatExists()) {
            return Error(chatRefId)
        }

        return chatRefId
            .retrieveMessages()
            .wrapInResult()
    }

    private fun Message.ChatRefId.chatExists() = messageChatRefRepository.chatExists(this)

    private fun Message.ChatRefId.retrieveMessages() = messageRepository.findAllByChatRefId(this)

    private fun List<Message>.wrapInResult() = Success(this)

    sealed class Result
    class Success(val messages: List<Message>) : Result()
    class Error(val chatRefId: Message.ChatRefId) : Result()
}
