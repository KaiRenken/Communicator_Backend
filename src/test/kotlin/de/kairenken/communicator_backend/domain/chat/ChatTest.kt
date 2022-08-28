package de.kairenken.communicator_backend.domain.chat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import java.util.UUID

internal class ChatTest {

    @Test
    fun `create chat without parameters`() {
        val chat = Chat()

        assertThat(chat.id.value).isInstanceOf(UUID::class.java)
        assertThat(chat.messageIds).isEmpty()
    }

    @Test
    fun `create chat id`() {
        val chatId = Chat.Id()

        assertThat(chatId.value).isInstanceOf(UUID::class.java)
    }
}