package de.kairenken.communicator_backend.infrastructure.message.database

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MessageJpaRepository : JpaRepository<UUID, MessageEntity>