package de.kairenken.communicator_backend.domain.chat

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import java.util.UUID

internal class ChatTest {

    @Test
    @DisplayName("Create Chat.Id")
    fun createChatId() {
        val chatId = Chat.Id()

        assertThat(chatId.value).isInstanceOf(UUID::class.java)
    }
}