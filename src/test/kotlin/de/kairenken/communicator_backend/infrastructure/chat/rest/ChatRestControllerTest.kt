package de.kairenken.communicator_backend.infrastructure.chat.rest

import com.ninjasquad.springmockk.MockkBean
import de.kairenken.communicator_backend.application.chat.ChatCreation
import de.kairenken.communicator_backend.domain.chat.Chat
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(value = [ChatRestController::class])
internal class ChatRestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var chatCreation: ChatCreation

    @Test
    fun `create chat successfully`() {
        val chat = Chat(name = Chat.Name("test-name"))
        val expectedResponse = """
            {
            "id": "${chat.id.value}",
            "name": "${chat.name.value}",
            "messageIds": []
            }
        """

        every { chatCreation.createChat(any()) } returns chat

        val requestJson = """
            {
            "name": "${chat.name.value}"
            }
        """
        mockMvc.perform(
            post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(status().isCreated)
            .andExpect(content().json(expectedResponse, true))
    }

    @Test
    fun `create chat with blank name`() {
        val expectedResponse = """
            {
            "code": "2",
            "message": "Chat.Name is blank"
            }
        """

        every { chatCreation.createChat(any()) } throws IllegalArgumentException("Chat.Name is blank")

        val requestJson = """
            {
            "name": " "
            }
        """
        mockMvc.perform(
            post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().json(expectedResponse, true))
    }
}