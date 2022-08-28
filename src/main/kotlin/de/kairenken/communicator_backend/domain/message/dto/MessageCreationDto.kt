package de.kairenken.communicator_backend.domain.message.dto

import java.util.*

class MessageCreationDto(
    val chatRefId: UUID,
    val content: String
)