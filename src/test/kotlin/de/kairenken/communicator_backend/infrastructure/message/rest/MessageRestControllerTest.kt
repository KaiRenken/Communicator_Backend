package de.kairenken.communicator_backend.infrastructure.message.rest

import com.ninjasquad.springmockk.MockkBean
import de.kairenken.communicator_backend.application.message.ChatNotFound
import de.kairenken.communicator_backend.application.message.MessageCreated
import de.kairenken.communicator_backend.application.message.MessageCreation
import de.kairenken.communicator_backend.domain.message.Message
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@WebMvcTest(value = [MessageRestController::class])
internal class MessageRestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var messageCreation: MessageCreation

    @Test
    fun `create message successfully`() {
        val message = Message(
            chatRefId = Message.ChatRefId(UUID.randomUUID()),
            content = Message.Content("test-content")
        )
        val expectedResponse = """
            {
            "id": "${message.id.value}",
            "chatRefId": "${message.chatRefId.value}",
            "content": "${message.content.value}"
            }
        """

        every { messageCreation.createMessage(any()) } returns MessageCreated(message)

        val requestJson = """
            {
            "chatRefId": "${message.chatRefId.value}",
            "content": "${message.content.value}"
            }
        """
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().json(expectedResponse, true))
    }

    @Test
    fun `create message for non existing chat`() {
        val message = Message(
            chatRefId = Message.ChatRefId(UUID.randomUUID()),
            content = Message.Content("test-content")
        )
        val expectedResponse = """
            {
            "error": "Chat with id '${message.chatRefId.value}' not found"
            }
            """

        every { messageCreation.createMessage(any()) } returns ChatNotFound(message.chatRefId)

        val requestJson = """
            {
            "chatRefId": "${message.chatRefId.value}",
            "content": "${message.content.value}"
            }
        """
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/message")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.content().json(expectedResponse, true))
    }
}