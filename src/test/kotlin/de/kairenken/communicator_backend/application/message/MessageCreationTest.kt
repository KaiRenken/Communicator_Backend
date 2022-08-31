package de.kairenken.communicator_backend.application.message

import de.kairenken.communicator_backend.application.message.dto.MessageCreationDto
import de.kairenken.communicator_backend.domain.message.MessageChatRefRepository
import de.kairenken.communicator_backend.domain.message.MessageRepository
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

    private val messageCreation = MessageCreation(
        messageChatRefRepository = messageChatRefRepository,
        messageRepository = messageRepository
    )

    @Test
    fun `create message successfully`() {
        val messageCreationDto = MessageCreationDto(UUID.randomUUID(), "test-message")
        every { messageChatRefRepository.chatExists(any()) } returns true
        justRun { messageRepository.storeMessage(any()) }

        val createdMessage = messageCreation.create(messageCreationDto)

        assertThat(createdMessage).isInstanceOf(MessageCreation.Success::class.java)
        verify { messageRepository.storeMessage(any()) }
        verify { messageChatRefRepository.chatExists(any()) }
        val messageCreated = createdMessage as MessageCreation.Success
        assertThat(messageCreated.message.chatRefId.value).isEqualTo(messageCreationDto.chatRefId)
        assertThat(messageCreated.message.content.value).isEqualTo(messageCreationDto.content)
    }

    @Test
    fun `create message to non existing chat`() {
        val messageCreationDto = MessageCreationDto(UUID.randomUUID(), "test-message")
        every { messageChatRefRepository.chatExists(any()) } returns false

        val result = messageCreation.create(messageCreationDto)

        assertThat(result).isInstanceOf(MessageCreation.Error::class.java)
        val chatNotFound = result as MessageCreation.Error
        assertThat(chatNotFound.chatRefId.value).isEqualTo(messageCreationDto.chatRefId)
    }
}