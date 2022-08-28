package de.kairenken.communicator_backend.infrastructure.chat.rest

import com.ninjasquad.springmockk.MockkBean
import de.kairenken.communicator_backend.application.chat.ChatCreation
import de.kairenken.communicator_backend.domain.chat.Chat
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
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
        val chat = Chat()
        val expectedResponse = """
            {
            "id": "${chat.id.value}",
            "messageIds": []
            }
        """

        every { chatCreation.createChat() } returns chat

        mockMvc.perform(post("/api/chat"))
            .andExpect(status().isCreated)
            .andExpect(content().json(expectedResponse, true))
    }
}