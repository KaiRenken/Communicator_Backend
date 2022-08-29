package de.kairenken.communicator_backend.infrastructure.chat.database

import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.testcontainers.AbstractDatabaseTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

internal class ChatRepositoryImplTest : AbstractDatabaseTest() {

    @Test
    fun `store chat`() {
        val chat = Chat()

        chatRepositoryImpl.storeChat(chat)

        val storedChat = chatJpaRepository.findById(chat.id.value)

        assertThat(storedChat).isPresent
        assertThat(storedChat.get().id).isEqualTo(chat.id.value)
    }

    @Test
    fun `does chat exist when chat exists`() {
        val chatEntity = ChatEntity(UUID.randomUUID())
        chatJpaRepository.save(chatEntity)

        Assertions.assertTrue(chatRepositoryImpl.doesChatExist(Chat.Id(chatEntity.id)))
    }

    @Test
    fun `does chat exist when chat does not exist`() {

        Assertions.assertFalse(chatRepositoryImpl.doesChatExist(Chat.Id(UUID.randomUUID())))
    }
}