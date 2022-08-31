package de.kairenken.communicator_backend.infrastructure.chat.rest

import de.kairenken.communicator_backend.application.chat.ChatCreation
import de.kairenken.communicator_backend.application.chat.ChatRetrieval
import de.kairenken.communicator_backend.application.chat.dto.ChatCreationDto
import de.kairenken.communicator_backend.domain.chat.Chat
import de.kairenken.communicator_backend.infrastructure.chat.rest.dto.CreateChatDto
import de.kairenken.communicator_backend.infrastructure.chat.rest.dto.ReadChatDto
import de.kairenken.communicator_backend.infrastructure.common.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chat")
class ChatRestController(
    private val chatCreation: ChatCreation,
    private val chatRetrieval: ChatRetrieval
) {

    @PostMapping
    fun postChat(@RequestBody createChatDto: CreateChatDto) = try {
        createChatDto
            .mapToChatCreationDto()
            .createChat()
            .mapToReadChatDto()
            .wrapInResponse()
    } catch (e: IllegalArgumentException) {
        e.wrapInBadRequestResponse()
    }

    @GetMapping
    fun getAllChats() = chatRetrieval
        .retrieveAllChats()
        .mapToReadChatDtos()
        .wrapInResponse()

    private fun ChatCreationDto.createChat() = chatCreation.create(this)

    private fun CreateChatDto.mapToChatCreationDto() = ChatCreationDto(name = this.name)

    private fun Chat.mapToReadChatDto() = ReadChatDto(
        id = this.id.value,
        name = this.name.value
    )

    private fun List<Chat>.mapToReadChatDtos() = this.map { it.mapToReadChatDto() }

    private fun ReadChatDto.wrapInResponse() = ResponseEntity<ReadChatDto>(this, HttpStatus.CREATED)

    private fun List<ReadChatDto>.wrapInResponse() = ResponseEntity<List<ReadChatDto>>(this, HttpStatus.OK)

    private fun IllegalArgumentException.wrapInBadRequestResponse() = ResponseEntity<ErrorResponseDto>(
        ErrorResponseDto("2", this.message),
        HttpStatus.BAD_REQUEST
    )
}
