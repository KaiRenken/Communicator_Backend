package de.kairenken.communicator_backend.infrastructure.message.database

import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.infrastructure.chat.database.ChatEntity
import de.kairenken.communicator_backend.testcontainers.AbstractDatabaseTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*

internal class MessageChatRefRepositoryImplTest : AbstractDatabaseTest() {

    @Test
    fun `chat exists with existing chat`() {
        val chatEntity = ChatEntity(UUID.randomUUID())
        chatJpaRepository.save(chatEntity)

        assertTrue(messageChatRefRepositoryImpl.chatExists(Message.ChatRefId(chatEntity.id)))
    }

    @Test
    fun `chat exists with missing chat`() {
        assertFalse(messageChatRefRepositoryImpl.chatExists(Message.ChatRefId(UUID.randomUUID())))
    }
}