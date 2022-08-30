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
    fun retrieveAllMessagesByChatRefId(chatRefId: Message.ChatRefId) = chatRefId.retrieveMessagesIfChatExists()

    private fun Message.ChatRefId.retrieveMessagesIfChatExists() = when (
        messageChatRefRepository.chatExists(this)
    ) {
        true -> MessageRetrievalResult.Success(messageRepository.findAllByChatRefId(this))
        false -> MessageRetrievalResult.Error(this)
    }
}

sealed class MessageRetrievalResult {

    class Success(val messages: List<Message>) : MessageRetrievalResult()

    class Error(val chatRefId: Message.ChatRefId) : MessageRetrievalResult()
}