package de.kairenken.communicator_backend.infrastructure.chat.database

import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.testing.data.ChatEntityTestDataFactory
import de.kairenken.communicator_backend.testing.data.ChatTestDataFactory
import de.kairenken.communicator_backend.testing.testcontainers.AbstractDatabaseTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

internal class ChatRepositoryImplTest : AbstractDatabaseTest() {

    @Test
    fun `store chat`() {
        val chat = ChatTestDataFactory.aTestChat().build()

        chatRepositoryImpl.storeChat(chat)

        val storedChat = chatJpaRepository.findById(chat.id.value)

        assertThat(storedChat).isPresent
        assertThat(storedChat.get().id).isEqualTo(chat.id.value)
        assertThat(storedChat.get().name).isEqualTo(chat.name.value)
    }

    @Test
    fun `does chat exist when chat exists`() {
        val chatEntity = ChatEntityTestDataFactory.aTestChatEntity().build()
        chatJpaRepository.save(chatEntity)

        Assertions.assertTrue(chatRepositoryImpl.doesChatExist(Chat.Id(chatEntity.id)))
    }

    @Test
    fun `does chat exist when chat does not exist`() {

        Assertions.assertFalse(chatRepositoryImpl.doesChatExist(Chat.Id(UUID.randomUUID())))
    }

    @Test
    fun `find all chats`() {
        val chatEntity1 = ChatEntityTestDataFactory.aTestChatEntity().build()
        val chatEntity2 = ChatEntityTestDataFactory.aTestChatEntity()
            .withId(UUID.randomUUID())
            .withName("test-name-2")
            .build()
        chatJpaRepository.saveAll(listOf(chatEntity1, chatEntity2))

        val storedChats = chatRepositoryImpl.findAllChats()

        assertThat(storedChats).hasSize(2)
        assertThat(storedChats.get(0).id.value).isEqualTo(chatEntity1.id)
        assertThat(storedChats.get(1).id.value).isEqualTo(chatEntity2.id)
    }
}