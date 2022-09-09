package de.kairenken.communicator_backend.application.message

import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.domain.message.MessageChatRefRepository
import de.kairenken.communicator_backend.domain.message.MessageRepository
import de.kairenken.communicator_backend.testing.data.MessageTestDataFactory
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class MessageRetrievalTest {

    private val messageRepository = mockk<MessageRepository>()
    private val messageChatRefRepository = mockk<MessageChatRefRepository>()

    private val messageRetrieval = MessageRetrieval(
        messageChatRefRepository = messageChatRefRepository,
        messageRepository = messageRepository
    )

    @Test
    fun `retrieve messages successfully`() {
        val message1 = MessageTestDataFactory.aTestMessage().build()
        val message2 = MessageTestDataFactory.aTestMessage()
            .withId(UUID.randomUUID())
            .withContent("test-content-2")
            .build()
        every { messageChatRefRepository.chatExists(message1.chatRefId) } returns true
        every { messageRepository.findAllByChatRefId(message1.chatRefId) } returns listOf(message1, message2)

        val retrievalResult = messageRetrieval.retrieveAllMessagesByChatRefId(message1.chatRefId)

        assertThat(retrievalResult).isInstanceOf(MessageRetrieval.Success::class.java)
        val retrievedMessages = (retrievalResult as MessageRetrieval.Success).messages
        assertThat(retrievedMessages).hasSize(2)
        assertThat(retrievedMessages).contains(message1)
        assertThat(retrievedMessages).contains(message2)
    }

    @Test
    fun `retrieve messages when chat does not exist`() {
        val chatRefId = Message.ChatRefId(UUID.randomUUID())
        every { messageChatRefRepository.chatExists(chatRefId) } returns false

        val retrievalResult = messageRetrieval.retrieveAllMessagesByChatRefId(chatRefId)

        assertThat(retrievalResult).isInstanceOf(MessageRetrieval.Error::class.java)
        val resultContent = (retrievalResult as MessageRetrieval.Error).chatRefId
        assertThat(resultContent).isEqualTo(chatRefId)
    }
}