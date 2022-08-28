package de.kairenken.communicator_backend.infrastructure.message.database

import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.domain.chat.ChatRepository
import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.domain.message.MessageChatRefRepository
import org.springframework.stereotype.Repository

@Repository
class MessageChatRefRepositoryImpl(val chatRepository: ChatRepository) : MessageChatRefRepository {

    override fun chatExists(messageChatRefId: Message.ChatRefId): Boolean {
        return chatRepository.doesChatExist(Chat.Id(messageChatRefId.value))
    }
}