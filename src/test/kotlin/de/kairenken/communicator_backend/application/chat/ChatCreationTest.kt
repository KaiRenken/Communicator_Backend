package de.kairenken.communicator_backend.application.chat

import de.kairenken.communicator_backend.domain.chat.ChatRepository
import de.kairenken.communicator_backend.testing.data.ChatCreationDtoTestDataFactory
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
        val chatCreationDto = ChatCreationDtoTestDataFactory.aTestChatCreationDto().build()
        justRun { chatRepository.storeChat(any()) }

        val createdChat = chatCreation.create(chatCreationDto)

        assertThat(createdChat.id.value).isInstanceOf(UUID::class.java)
        assertThat(createdChat.name.value).isEqualTo(chatCreationDto.name)
        verify { chatRepository.storeChat(any()) }
    }
}