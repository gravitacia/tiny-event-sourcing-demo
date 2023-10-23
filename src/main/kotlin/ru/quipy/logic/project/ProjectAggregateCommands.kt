package ru.quipy.logic.project

import ru.quipy.api.project.ProjectCreatedEvent
import ru.quipy.api.project.StatusCreatedEvent
import ru.quipy.api.project.UserInvitedEvent
import java.util.*


// Commands : takes something -> returns event
// Here the commands are represented by extension functions, but also can be the class member functions

fun ProjectAggregateState.create(id: UUID, title: String, creatorId: String): ProjectCreatedEvent {
    return ProjectCreatedEvent(
            projectId = id,
            title = title,
            creatorId = creatorId,
    )
}

fun ProjectAggregateState.createStatus(title: String, color: String): StatusCreatedEvent {
    if (projectStatuses.values.any { it.title == title }) {
        throw IllegalArgumentException("Status already exists: $title")
    }
    return StatusCreatedEvent(projectId = this.getId(), statusId = UUID.randomUUID(), title = title, color = color)
}

fun ProjectAggregateState.inviteUser(id: UUID): UserInvitedEvent {
    if (projectUsers.values.any { it.userId == id }) {
        throw IllegalArgumentException("User already exists: $id")
    }
    return UserInvitedEvent(projectId = this.getId(), userId = id)
}