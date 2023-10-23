package ru.quipy.logic.task

import ru.quipy.api.task.*
import java.util.*

fun TaskAggregateState.create(id: UUID, title: String, creatorId: String, projectId: UUID): TaskCreatedEvent {
    return TaskCreatedEvent(
            taskId = id,
            title = title,
            projectId = projectId
    )
}

fun TaskAggregateState.changeTitle(id: UUID, title: String): TaskTitleChangedEvent {
    return TaskTitleChangedEvent(
            taskId = id,
            title = title
    )
}

fun TaskAggregateState.assignStatus(id: UUID, statusId: UUID): StatusAssignedToTaskEvent {
    return StatusAssignedToTaskEvent(
            taskId = id,
            statusId = statusId,
    )
}

fun TaskAggregateState.addExecutor(id: UUID): TaskAddedExecutorEvent {
    if (assignees.values.any { it.userId == id }) {
        throw IllegalArgumentException("User already exists: $id")
    }
    return TaskAddedExecutorEvent(taskId = this.getId(), executorId = id)
}

fun TaskAggregateState.deleteTask(projectId: UUID, id: UUID): TaskRemoved {
    return TaskRemoved(
            taskId = id,
            projectId = projectId
    )
}
