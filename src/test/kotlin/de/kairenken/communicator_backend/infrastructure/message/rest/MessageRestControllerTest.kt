package de.kairenken.communicator_backend.infrastructure.message.rest

import com.ninjasquad.springmockk.MockkBean
import de.kairenken.communicator_backend.application.message.MessageCreation
import de.kairenken.communicator_backend.application.message.MessageRetrieval
import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.testing.data.MessageTestDataFactory
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

    @MockkBean
    private lateinit var messageRetrieval: MessageRetrieval

    @Test
    fun `create message successfully`() {
        val message = MessageTestDataFactory.aTestMessage().build()
        val expectedResponse = """
            {
            "id": "${message.id.value}",
            "chatRefId": "${message.chatRefId.value}",
            "content": "${message.content.value}"
            }
        """

        every { messageCreation.create(any()) } returns MessageCreation.Success(message)

        val requestJson = """
            {
            "content": "${message.content.value}"
            }
        """
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/message/${message.chatRefId.value}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().json(expectedResponse, true))
    }

    @Test
    fun `create message for non existing chat`() {
        val message = MessageTestDataFactory.aTestMessage().build()
        val expectedResponse = """
            {
            "code": "1",
            "message": "Chat with id '${message.chatRefId.value}' not found"
            }
            """

        every { messageCreation.create(any()) } returns MessageCreation.Error(message.chatRefId)

        val requestJson = """
            {
            "content": "${message.content.value}"
            }
        """
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/message/${message.chatRefId.value}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.content().json(expectedResponse, true))
    }

    @Test
    fun `create message with blank content`() {
        val expectedResponse = """
            {
            "code": "2",
            "message": "Message.Content is blank"
            }
            """

        every { messageCreation.create(any()) } throws IllegalArgumentException("Message.Content is blank")

        val requestJson = """
            {
            "content": ""
            }
        """
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/message/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().json(expectedResponse, true))
    }

    @Test
    fun `create message with missing body`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/message/" + UUID.randomUUID())
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("3"))
    }

    @Test
    fun `create message with content missing in body`() {
        val requestJson = """
            {
            "someOtherKey": "someOtherValue"
            }
        """

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/message/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("3"))
    }

    @Test
    fun `create message with invalid chat id`() {
        val requestJson = """
            {
            "content": "test-content"
            }
        """

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/message/invalidChatId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("4"))
    }

    @Test
    fun `retrieve messages by chatRefId successfully`() {
        val message1 = MessageTestDataFactory.aTestMessage().build()
        val message2 = MessageTestDataFactory.aTestMessage()
            .withId(UUID.randomUUID())
            .build()
        val expectedResponse = """
            [
              {
              "id": "${message1.id.value}",
             "chatRefId": "${message1.chatRefId.value}",
             "content": "${message1.content.value}"
              },
              {
              "id": "${message2.id.value}",
              "chatRefId": "${message2.chatRefId.value}",
              "content": "${message2.content.value}"
              }
            ]
        """

        every { messageRetrieval.retrieveAllMessagesByChatRefId(any()) } returns MessageRetrieval.Success(
            listOf(message1, message2)
        )

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/message/${message1.chatRefId.value}")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(expectedResponse, true))
    }

    @Test
    fun `retrieve messages by chatRefId for non existing chat`() {
        val chatRefId = Message.ChatRefId(UUID.randomUUID())
        val expectedResponse = """
            {
            "code": "1",
            "message": "Chat with id '${chatRefId.value}' not found"
            }
        """

        every { messageRetrieval.retrieveAllMessagesByChatRefId(any()) } returns MessageRetrieval.Error(chatRefId)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/message/${chatRefId.value}")
        )
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.content().json(expectedResponse, true))
    }

    @Test
    fun `retrieve messages by chatRefId with invalid chat id`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/message/invalidChatId")
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("4"))
    }
}