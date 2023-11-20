package ru.quipy.projections.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.quipy.domain.Unique
import java.util.UUID

@Document("users-view")
data class User (
        @Id
        override val id: UUID,
        var name: String,
        val nickname: String,
) : Unique<UUID>