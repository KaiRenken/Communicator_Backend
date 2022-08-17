package de.kairenken.communicator_backend.application.chat

import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.domain.chat.ChatRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

internal class ChatCreationTest {

    private val chatRepository = mock(ChatRepository::class.java)

    private val chatCreation = ChatCreation(chatRepository)

    @Test
    @DisplayName("Create chat")
    fun createChat() {
        val chat = Chat(Chat.Id())
        Mockito.`when`(chatCreation.createChat(chat)).thenReturn(chat)

        val createdChat = chatCreation.createChat(chat)

        assertThat(createdChat.id).isEqualTo(chat.id)
        assertThat(createdChat.messageIds).isEmpty()
        verify(chatRepository).storeChat(chat)
    }
}