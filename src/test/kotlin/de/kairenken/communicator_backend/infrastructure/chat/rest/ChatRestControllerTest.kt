package de.kairenken.communicator_backend.infrastructure.chat.rest

import com.ninjasquad.springmockk.MockkBean
import de.kairenken.communicator_backend.application.chat.ChatCreation
import de.kairenken.communicator_backend.application.chat.ChatRetrieval
import de.kairenken.communicator_backend.domain.chat.Chat
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.*

@WebMvcTest(value = [ChatRestController::class])
internal class ChatRestControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var chatCreation: ChatCreation

    @MockkBean
    private lateinit var chatRetrieval: ChatRetrieval

    @Test
    fun `create chat successfully`() {
        val chat = Chat(name = Chat.Name("test-name"))
        val expectedResponse = """
            {
            "id": "${chat.id.value}",
            "name": "${chat.name.value}"
            }
        """

        every { chatCreation.create(any()) } returns chat

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

        every { chatCreation.create(any()) } throws IllegalArgumentException("Chat.Name is blank")

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

    @Test
    fun `create chat with missing body`() {
        mockMvc.perform(
            post("/api/chat")
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("3"))
    }

    @Test
    fun `create chat with name missing in body`() {
        val requestJson = """
            {
            "someOtherKey": "someOtherValue"
            }
        """
        mockMvc.perform(
            post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.code").value("3"))
    }

    @Test
    fun `get all chats`() {
        val chat1 = Chat(
            id = Chat.Id(UUID.randomUUID()),
            name = Chat.Name("test-name-1")
        )
        val chat2 = Chat(
            id = Chat.Id(UUID.randomUUID()),
            name = Chat.Name("test-name-2")
        )
        every { chatRetrieval.retrieveAllChats() } returns listOf(chat1, chat2)
        val expectedResponse = """
            [
              {
              "id": "${chat1.id.value}",
              "name": "test-name-1"
              },
              {
              "id": "${chat2.id.value}",
              "name": "test-name-2"
              }
            ]
        """

        mockMvc.perform(
            get("/api/chat")
        )
            .andExpect(status().isOk)
            .andExpect(content().json(expectedResponse, true))
    }
}