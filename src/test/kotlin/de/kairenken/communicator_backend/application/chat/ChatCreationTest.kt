package de.kairenken.communicator_backend.application.chat

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
        justRun { chatRepository.storeChat(any()) }

        val createdChat = chatCreation.createChat()

        assertThat(createdChat.id.value).isInstanceOf(UUID::class.java)
        assertThat(createdChat.messageIds).isEmpty()
        verify { chatRepository.storeChat(any()) }
    }
}