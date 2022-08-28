package de.kairenken.communicator_backend.application.message

import de.kairenken.communicator_backend.domain.message.MessageChatRefRepository
import de.kairenken.communicator_backend.domain.message.MessageRepository
import de.kairenken.communicator_backend.domain.message.dto.MessageCreationDto
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class MessageCreationTest {

    private val messageRepository = mockk<MessageRepository>()
    private val messageChatRefRepository = mockk<MessageChatRefRepository>()

    private val messageCreation = MessageCreation(messageChatRefRepository, messageRepository)

    @Test
    fun `create message successfully`() {
        val messageCreationDto = MessageCreationDto(UUID.randomUUID(), "test-message")
        every { messageChatRefRepository.chatExists(any()) } returns true
        justRun { messageRepository.storeMessage(any()) }

        val createdMessage = messageCreation.createMessage(messageCreationDto)

        assertThat(createdMessage).isInstanceOf(MessageCreated::class.java)
        verify { messageRepository.storeMessage(any()) }
        verify { messageChatRefRepository.chatExists(any()) }
        TODO("Check results content")
    }

    @Test
    fun `create message to non existing chat`() {
        val messageCreationDto = MessageCreationDto(UUID.randomUUID(), "test-message")
        every { messageChatRefRepository.chatExists(any()) } returns false

        val result = messageCreation.createMessage(messageCreationDto)

        assertThat(result).isInstanceOf(ChatNotFound::class.java)
        TODO("Check results content")
    }
}