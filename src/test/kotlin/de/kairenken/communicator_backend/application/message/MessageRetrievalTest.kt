package de.kairenken.communicator_backend.application.message

import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.domain.message.MessageChatRefRepository
import de.kairenken.communicator_backend.domain.message.MessageRepository
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
        val chatRefId = Message.ChatRefId(UUID.randomUUID())
        val message1 = Message(
            id = Message.Id(UUID.randomUUID()),
            chatRefId = chatRefId,
            content = Message.Content("test-content-1")
        )
        val message2 = Message(
            id = Message.Id(UUID.randomUUID()),
            chatRefId = chatRefId,
            content = Message.Content("test-content-2")
        )
        every { messageChatRefRepository.chatExists(chatRefId) } returns true
        every { messageRepository.findAllByChatRefId(chatRefId) } returns listOf(message1, message2)

        val retrievalResult = messageRetrieval.retrieveAllMessagesByChatRefId(chatRefId)

        assertThat(retrievalResult).isInstanceOf(MessageRetrievalResult.Success::class.java)
        val retrievedMessages = (retrievalResult as MessageRetrievalResult.Success).messages
        assertThat(retrievedMessages).hasSize(2)
        assertThat(retrievedMessages).contains(message1)
        assertThat(retrievedMessages).contains(message2)
    }

    @Test
    fun `retrieve messages when chat does not exist`() {
        val chatRefId = Message.ChatRefId(UUID.randomUUID())
        every { messageChatRefRepository.chatExists(chatRefId) } returns false

        val retrievalResult = messageRetrieval.retrieveAllMessagesByChatRefId(chatRefId)

        assertThat(retrievalResult).isInstanceOf(MessageRetrievalResult.Error::class.java)
        val resultContent = (retrievalResult as MessageRetrievalResult.Error).chatRefId
        assertThat(resultContent).isEqualTo(chatRefId)
    }
}