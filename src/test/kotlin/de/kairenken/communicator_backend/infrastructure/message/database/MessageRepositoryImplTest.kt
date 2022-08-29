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
}