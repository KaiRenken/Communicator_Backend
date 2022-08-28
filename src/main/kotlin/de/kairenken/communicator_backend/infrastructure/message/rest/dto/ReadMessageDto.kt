package de.kairenken.communicator_backend.infrastructure.message.rest.dto

import java.util.*

class ReadMessageDto(
    val id: UUID,
    val chatRefId: UUID,
    val content: String
)