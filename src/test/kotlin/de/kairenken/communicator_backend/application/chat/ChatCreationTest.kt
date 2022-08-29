package de.kairenken.communicator_backend.application.chat

import de.kairenken.communicator_backend.application.chat.dto.ChatCreationDto
import de.kairenken.communicator_backend.domain.chat.ChatRepository
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class ChatCreationTest {

    private val chatRepository = mockk<ChatRepository>()

    private val chatCreation = ChatCreation(chatRepository)

    @Test
    fun `create chat`() {
        val chatCreationDto = ChatCreationDto(name = "test-name")
        justRun { chatRepository.storeChat(any()) }

        val createdChat = chatCreation.createChat(chatCreationDto)

        assertThat(createdChat.id.value).isInstanceOf(UUID::class.java)
        assertThat(createdChat.messageIds).isEmpty()
        assertThat(createdChat.name.value).isEqualTo("test-name")
        verify { chatRepository.storeChat(any()) }
    }
}