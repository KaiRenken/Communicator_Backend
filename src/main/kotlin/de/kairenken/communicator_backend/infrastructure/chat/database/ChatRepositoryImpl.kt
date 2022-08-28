package de.kairenken.communicator_backend.infrastructure.chat.database

import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.domain.chat.ChatRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ChatRepositoryImpl : ChatRepository {

    private val chatStore = mutableMapOf<UUID, Chat>()

    override fun storeChat(chat: Chat): Chat {
        chatStore.put(chat.id.value, chat)
        return chat
    }

    override fun doesChatExist(id: Chat.Id): Boolean {
        return chatStore.containsKey(id.value)
    }
}