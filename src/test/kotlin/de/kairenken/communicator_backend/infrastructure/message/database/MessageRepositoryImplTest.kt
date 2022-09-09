package de.kairenken.communicator_backend.infrastructure.message.database

import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.testing.data.MessageEntityTestDataFactory
import de.kairenken.communicator_backend.testing.data.MessageTestDataFactory
import de.kairenken.communicator_backend.testing.testcontainers.AbstractDatabaseTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

internal class MessageRepositoryImplTest : AbstractDatabaseTest() {

    @Test
    fun `store message`() {
        val message = MessageTestDataFactory.aTestMessage().build()

        messageRepositoryImpl.storeMessage(message)

        val storedMessage = messageJpaRepository.findById(message.id.value)

        Assertions.assertThat(storedMessage).isPresent
        Assertions.assertThat(storedMessage.get().id).isEqualTo(message.id.value)
        Assertions.assertThat(storedMessage.get().chatRefId).isEqualTo(message.chatRefId.value)
        Assertions.assertThat(storedMessage.get().content).isEqualTo(message.content.value)
    }

    @Test
    fun `find all messages by chatrefid`() {
        val messageEntity1 = MessageEntityTestDataFactory.aTestMessageEntity().build()
        val messageEntity2 = MessageEntityTestDataFactory.aTestMessageEntity()
            .withId(UUID.randomUUID())
            .build()
        val messageEntity3 = MessageEntityTestDataFactory.aTestMessageEntity()
            .withId(UUID.randomUUID())
            .withChatRefId(UUID.randomUUID())
            .build()
        messageJpaRepository.saveAll(listOf(messageEntity1, messageEntity2, messageEntity3))

        val messages = messageRepositoryImpl.findAllByChatRefId(Message.ChatRefId(messageEntity1.chatRefId))

        Assertions.assertThat(messages).hasSize(2)
        Assertions.assertThat(messages.get(0).id.value).isEqualTo(messageEntity1.id)
        Assertions.assertThat(messages.get(1).id.value).isEqualTo(messageEntity2.id)
        Assertions.assertThat(messages.get(0).chatRefId.value).isEqualTo(messageEntity1.chatRefId)
        Assertions.assertThat(messages.get(1).chatRefId.value).isEqualTo(messageEntity2.chatRefId)
        Assertions.assertThat(messages.get(0).content.value).isEqualTo(messageEntity1.content)
        Assertions.assertThat(messages.get(1).content.value).isEqualTo(messageEntity2.content)

    }
}