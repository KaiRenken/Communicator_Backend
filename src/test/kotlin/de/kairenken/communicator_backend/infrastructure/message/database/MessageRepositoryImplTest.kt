package de.kairenken.communicator_backend.infrastructure.message.database

import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.testcontainers.AbstractDatabaseTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

internal class MessageRepositoryImplTest : AbstractDatabaseTest() {

    @Test
    fun `store message`() {
        val message = Message(
            chatRefId = Message.ChatRefId(UUID.randomUUID()),
            content = Message.Content("test-content")
        )

        messageRepositoryImpl.storeMessage(message)

        val storedMessage = messageJpaRepository.findById(message.id.value)

        Assertions.assertThat(storedMessage).isPresent
        Assertions.assertThat(storedMessage.get().id).isEqualTo(message.id.value)
        Assertions.assertThat(storedMessage.get().chatRefId).isEqualTo(message.chatRefId.value)
        Assertions.assertThat(storedMessage.get().content).isEqualTo(message.content.value)
    }

    @Test
    fun `find all messages by chatrefid`() {
        val chatRefId1 = UUID.randomUUID()
        val chatRefId2 = UUID.randomUUID()
        val messageEntity1 = MessageEntity(
            id = UUID.randomUUID(),
            chatRefId = chatRefId1,
            content = "test-content"
        )
        val messageEntity2 = MessageEntity(
            id = UUID.randomUUID(),
            chatRefId = chatRefId1,
            content = "test-content"
        )
        val messageEntity3 = MessageEntity(
            id = UUID.randomUUID(),
            chatRefId = chatRefId2,
            content = "test-content"
        )
        messageJpaRepository.saveAll(listOf(messageEntity1, messageEntity2, messageEntity3))

        val messages = messageRepositoryImpl.findAllByChatRefId(Message.ChatRefId(chatRefId1))

        Assertions.assertThat(messages).hasSize(2)
        Assertions.assertThat(messages.get(0).id.value).isEqualTo(messageEntity1.id)
        Assertions.assertThat(messages.get(1).id.value).isEqualTo(messageEntity2.id)
        Assertions.assertThat(messages.get(0).chatRefId.value).isEqualTo(messageEntity1.chatRefId)
        Assertions.assertThat(messages.get(1).chatRefId.value).isEqualTo(messageEntity2.chatRefId)
        Assertions.assertThat(messages.get(0).content.value).isEqualTo(messageEntity1.content)
        Assertions.assertThat(messages.get(1).content.value).isEqualTo(messageEntity2.content)

    }
}