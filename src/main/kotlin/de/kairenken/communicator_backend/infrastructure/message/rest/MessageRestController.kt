package de.kairenken.communicator_backend.infrastructure.message.rest

import de.kairenken.communicator_backend.application.message.MessageCreation
import de.kairenken.communicator_backend.application.message.MessageCreationResult
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
class MessageRestController(private val messageCreation: MessageCreation) {

    @PostMapping("/{chatRefId}")
    fun createMessage(
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

    private fun CreateMessageDto.mapToMessageCreationDto(chatRefId: UUID) = MessageCreationDto(chatRefId, this.content)

    private fun MessageCreationDto.createMessage() = messageCreation.createMessage(this)

    private fun MessageCreationResult.wrapInResponse() = when (this) {
        is MessageCreationResult.Error -> this.chatRefId.wrapInNotFoundResponse()
        is MessageCreationResult.Success -> this.message.wrapInCreatedResponse()
    }

    private fun Message.ChatRefId.wrapInNotFoundResponse() = ResponseEntity<ErrorResponseDto>(
        ErrorResponseDto("1", "Chat with id '${this.value}' not found"),
        HttpStatus.NOT_FOUND
    )

    private fun Message.wrapInCreatedResponse() = ResponseEntity<ReadMessageDto>(
        this.mapToReadMessageDto(),
        HttpStatus.CREATED
    )

    private fun Message.mapToReadMessageDto() = ReadMessageDto(
        this.id.value,
        this.chatRefId.value,
        this.content.value
    )

    private fun IllegalArgumentException.wrapInBadRequestResponse() = ResponseEntity<ErrorResponseDto>(
        ErrorResponseDto("2", this.message),
        HttpStatus.BAD_REQUEST
    )
}
