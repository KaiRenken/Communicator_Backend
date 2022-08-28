package de.kairenken.communicator_backend.infrastructure.message.rest

import de.kairenken.communicator_backend.application.message.ChatNotFound
import de.kairenken.communicator_backend.application.message.MessageCreated
import de.kairenken.communicator_backend.application.message.MessageCreation
import de.kairenken.communicator_backend.application.message.Result
import de.kairenken.communicator_backend.domain.message.Message
import de.kairenken.communicator_backend.domain.message.dto.MessageCreationDto
import de.kairenken.communicator_backend.infrastructure.message.rest.dto.ChatNotFoundDto
import de.kairenken.communicator_backend.infrastructure.message.rest.dto.CreateMessageDto
import de.kairenken.communicator_backend.infrastructure.message.rest.dto.ReadMessageDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/message")
class MessageRestController(val messageCreation: MessageCreation) {

    @PostMapping
    fun createMessage(@RequestBody createMessageDto: CreateMessageDto) = createMessageDto
        .mapToMessageCreationDto()
        .createMessage()
        .wrapInResponse()

    private fun CreateMessageDto.mapToMessageCreationDto() = MessageCreationDto(this.chatRefId, this.content)

    private fun MessageCreationDto.createMessage() = messageCreation.createMessage(this)

    private fun Result.wrapInResponse() = when (this) {
        is ChatNotFound -> this.chatRefId.wrapInNotFoundResponse()
        is MessageCreated -> this.message.wrapInCreatedResponse()
    }

    private fun Message.ChatRefId.wrapInNotFoundResponse() = ResponseEntity<ChatNotFoundDto>(
        ChatNotFoundDto("Chat with id '${this.value}' not found"),
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
}
