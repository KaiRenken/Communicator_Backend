package de.kairenken.communicator_backend.domain.message

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.util.*

internal class MessageTest {

    @Test
    fun `create message id`() {
        val messageId = Message.Id()

        assertThat(messageId.value).isInstanceOf(UUID::class.java)
    }

    @Test
    fun `create message content with empty content`() {
        assertThatThrownBy { Message.Content("") }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `create message content with blank content`() {
        assertThatThrownBy { Message.Content("     ") }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
    }
}