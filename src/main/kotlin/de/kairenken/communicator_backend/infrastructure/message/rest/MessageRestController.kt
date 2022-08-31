package de.kairenken.communicator_backend.infrastructure.message.rest

import de.kairenken.communicator_backend.application.message.MessageCreation
import de.kairenken.communicator_backend.application.message.MessageRetrieval
import de.kairenken.communicator_backend.application.message.dto.MessageCreationDto
import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.infrastructure.common.ErrorResponseDto
import de.kairenken.communicator_backend.infrastructure.message.rest.dto.CreateMessageDto
import de.kairenken.communicator_backend.infrastructure.message.rest.dto.ReadMessageDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/message")
class MessageRestController(
    private val messageCreation: MessageCreation,
    private val messageRetrieval: MessageRetrieval
) {

    @PostMapping("/{chatRefId}")
    fun postMessage(
        @PathVariable(name = "chatRefId") chatRefId: UUID,
        @RequestBody createMessageDto: CreateMessageDto
    ) = try {
        createMessageDto
            .mapToMessageCreationDto(chatRefId)
            .createMessage()
            .wrapInResponse()
    } catch (e: IllegalArgumentException) {
        e.wrapInBadRequestResponse()
    }

    @GetMapping("/{chatRefId}")
    fun getMessagesFromChat(@PathVariable(name = "chatRefId") chatRefId: UUID) = chatRefId
        .retrieveMessages()
        .wrapInResponse()

    private fun MessageCreationDto.createMessage() = messageCreation.create(this)

    private fun UUID.retrieveMessages() = messageRetrieval.retrieveAllMessagesByChatRefId(Message.ChatRefId(this))

    private fun MessageCreation.Result.wrapInResponse() = when (this) {
        is MessageCreation.Error -> this.chatRefId.wrapInNotFoundResponse()
        is MessageCreation.Success -> this.message.wrapInCreatedResponse()
    }

    private fun MessageRetrieval.Result.wrapInResponse() = when (this) {
        is MessageRetrieval.Success -> this.messages.wrapInRetrievalResponse()
        is MessageRetrieval.Error -> this.chatRefId.wrapInNotFoundResponse()
    }

    private fun Message.ChatRefId.wrapInNotFoundResponse() = ResponseEntity<ErrorResponseDto>(
        ErrorResponseDto("1", "Chat with id '${this.value}' not found"),
        HttpStatus.NOT_FOUND
    )

    private fun Message.wrapInCreatedResponse() = ResponseEntity<ReadMessageDto>(
        this.mapToReadMessageDto(),
        HttpStatus.CREATED
    )

    private fun IllegalArgumentException.wrapInBadRequestResponse() = ResponseEntity<ErrorResponseDto>(
        ErrorResponseDto("2", this.message),
        HttpStatus.BAD_REQUEST
    )

    private fun List<Message>.wrapInRetrievalResponse() = ResponseEntity<List<ReadMessageDto>>(
        this.map { it.mapToReadMessageDto() },
        HttpStatus.OK
    )

    private fun Message.mapToReadMessageDto() = ReadMessageDto(
        this.id.value,
        this.chatRefId.value,
        this.content.value
    )

    private fun CreateMessageDto.mapToMessageCreationDto(chatRefId: UUID) = MessageCreationDto(chatRefId, this.content)
}
