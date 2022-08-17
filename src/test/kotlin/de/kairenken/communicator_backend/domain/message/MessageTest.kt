package de.kairenken.communicator_backend.domain.message

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*

internal class MessageTest {

    @Test
    @DisplayName("Create Message.Id")
    fun createMessageId() {
        val messageId = Message.Id()

        assertThat(messageId.value).isInstanceOf(UUID::class.java)
    }

    @Test
    @DisplayName("Create empty Message.Content")
    fun createMessageContentWithEmptyContent() {
        assertThatThrownBy { Message.Content("") }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    @DisplayName("Create blank Message.Content")
    fun createMessageContentWithBlankContent() {
        assertThatThrownBy { Message.Content(" ") }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
    }
}