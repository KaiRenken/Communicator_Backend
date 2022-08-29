package de.kairenken.communicator_backend.domain.chat

import de.kairenken.communicator_backend.domain.message.Message
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import java.util.UUID

internal class ChatTest {

    @Test
    fun `create chat with only required parameters`() {
        val chat = Chat(name = Chat.Name("test-name"))

        assertThat(chat.id.value).isInstanceOf(UUID::class.java)
        assertThat(chat.messageIds).isEmpty()
    }

    @Test
    fun `create chat id`() {
        val chatId = Chat.Id()

        assertThat(chatId.value).isInstanceOf(UUID::class.java)
    }

    @Test
    fun `create chat name with blank value`() {
        assertThatThrownBy { Chat.Name("    ") }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Chat.Name is blank")
    }
}