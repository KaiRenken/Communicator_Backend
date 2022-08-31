package de.kairenken.communicator_backend.application.chat

import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.domain.chat.ChatRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

internal class ChatRetrievalTest {

    private val chatRepository = mockk<ChatRepository>()

    private val chatRetrieval = ChatRetrieval(chatRepository)

    @Test
    fun `retrieve all chats`() {
        val chat1 = Chat(
            id = Chat.Id(UUID.randomUUID()),
            name = Chat.Name("test-name-1")
        )
        val chat2 = Chat(
            id = Chat.Id(UUID.randomUUID()),
            name = Chat.Name("test-name-2")
        )
        every { chatRepository.findAllChats() } returns listOf(chat1, chat2)

        val retrievedChats = chatRetrieval.retrieveAllChats()

        assertThat(retrievedChats).hasSize(2)
        assertThat(retrievedChats.get(0)).isEqualTo(chat1)
        assertThat(retrievedChats.get(1)).isEqualTo(chat2)
    }
}