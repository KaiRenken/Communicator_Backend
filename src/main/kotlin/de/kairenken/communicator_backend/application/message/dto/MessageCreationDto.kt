package de.kairenken.communicator_backend.application.message.dto

import java.util.*

class MessageCreationDto(
    val chatRefId: UUID,
    val content: String
)