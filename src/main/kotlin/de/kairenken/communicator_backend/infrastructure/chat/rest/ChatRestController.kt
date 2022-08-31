package de.kairenken.communicator_backend.infrastructure.chat.rest

import de.kairenken.communicator_backend.application.chat.ChatCreation
import de.kairenken.communicator_backend.application.chat.dto.ChatCreationDto
import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.infrastructure.chat.rest.dto.CreateChatDto
import de.kairenken.communicator_backend.infrastructure.chat.rest.dto.ReadChatDto
import de.kairenken.communicator_backend.infrastructure.common.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatRestController(private val chatCreation: ChatCreation) {

    @PostMapping
    fun createChat(@RequestBody createChatDto: CreateChatDto) = try {
        createChatDto
            .mapToChatCreationDto()
            .createChat()
            .mapToReadChatDto()
            .wrapInResponse()
    } catch (e: IllegalArgumentException) {
        e.wrapInBadRequestResponse()
    }

    private fun ChatCreationDto.createChat() = chatCreation.create(this)

    private fun CreateChatDto.mapToChatCreationDto() = ChatCreationDto(name = this.name)

    private fun Chat.mapToReadChatDto() = ReadChatDto(
        id = this.id.value,
        name = this.name.value
    )

    private fun ReadChatDto.wrapInResponse() = ResponseEntity<ReadChatDto>(this, HttpStatus.CREATED)
    private fun IllegalArgumentException.wrapInBadRequestResponse() = ResponseEntity<ErrorResponseDto>(
        ErrorResponseDto("2", this.message),
        HttpStatus.BAD_REQUEST
    )
}
