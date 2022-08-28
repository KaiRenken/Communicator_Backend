package de.kairenken.communicator_backend.application.chat

import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.domain.chat.ChatRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ChatCreationTest {

    private val chatRepository = mockk<ChatRepository>()

    private val chatCreation = ChatCreation(chatRepository)

    @Test
    fun `create chat`() {
        val chat = Chat()
        every { chatRepository.storeChat(any()) } returns chat

        val createdChat = chatCreation.createChat()

        assertThat(createdChat.id).isEqualTo(chat.id)
        assertThat(createdChat.messageIds).isEmpty()
        verify { chatRepository.storeChat(any()) }
    }
}