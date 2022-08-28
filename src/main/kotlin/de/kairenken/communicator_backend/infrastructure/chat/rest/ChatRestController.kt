package de.kairenken.communicator_backend.infrastructure.chat.rest

import de.kairenken.communicator_backend.application.chat.ChatCreation
import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.infrastructure.chat.rest.dto.ReadChatDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatRestController(private val chatCreation: ChatCreation) {

    @PostMapping
    fun createChat(): ResponseEntity<ReadChatDto> {
        return chatCreation
            .createChat()
            .mapToReadChatDto()
            .wrapInResponse()
    }

    private fun Chat.mapToReadChatDto() = ReadChatDto(
        this.id.value,
        this.messageIds.map { it.value }
    )

    private fun ReadChatDto.wrapInResponse() = ResponseEntity<ReadChatDto>(this, HttpStatus.CREATED)
}
