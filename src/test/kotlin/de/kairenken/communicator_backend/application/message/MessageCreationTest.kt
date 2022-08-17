package de.kairenken.communicator_backend.application.message

import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.domain.message.MessageChatRefRepository
import de.kairenken.communicator_backend.domain.message.MessageRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

internal class MessageCreationTest {

    private val messageRepository = mock(MessageRepository::class.java)
    private val messageChatRefRepository = mock(MessageChatRefRepository::class.java)

    private val messageCreation = MessageCreation(messageChatRefRepository, messageRepository)

    @Test
    @DisplayName("Create message successfully")
    fun createMessage() {
        val message = Message(
            Message.Id(),
            Message.ChatRefId(UUID.randomUUID()),
            Message.Content("test-message")
        )

        `when`(messageChatRefRepository.chatExists(message.chatRefId))
            .thenReturn(true)
        `when`(messageRepository.storeMessage(message))
            .thenReturn(message)

        val createdMessage = messageCreation.createMessage(message)

        assertThat(createdMessage).isEqualTo(message)
        verify(messageRepository).storeMessage(message)
        verify(messageChatRefRepository).chatExists(message.chatRefId)
    }

    @Test
    @DisplayName("Create message to non-existing chat")
    fun createMessageToNonExistingChat() {
        val message = Message(
            Message.Id(),
            Message.ChatRefId(UUID.randomUUID()),
            Message.Content("test-message")
        )

        `when`(messageChatRefRepository.chatExists(messageChatRefId = message.chatRefId))
            .thenReturn(false)

        assertThatThrownBy { messageCreation.createMessage(message) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Chat does not exist")
    }
}