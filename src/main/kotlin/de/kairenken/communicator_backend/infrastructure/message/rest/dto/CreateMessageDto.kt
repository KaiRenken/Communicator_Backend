package de.kairenken.communicator_backend.infrastructure.message.rest.dto

import java.util.*

class CreateMessageDto(
    val chatRefId: UUID,
    val content: String
)